package com.bms.industry.service;

import com.bms.Constant;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.util.BeanMapper;
import com.bms.common.util.DateUtil;
import com.bms.entity.BusOnlineDataDeclareItem;
import com.bms.entity.BusOnlineDataDeclareStats;
import com.bms.industry.view.BusOnlineDataDeclareStatsEnergyComparison;
import com.bms.industry.view.BusOnlineDataDeclareStatsEnergyComparisonEchartView;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 统计数据表.
 *
 * @author luojimeng
 * @date 2020/3/30
 */
@Service
@Transactional(readOnly = true, rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class BusOnlineDataDeclareStatsService {
    private final HibernateDao hibernateDao;

    public BusOnlineDataDeclareItem carbon() {
        BusOnlineDataDeclareItem item = hibernateDao.getSingle(new DaoCmd(Constant.MAPPER_BUS_ONLINE_DATA_DECLARE_STATS, null, BusOnlineDataDeclareItem.class));
        if (item == null) {
            item = new BusOnlineDataDeclareItem();
            item.setGasQuantity(new BigDecimal(0.0));
            item.setDieselOilQuantity(new BigDecimal(0.0));
            item.setNaturalGasQuantity(new BigDecimal(0.0));
            item.setElectricQuantity(new BigDecimal(0.0));
            return item;
        }
        item.setGasQuantity(Optional.ofNullable(item.getGasQuantity()).orElse(new BigDecimal(0.0)).multiply(new BigDecimal(2.7)));
        item.setDieselOilQuantity(Optional.ofNullable(item.getDieselOilQuantity()).orElse(new BigDecimal(0.0)).multiply(new BigDecimal(3.115)));
        item.setNaturalGasQuantity(Optional.ofNullable(item.getNaturalGasQuantity()).orElse(new BigDecimal(0.0)).multiply(new BigDecimal(1.96)));
        item.setElectricQuantity(Optional.ofNullable(item.getElectricQuantity()).orElse(new BigDecimal(0.0)).multiply(new BigDecimal(0.997)));
        return item;
    }

    public BusOnlineDataDeclareItem cutEmissions(Map<String, Object> params) {
        BusOnlineDataDeclareItem item = hibernateDao.getSingle(new DaoCmd(Constant.MAPPER_BUS_ONLINE_DATA_DECLARE_STATS, params, BusOnlineDataDeclareItem.class));
        if (item == null) {
            item = new BusOnlineDataDeclareItem();
            item.setGasQuantity(new BigDecimal(0.0));
            item.setDieselOilQuantity(new BigDecimal(0.0));
            item.setNaturalGasQuantity(new BigDecimal(0.0));
            item.setElectricQuantity(new BigDecimal(0.0));
            return item;
        }
        item.setGasQuantity(Optional.ofNullable(item.getGasQuantity()).orElse(new BigDecimal(0.0)).multiply(new BigDecimal(2.7)));
        item.setDieselOilQuantity(Optional.ofNullable(item.getDieselOilQuantity()).orElse(new BigDecimal(0.0)).multiply(new BigDecimal(3.115)));
        item.setNaturalGasQuantity(Optional.ofNullable(item.getNaturalGasQuantity()).orElse(new BigDecimal(0.0)).multiply(new BigDecimal(1.96)));
        item.setElectricQuantity(Optional.ofNullable(item.getElectricQuantity()).orElse(new BigDecimal(0.0)).multiply(new BigDecimal(0.997)));
        return item;
    }

    public BusOnlineDataDeclareStatsEnergyComparisonEchartView year(BusOnlineDataDeclareItem params) {
        return forYearAndQuarterAndMonthAndWeek(params);
    }

    public BusOnlineDataDeclareStatsEnergyComparisonEchartView quarter(BusOnlineDataDeclareItem params) {
        return forYearAndQuarterAndMonthAndWeek(params);
    }

    public BusOnlineDataDeclareStatsEnergyComparisonEchartView month(BusOnlineDataDeclareItem params) {
        return forYearAndQuarterAndMonthAndWeek(params);
    }

    public BusOnlineDataDeclareStatsEnergyComparisonEchartView week(BusOnlineDataDeclareItem params) {
        return forYearAndQuarterAndMonthAndWeek(params);
    }

    private BusOnlineDataDeclareStatsEnergyComparisonEchartView forYearAndQuarterAndMonthAndWeek(BusOnlineDataDeclareItem params) {
        List<BusOnlineDataDeclareStatsEnergyComparison> list = hibernateDao.getList(new DaoCmd(Constant.MAPPER_BUS_ONLINE_DATA_DECLARE_STATS_ENERGYCOMPARISON, BeanMapper.toMap(params), BusOnlineDataDeclareStatsEnergyComparison.class));

        BusOnlineDataDeclareStatsEnergyComparisonEchartView view = new BusOnlineDataDeclareStatsEnergyComparisonEchartView();
        view.getLegendData().addAll(Arrays.asList(BusOnlineDataDeclareStatsEnergyComparisonEchartView.DEFAULT_TYPE_NAMES));

        Date begin = params.getBegin();
        Date end = params.getEnd();
        Date end2 = new Date(begin.getTime());

        Map<String, BusOnlineDataDeclareStatsEnergyComparisonEchartView.Series> seriesMap = new HashMap<>();
        while (end2.before(end)) {
            String fmtTime = "";
            if (params.getCategory() == BusOnlineDataDeclareStats.CATEGORY_YEAR) {
                fmtTime = DateFormatUtils.format(DateUtil.utc2gmt8(end2), "yyyyMM");
            } else {
                fmtTime = DateFormatUtils.format(DateUtil.utc2gmt8(end2), "yyyyMMdd");
            }

            if (list == null || list.isEmpty()) {
                view.getLegendData().forEach(o -> {
                    BusOnlineDataDeclareStatsEnergyComparisonEchartView.Series series = seriesMap.get(o);
                    if (series == null) {
                        series = new BusOnlineDataDeclareStatsEnergyComparisonEchartView.Series();
                        seriesMap.put(o, series);
                    }
                    series.setName(o);
                    series.getData().add(new BigDecimal(0.0));
                });
                continue;
            }

            Map<String, Boolean> existMap = new HashMap<>();
            for (BusOnlineDataDeclareStatsEnergyComparison stats : list) {
                if (StringUtils.equals(stats.getFmtTime(), fmtTime)) {
                    BusOnlineDataDeclareStatsEnergyComparisonEchartView.Series series = seriesMap.get(stats.getTypeName());
                    if (series == null) {
                        series = new BusOnlineDataDeclareStatsEnergyComparisonEchartView.Series();
                        seriesMap.put(stats.getTypeName(), series);
                    }
                    series.setName(stats.getTypeName());
                    series.getData().add(Optional.ofNullable(stats.getQuantity()).orElse(new BigDecimal(0.0)));

                    existMap.put(stats.getTypeName(), Boolean.TRUE);
                }
            }

            for (String name : view.getLegendData()) {
                Boolean exist = existMap.get(name);
                if (exist != null) {
                    continue;
                }
                BusOnlineDataDeclareStatsEnergyComparisonEchartView.Series series = seriesMap.get(name);

                if (series == null) {
                    series = new BusOnlineDataDeclareStatsEnergyComparisonEchartView.Series();
                    series.setName(name);
                    seriesMap.put(name, series);
                }
                series.getData().add(new BigDecimal(0.0));
            }

            Calendar cl = Calendar.getInstance();
            cl.setTime(end2);
            if (params.getCategory() == BusOnlineDataDeclareStats.CATEGORY_YEAR) {
                cl.add(Calendar.MONTH, 1);
            } else if (params.getCategory() == BusOnlineDataDeclareStats.CATEGORY_QUARTER) {
                cl.add(Calendar.DATE, 15);
            } else {
                cl.add(Calendar.DATE, 1);
            }
            end2 = cl.getTime();
        }
        view.getLegendData().forEach(k -> {
            view.getSeries().add(seriesMap.get(k));
        });

        return view;
    }
}
