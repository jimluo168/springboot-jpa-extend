package com.bms.industry.sync.busbasic;

import com.bms.industry.sync.Http;
import com.bms.industry.sync.SyncProperties;
import com.bms.industry.sync.busbasic.api.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

/**
 * 基础业务数据同步.
 *
 * @author luojimeng
 * @date 2020/4/3
 */
@Configuration
@EnableConfigurationProperties({SyncProperties.class})
@RequiredArgsConstructor
public class BusBasicSync {
    private static final Logger logger = LoggerFactory.getLogger(BusBasicSync.class);

    private final SyncProperties syncProperties;
    private final Http http;
    private final LoginApi loginApi;
    private final CompanyApi companyApi;
    private final TeamApi teamApi;
    private final BusApi busApi;
    private final LineApi lineApi;
    private final PassengerApi passengerApi;
    private final StationApi stationApi;

    @Scheduled(cron = "${sync.bus.cron}")
    public void execute() {
        logger.info("开始执行基础业务数据同步任务");
        long start = System.currentTimeMillis();

        try {
            http.setHeader("Content-Type", "application/x-www-form-urlencoded");

            String accessToken = loginApi.login();
            http.setHeader("accessToken", accessToken);

            companyApi.getAll();
            teamApi.getAll();
            lineApi.getAll();
            stationApi.getAll();
            busApi.getAll();
            passengerApi.getAll();
        } catch (IOException e) {
            logger.error("执行基础业务数据同步任务出错", e);
        }

        logger.info("结束执行基础业务数据同步任务,总耗时{}秒", (System.currentTimeMillis() - start) / 1000.0f);
    }
}
