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
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 客户端消息处理类.
 *
 * @author luojimeng
 * @date 2020/4/8
 */
@RequiredArgsConstructor
public class DataForwardClientHandler extends SimpleChannelInboundHandler<String> {
    private static final Logger logger = LoggerFactory.getLogger(DataForwardClientHandler.class);

    public static final String CMD_C2 = "C2";
    public static final String CMD_C7 = "C7";
    public static final String CMD_C8 = "C8";
    public static final String CMD_DATA_SPLIT_REGEX = "\\|";
    public static final String DATE_FORMAT_BLANK = " ";

    public static final ExecutorService THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(20, 20000,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(10000000));

    private final SyncProperties syncProperties;
    private final MoBusVehicleGpsDataService moBusVehicleGpsDataService;
    private final MoOffSiteDataService moOffSiteDataService;
    private final DataForwardService dataForwardService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("数据转发接收到消息:{}", message);
        }

        THREAD_POOL_EXECUTOR.execute(() -> {
            String[] cmdArr = message.split(DataForwardClient.PACKET_END);
            try {
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
            } catch (Exception e) {
                logger.error("parse cmd data error, cmddata:" + message, e);
            }
        });
    }

    private void cmdC2(String[] data) {
        int i = 2;
        String routeOId = data[i++];
        String vehCode = data[i++];
        if (StringUtils.isNotBlank(vehCode)) {
            Integer nextSiteIndex = parseInt(data[12], 0);
            String nextSiteName = StringUtils.EMPTY;
            Integer currentSiteIndex = 0;
            String currentSiteName = StringUtils.EMPTY;
            Float speed = parseFloat(data[8], 0.0f);
            String practOId = data[22];

            if (StringUtils.isNotBlank(routeOId)) {
                BusRouteNameAndSiteNameView view = dataForwardService.findBusRouteNameAndSiteNameByRouteOIdAndSiteIndex(routeOId, nextSiteIndex);
                if (view != null) {
                    nextSiteName = view.getSiteName();
                }
                if (nextSiteIndex > 0) {
                    currentSiteIndex = nextSiteIndex - 1;
                    view = dataForwardService.findBusRouteNameAndSiteNameByRouteOIdAndSiteIndex(routeOId, nextSiteIndex - 1);
                    if (view != null) {
                        currentSiteName = view.getSiteName();
                    }
                } else {
                    currentSiteName = nextSiteName;
                }
            } else {
                if (nextSiteIndex > 0) {
                    currentSiteIndex = nextSiteIndex - 1;
                }
            }

            dataForwardService.updateBusVehicleByCode(vehCode, parseInt(data[11], 0), parseInt(data[23], 0),
                    currentSiteIndex, currentSiteName, speed,
                    nextSiteIndex, nextSiteName, practOId);
        }

        /**
         * 当线路为空时说明车辆停止在总站 无需处理.
         */
        if (StringUtils.isBlank(routeOId)) {
            return;
        }

        MoBusVehicleGpsData gps = new MoBusVehicleGpsData();
        gps.setRouteOId(routeOId);
        gps.setVehCode(vehCode);

        String gpsCreateDate = data[i++] + DATE_FORMAT_BLANK + data[i++];
        gps.setGpsCreateDate(parseDate(gpsCreateDate));

        gps.setLatitude(new BigDecimal(GPSUtils.fm2du(data[i++])));
        gps.setLongitude(new BigDecimal(GPSUtils.fm2du(data[i++])));
        gps.setSpeed(parseFloat(data[i++], 0.0f));
        gps.setGpsAngle(parseFloat(data[i++], 0.0f));
        gps.setHeight(parseFloat(data[i++], 0.0f));
        gps.setMove(parseInt(data[i++], 0));
        gps.setNextSiteIndex(parseInt(data[i++], 0));
        gps.setEngineTemperature(parseFloat(data[i++], 0.0f));
        gps.setCarriageTemperature(parseFloat(data[i++], 0.0f));
        gps.setMileage(parseInt(data[i++], 0));
        gps.setInsideNumber(parseInt(data[i++], 0));
        gps.setStart(parseInt(data[i++], 0));

        String schDate = data[i++] + DATE_FORMAT_BLANK + data[i++];
        gps.setSchDate(parseDate(schDate));

        gps.setUpDown(parseInt(data[i++], 2));
        gps.setVehAlarm(Integer.parseInt(data[i++]));
        gps.setPractOId(data[i++]);
        gps.setOnline(Integer.parseInt(data[i++]));
        gps.setOfflineReason(data[i++]);
        gps.setTripTimes(Integer.parseInt(data[i++]));
        gps.setShiftTimes(Integer.parseInt(data[i++]));
        gps.setCrossLine(Integer.parseInt(data[i++]));
        gps.setOffLine(Integer.parseInt(data[i++]));
        gps.setPractName(data[i++]);
        gps.setWork(Integer.parseInt(data[i++]));

        moBusVehicleGpsDataService.insert(gps);
    }

    private void cmdC7(String[] data) {

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
        String arrivalDate = data[i++] + " " + data[i++];
        offsite.setArrivalDate(parseDate(arrivalDate));
        String offsiteDate = data[i++] + " " + data[i++];
        offsite.setOffsiteDate(parseDate(offsiteDate));
        offsite.setMove(Integer.parseInt(data[i++]));
        offsite.setSiteIndex(Integer.parseInt(data[i++]));
        offsite.setGetonNumber(parseInt(data[i++], 0));
        offsite.setGetoffNumber(parseInt(data[i++], 0));
        offsite.setInsideNumber(parseInt(data[i++], 0));

        if (StringUtils.isNotBlank(offsite.getRouteOId())) {
            BusRouteNameAndSiteNameView view = dataForwardService.findBusRouteNameAndSiteNameByRouteOIdAndSiteIndex(offsite.getRouteOId(), offsite.getSiteIndex());
            if (view != null) {
                offsite.setRouteName(view.getRouteName());
                offsite.setSiteName(view.getSiteName());
            }
        }

        moOffSiteDataService.insert(offsite);
    }

    private static Date parseDate(String s) {
        if (StringUtils.equals(s, DATE_FORMAT_BLANK)) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constant.DATE_FORMAT_YYYY_MM_DD_HH_mm_ss);
        LocalDateTime localDateTime = LocalDateTime.parse(s, formatter);
        Instant instant = localDateTime.atZone(ZoneId.of("GMT+8")).toInstant();
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
}
