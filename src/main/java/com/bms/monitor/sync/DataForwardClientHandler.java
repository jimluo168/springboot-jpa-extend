package com.bms.monitor.sync;

import com.bms.Constant;
import com.bms.common.util.GPSUtils;
import com.bms.entity.MoBusVehicleGpsData;
import com.bms.entity.MoOffSiteData;
import com.bms.industry.sync.SyncProperties;
import com.bms.monitor.view.BusRouteNameAndSiteNameView;
import com.bms.monitor.service.MoBusVehicleGpsDataService;
import com.bms.monitor.service.MoOffSiteDataService;
import io.netty.channel.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.*;

/**
 * 客户端消息处理类.
 *
 * @author luojimeng
 * @date 2020/4/8
 */
@RequiredArgsConstructor
public class DataForwardClientHandler extends SimpleChannelInboundHandler<String> {
    private static final Logger logger = LoggerFactory.getLogger(DataForwardClientHandler.class);

    /**
     * 命令的定义(C2 C7 C8).
     * C2:定位数据.
     * C7:离开站点数据.
     * C8:到达站点数据.
     */
    public static final String CMD_C2 = "C2";
    public static final String CMD_C7 = "C7";
    public static final String CMD_C8 = "C8";

    public static final String CMD_DATA_SPLIT_REGEX = "\\|";
    public static final String DATE_FORMAT_SPACE = " ";

    /**
     * 线程池定义 处理转发过来的数据.
     */
    public static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(400, 400 * 2,
            10L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(400 * 2 * 10),
            Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy()/*new ThreadPoolExecutor.DiscardOldestPolicy()*/);

