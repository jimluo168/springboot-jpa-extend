package com.bms.monitor.sync;

import com.bms.common.config.redis.RedisClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * 定时刷新车辆信息.
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void updateBusVehicleInfo() {
        logger.info("定时刷新车辆信息任务开始执行");
        long start = System.currentTimeMillis();
        try {
            Set<String> keySet = redisClient.keys(DataForwardClientHandler.CACHE_KEYS);
            if (keySet == null || keySet.isEmpty()) {
                logger.info("定时刷新车辆信息");
                return;
            }
        } finally {
            logger.info("定时刷新车辆信息任务结束执行... 总耗时:{}ms", System.currentTimeMillis() - start);
        }
    }
}
