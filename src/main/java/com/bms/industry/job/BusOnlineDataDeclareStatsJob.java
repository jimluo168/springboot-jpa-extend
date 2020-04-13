package com.bms.industry.job;

import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.entity.*;
import com.bms.industry.dao.BusOnlineDataDeclareStatsRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 数据申报统计定时任务.
 *
 * @author luojimeng
 * @date 2020/4/8
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class BusOnlineDataDeclareStatsJob {

    private static final Logger logger = LoggerFactory.getLogger(BusOnlineDataDeclareStatsJob.class);

    private final HibernateDao hibernateDao;
    private final BusOnlineDataDeclareStatsRepository busOnlineDataDeclareStatsRepository;
    private final FlakeId flakeId;

    /**
     * 年统计 按每个月份统计一次 每月1号开始执行.
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    public void yearOfMonth() {
        logger.info("数据申报统计定时任务开始执行年统计...");
        long start = System.currentTimeMillis();
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 16, 0, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.add(Calendar.MONTH, -1);
            calendar.setTime(calendar.getTime());
            calendar.set(Calendar.DAY_OF_MONTH, 0);

            Date begin = new Date(calendar.getTimeInMillis());
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date end = new Date(calendar.getTimeInMillis());

            execute(begin, end, BusOnlineDataDeclareStats.CATEGORY_YEAR);

        } finally {
            logger.info("数据申报统计定时任务结束执行年统计... 总耗时:{}ms", System.currentTimeMillis() - start);
        }
    }

    /**
     * 季度统计 1号 和 16号执行.
     */
    @Scheduled(cron = "0 0 0 1,16 * ?")
    public void quarter() {
        logger.info("数据申报统计定时任务开始执行季度统计...");
        long start = System.currentTimeMillis();
        try {
            Date begin = null;
            Date end = null;
            Calendar calendar = Calendar.getInstance();
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 16, 0, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.setTime(calendar.getTime());

            int day = calendar.get(Calendar.DAY_OF_MONTH);
            if (day == 1) {
                calendar.add(Calendar.MONTH, -1);
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                end = new Date(calendar.getTimeInMillis());
                calendar.set(Calendar.DAY_OF_MONTH, 15);
                begin = new Date(calendar.getTimeInMillis());
            }
            if (day == 16) {
                calendar.set(Calendar.DAY_OF_MONTH, 15);
                end = new Date(calendar.getTimeInMillis());
                calendar.set(Calendar.DAY_OF_MONTH, 0);
                begin = new Date(calendar.getTimeInMillis());
            }

            execute(begin, end, BusOnlineDataDeclareStats.CATEGORY_QUARTER);

        } finally {
            logger.info("数据申报统计定时任务结束执行季度统计... 总耗时:{}ms", System.currentTimeMillis() - start);
        }
    }


    /**
     * 月统计 每5天执行一次.
     */
    @Scheduled(cron = "0 0 0 1,6,11,16,21,26 * ?")
    public void month() {
        logger.info("数据申报统计定时任务开始执行月统计...");
        long start = System.currentTimeMillis();
        try {
            Date begin = null;
            Date end = null;
            Calendar calendar = Calendar.getInstance();
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 16, 0, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.setTime(calendar.getTime());

            int day = calendar.get(Calendar.DAY_OF_MONTH);
            if (day == 1) {
                calendar.add(Calendar.MONTH, -1);
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                end = new Date(calendar.getTimeInMillis());
                calendar.set(Calendar.DAY_OF_MONTH, 25);
                begin = new Date(calendar.getTimeInMillis());
            } else {
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                end = new Date(calendar.getTimeInMillis());
                calendar.add(Calendar.DAY_OF_MONTH, -5);
                begin = new Date(calendar.getTimeInMillis());
            }

            execute(begin, end, BusOnlineDataDeclareStats.CATEGORY_MONTH);

        } finally {
            logger.info("数据申报统计定时任务结束执行月统计... 总耗时:{}ms", System.currentTimeMillis() - start);
        }
    }


    /**
     * 日统计 每天北京时间晚上两点执行.
     */
    @Scheduled(cron = "0 0 18 * * ?")
    public void day() {
        logger.info("数据申报统计定时任务开始执行日统计...");
        long start = System.currentTimeMillis();
        try {
            Date begin = null;
            Date end = null;
            Calendar calendar = Calendar.getInstance();
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 16, 0, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.setTime(calendar.getTime());

            end = new Date(calendar.getTimeInMillis());
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            begin = new Date(calendar.getTimeInMillis());

            execute(begin, end, BusOnlineDataDeclareStats.CATEGORY_WEEK);
        } finally {
            logger.info("数据申报统计定时任务结束执行日统计... 总耗时:{}ms", System.currentTimeMillis() - start);
        }
    }

    private void execute(Date begin, Date end, int category) {
        Map<String, Object> params = new HashMap<>();
        params.put("begin", begin);
        params.put("end", end);
        List<BusOnlineDataDeclareItem> itemList = hibernateDao.getList(new DaoCmd("bus_online_data_declare_stats_job_month", params, BusOnlineDataDeclareItem.class));
        if (itemList == null || itemList.isEmpty()) {
            logger.info("数据申报统计 开始时间:{} 结束时间:{} 数据为空.", begin, end);
        }

        List<BusOnlineDataDeclareItem> orgRouteList = hibernateDao.getList(new DaoCmd("bus_online_data_declare_stats_job_month_org_route", null, BusOnlineDataDeclareItem.class));
        if (orgRouteList == null || orgRouteList.isEmpty()) {
            logger.error("系统不存在公司和线路,无法统计");
            return;
        }
        for (BusOnlineDataDeclareItem item : orgRouteList) {

            AtomicReference<BusOnlineDataDeclareItem> existItemRef = new AtomicReference<>();
            if (itemList != null && !itemList.isEmpty()) {
                itemList.forEach(o -> {
                    if (o.getOrgId() != null && o.getOrgId() == item.getOrgId()) {
                        existItemRef.set(o);
                    }
                });
            }

            for (int type : BusOnlineDataDeclareStats.TYPES) {
                BusOnlineDataDeclareItem existItem = existItemRef.get();
                if (existItem == null) {
                    existItem = new BusOnlineDataDeclareItem();
                    BeanUtils.copyProperties(item, existItem);
                }
                BusOnlineDataDeclareStats stats = new BusOnlineDataDeclareStats();
                BeanUtils.copyProperties(item, stats);

                stats.setId(flakeId.next());
                if (type == BusOnlineDataDeclareStats.TYPE_GAS) {
                    stats.setQuantity(Optional.ofNullable(item.getGasQuantity()).orElse(new BigDecimal(0.0)));
                    stats.setBalance(Optional.ofNullable(item.getGasBalance()).orElse(new BigDecimal(0.0)));
                }
                if (type == BusOnlineDataDeclareStats.TYPE_DIESELOIL) {
                    stats.setQuantity(Optional.ofNullable(item.getDieselOilQuantity()).orElse(new BigDecimal(0.0)));
                    stats.setBalance(Optional.ofNullable(item.getDieselOilBalance()).orElse(new BigDecimal(0.0)));
                }
                if (type == BusOnlineDataDeclareStats.TYPE_NATURALGAS) {
                    stats.setQuantity(Optional.ofNullable(item.getNaturalGasQuantity()).orElse(new BigDecimal(0.0)));
                    stats.setBalance(Optional.ofNullable(item.getNaturalGasBalance()).orElse(new BigDecimal(0.0)));
                }
                if (type == BusOnlineDataDeclareStats.TYPE_ELECTRIC) {
                    stats.setQuantity(Optional.ofNullable(item.getElectricQuantity()).orElse(new BigDecimal(0.0)));
                    stats.setBalance(Optional.ofNullable(item.getElectricBalance()).orElse(new BigDecimal(0.0)));
                }
                stats.setCategory(category);
                stats.setType(type);
                stats.setTime(end);

                BusRoute route = new BusRoute();
                route.setId(item.getRouteId());
                stats.setBusRoute(route);

                Organization organization = new Organization();
                organization.setId(item.getOrgId());
                stats.setOrganization(organization);

                busOnlineDataDeclareStatsRepository.save(stats);
            }
        }
    }

}