    private final SyncProperties syncProperties;
    private final MoBusVehicleGpsDataService moBusVehicleGpsDataService;
    private final MoOffSiteDataService moOffSiteDataService;
    private final DataForwardService dataForwardService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("数据转发接收到消息:{}", message);
        }
        try {
            THREAD_POOL_EXECUTOR.execute(() -> {
                String[] cmdArr = message.split(DataForwardClient.PACKET_END);
                for (String s : cmdArr) {
                    String[] dataArr = s.split(CMD_DATA_SPLIT_REGEX, -1);
                    switch (dataArr[1]) {
                        case CMD_C2:
                            cmdC2(dataArr);
                            break;
                        case CMD_C7:
                            cmdC7(dataArr);
                            break;
                        case CMD_C8:
                            cmdC8(dataArr);
                            break;
                    }
                }

            });
        } catch (Exception e) {
            logger.error("execute processing data forwarding thread error,threadPool completed task count:" + THREAD_POOL_EXECUTOR.getCompletedTaskCount() +
                    " active count:" + THREAD_POOL_EXECUTOR.getActiveCount() +
                    " queue size:" + THREAD_POOL_EXECUTOR.getQueue().size() +
                    " cmddata:" + message, e);
        }
    }

    private void cmdC2(String[] data) {
        int i = 2;
        String routeOId = data[i++];
        String vehCode = data[i++];

        String latitudeFen = data[6];
        String longitudeFen = data[7];


        if (StringUtils.isBlank(vehCode)) {
            return;
        }
        /**
         * 判断 同一辆车位置相同 不处理.
         */
        MoDataForwardCache cache = dataForwardService.getMoDataForwardCacheByVehCode(vehCode);
        if (cache != null) {
            if (StringUtils.equals(cache.getLatitudeFen(), latitudeFen)
                    && StringUtils.equals(cache.getLongitudeFen(), longitudeFen)) {
                cache.setLastUpdDate(System.currentTimeMillis());
                cache.setUpdateStatus(MoDataForwardCache.UPDATE_STATUS_FALSE);
                dataForwardService.setMoDataForwardCacheByVehCode(vehCode, cache);
                return;
            }
        }

        // 是否运行 0:静止 1:运动
        Integer isMove = parseInt(data[11], 0);
        Integer isOnline = parseInt(data[23], 0);
        Float speed = parseFloat(data[8], 0.0f);

        BigDecimal latitude = new BigDecimal(GPSUtils.fm2du(latitudeFen));
        BigDecimal longitude = new BigDecimal(GPSUtils.fm2du(latitudeFen));
        Integer upDown = parseInt(data[20], 2);

        /**
         * 将车辆信息存入缓存.
         */
        Integer nextSiteIndex = parseInt(data[12], 0);
        String nextSiteName = StringUtils.EMPTY;
        Integer currentSiteIndex = 0;
        String currentSiteName = StringUtils.EMPTY;
        String practOId = data[22];
        if (nextSiteIndex > 0) {
            currentSiteIndex = nextSiteIndex - 1;
        }

        Float gpsAngle = parseFloat(data[9], 0.0f);

        /**
         * 存放缓存.
         */
        cache = new MoDataForwardCache();
        cache.setCurrentSiteIndex(currentSiteIndex);
        cache.setLatitudeFen(latitudeFen);
        cache.setLongitudeFen(longitudeFen);
        cache.setMove(isMove);
        cache.setNextSiteIndex(nextSiteIndex);
        cache.setOnline(isOnline);
        cache.setPractOId(practOId);
        cache.setSpeed(speed);
        cache.setUpDown(upDown);
        cache.setRouteOId(routeOId);
        cache.setGpsAngle(gpsAngle);
        cache.setUpdateStatus(MoDataForwardCache.UPDATE_STATUS_TRUE);
        dataForwardService.setMoDataForwardCacheByVehCode(vehCode, cache);

        /**
         * 当线路为空时说明车辆停止在总站 无需处理.
         */
        if (StringUtils.isBlank(routeOId)) {
            return;
        }
        if (speed < 1.0f) {
            // 车辆为静止的时候不用保存.
            return;
        }

        MoBusVehicleGpsData gps = new MoBusVehicleGpsData();
        gps.setRouteOId(routeOId);
        gps.setVehCode(vehCode);

        String gpsCreateDate = data[i++] + DATE_FORMAT_SPACE + data[i++];
        gps.setGpsCreateDate(parseDate(gpsCreateDate));

        gps.setLatitude(latitude); //new BigDecimal(GPSUtils.fm2du(data[i++])));
        gps.setLongitude(longitude); //new BigDecimal(GPSUtils.fm2du(data[i++])));
        i++;
        i++;
        gps.setSpeed(speed); //parseFloat(data[i++], 0.0f));
        i++;
        gps.setGpsAngle(gpsAngle);
        i++;
        gps.setHeight(parseFloat(data[i++], 0.0f));
        gps.setMove(isMove); //parseInt(data[i++], 0));
        i++;
        gps.setNextSiteIndex(parseInt(data[i++], 0));
        gps.setEngineTemperature(parseFloat(data[i++], 0.0f));
        gps.setCarriageTemperature(parseFloat(data[i++], 0.0f));
        gps.setMileage(parseInt(data[i++], 0));
        gps.setInsideNumber(parseInt(data[i++], 0));
        gps.setStart(parseInt(data[i++], 0));

        String schDate = data[i++] + DATE_FORMAT_SPACE + data[i++];
        gps.setSchDate(parseDate(schDate));

        gps.setUpDown(upDown);
        i++;
        gps.setVehAlarm(Integer.parseInt(data[i++]));
        gps.setPractOId(data[i++]);
        gps.setOnline(Integer.parseInt(data[i++]));
        gps.setOfflineReason(data[i++]);
        gps.setTripTimes(Integer.parseInt(data[i++]));
        gps.setShiftTimes(Integer.parseInt(data[i++]));
        gps.setCrossLine(Integer.parseInt(data[i++]));
        gps.setOffLine(Integer.parseInt(data[i++]));
        gps.setPractName(data[i++]);
        gps.setWork(parseInt(data[i++], 0));

        moBusVehicleGpsDataService.insert(gps);
    }

    private void cmdC7(String[] data) {
        // 处理C7
    }


    private void cmdC8(String[] data) {
        int i = 2;
        String routeOId = data[i++];
        String vehCode = data[i++];

        /**
         * 当线路为空时说明车辆停止在总站 无需处理.
         */
        if (StringUtils.isBlank(routeOId)) {
            return;
        }


        MoOffSiteData offsite = new MoOffSiteData();
        offsite.setRouteOId(routeOId);
        offsite.setVehCode(vehCode);

        String arrivalDate = data[i++] + DATE_FORMAT_SPACE + data[i++];
        offsite.setArrivalDate(parseDate(arrivalDate));

        String offsiteDate = data[i++] + DATE_FORMAT_SPACE + data[i++];
        offsite.setOffsiteDate(parseDate(offsiteDate));
        offsite.setMove(Integer.parseInt(data[i++]));
        offsite.setSiteIndex(Integer.parseInt(data[i++]));
        offsite.setGetonNumber(parseInt(data[i++], 0));
        offsite.setGetoffNumber(parseInt(data[i++], 0));
        offsite.setInsideNumber(parseInt(data[i++], 0));

        /**
         * TODO 改用缓存.
         */
        if (StringUtils.isNotBlank(offsite.getRouteOId())) {
            BusRouteNameAndSiteNameView view = dataForwardService.findBusRouteNameAndSiteNameByRouteOIdAndSiteIndex(offsite.getRouteOId(), offsite.getSiteIndex());
            if (view != null) {
                offsite.setRouteName(view.getRouteName());
                offsite.setSiteName(view.getSiteName());
            }
        }

        moOffSiteDataService.insert(offsite);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ctx.writeAndFlush(syncProperties.getDataForward().getAccessKey());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        Channel channel = ctx.channel();
        if (channel.isActive()) {
            ctx.close();
        }
    }

    private static Date parseDate(String s) {
        if (StringUtils.equals(s, DATE_FORMAT_SPACE)) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constant.DATE_FORMAT_YYYY_MM_DD_HH_mm_ss);
        LocalDateTime localDateTime = LocalDateTime.parse(s, formatter);
        Instant instant = localDateTime.atZone(ZoneId.of(Constant.TIME_ZONE_GMT8)).toInstant();
        return Date.from(instant);
    }

    private static Integer parseInt(String s, int defaultValue) {
        if (StringUtils.isBlank(s)) {
            return defaultValue;
        }
        return Integer.parseInt(s);
    }

    private static Float parseFloat(String s, float defaultValue) {
        if (StringUtils.isBlank(s)) {
            return defaultValue;
        }
        return Float.parseFloat(s);
    }
}
