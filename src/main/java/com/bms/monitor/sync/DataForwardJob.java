package com.bms.monitor.sync;

import com.bms.common.config.redis.RedisClient;
import com.bms.common.util.GPSUtils;
import com.bms.common.util.JSON;
import com.bms.monitor.view.BusRouteNameAndSiteNameView;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

/**
 * 数据转发Job.
 *
 * @author luojimeng
 * @date 2020/4/14
 */
@Service
@RequiredArgsConstructor
public class DataForwardJob {
    private static final Logger logger = LoggerFactory.getLogger(DataForwardJob.class);

    private final DataForwardService dataForwardService;
    private final RedisClient redisClient;

    /**
     * 超时连接.
     */
    public static final int ONLINE_TIME_OUT = 5 * 60 * 1000;

    /**
     * 定时刷新车辆信息.
     */
    @Async
    @Scheduled(cron = "0 0/5 * * * ?")
    public void updateBusVehicleInfo() {
        logger.info("定时刷新车辆信息任务开始执行");
        long start = System.currentTimeMillis();
        try {
            Set<String> keySet = dataForwardService.findCacheKeys();
            if (keySet == null || keySet.isEmpty()) {
                logger.info("定时刷新车辆信息缓存不存在");
                return;
            }
            keySet.stream().forEach(key -> {
                String json = redisClient.get(key);
                MoDataForwardCache cache = JSON.parseObject(json, MoDataForwardCache.class);
                if (cache == null) {
                    return;
                }
                // 如果数据5分钟内没有接收到数据 代表与服务器断开了
                if (cache.getLastUpdDate() != null && (System.currentTimeMillis() - cache.getLastUpdDate()) > ONLINE_TIME_OUT) {
                    cache.setOnline(MoDataForwardCache.ONLINE_OFF);
                    cache.setUpdateStatus(MoDataForwardCache.UPDATE_STATUS_TRUE);
                }
                // 如果数据没有更新 无需处理
                if (cache.getUpdateStatus() != null && cache.getUpdateStatus() == MoDataForwardCache.UPDATE_STATUS_FALSE) {
                    return;
                }
                cache.setVehCode(key.replace(DataForwardService.CACHE_KEYS.substring(0, DataForwardService.CACHE_KEYS.length() - 1), ""));
                // 转换经纬度
                if (StringUtils.isNotBlank(cache.getLatitudeFen())) {
                    cache.setLatitude(new BigDecimal(GPSUtils.fm2du(cache.getLatitudeFen())));
                }
                if (StringUtils.isNotBlank(cache.getLongitudeFen())) {
                    cache.setLongitude(new BigDecimal(GPSUtils.fm2du(cache.getLongitudeFen())));
                }

                if (cache.getNextSiteIndex() > 0) {
                    cache.setCurrentSiteIndex(cache.getNextSiteIndex() - 1);
                }
                if (StringUtils.isNotBlank(cache.getRouteOId())) {
                    BusRouteNameAndSiteNameView view = dataForwardService.findBusRouteNameAndSiteNameByRouteOIdAndSiteIndex(cache.getRouteOId(), cache.getNextSiteIndex());
                    if (view != null) {
                        cache.setCurrentSiteName(view.getSiteName());
                    }
                    if (cache.getNextSiteIndex() > 0) {
                        view = dataForwardService.findBusRouteNameAndSiteNameByRouteOIdAndSiteIndex(cache.getRouteOId(), cache.getCurrentSiteIndex());
                        if (view != null) {
                            cache.setCurrentSiteName(view.getSiteName());
                        }
                    } else {
                        cache.setCurrentSiteName(cache.getNextSiteName());
                    }
                }

                dataForwardService.updateBusVehicleByCode(cache);
            });

        } finally {
            logger.info("定时刷新车辆信息任务结束执行... 总耗时:{}ms", System.currentTimeMillis() - start);
        }
    }
}
