package com.bms.industry.service;

import com.bms.Constant;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.industry.view.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 事件统计分析.
 *
 * @author luojimeng
 * @date 2020/3/24
 */
@Service
@Transactional(rollbackFor = RuntimeException.class, readOnly = true)
@RequiredArgsConstructor
public class BusViolationStatsService {
    private final HibernateDao hibernateDao;

    /**
     * 违规类型.
     */
    public List<BusViolationStatsType> type(Map<String, Object> params) {
        return hibernateDao.getList(new DaoCmd(Constant.MAPPER_BUS_VIOLATION_STATS_TYPE, params, BusViolationStatsType.class));
    }

    /**
     * 公司违规.
     */
    public List<BusViolationStatsCompany> company(Map<String, Object> params) {
        return hibernateDao.getList(new DaoCmd(Constant.MAPPER_BUS_VIOLATION_STATS_COMPANY, params, BusViolationStatsCompany.class));
    }

    /**
     * 司机违规.
     */
    public List<BusViolationStatsDriver> driver(Map<String, Object> params) {
        return hibernateDao.getList(new DaoCmd(Constant.MAPPER_BUS_VIOLATION_STATS_DRIVER, params, BusViolationStatsDriver.class));
    }

    public BusViolationStatsEchartView week(Map<String, Object> params) {
        return weekOrMonth(params);
    }

    private BusViolationStatsEchartView weekOrMonth(Map<String, Object> params) {
        List<BusViolationStatsWeek> list = hibernateDao.getList(new DaoCmd(Constant.MAPPER_BUS_VIOLATION_STATS_WEEK, params, BusViolationStatsWeek.class));
        if (list == null || list.isEmpty()) {
            return new BusViolationStatsEchartView();
        }
        BusViolationStatsEchartView view = new BusViolationStatsEchartView();

        List<String> legendData = view.getLegendData();
        list.forEach(o -> {
            legendData.add(o.getText());
        });
        Date begin = (Date) params.get("begin");
        Date end = (Date) params.get("end");
        Date end2 = new Date(begin.getTime());
        List<BusViolationStatsEchartView.Series> seriesList = view.getSeries();

        Map<String, BusViolationStatsEchartView.Series> seriesMap = new HashMap<>();
        while (end2.before(end)) {
            Calendar cl = Calendar.getInstance();
            cl.setTime(end2);
            cl.add(Calendar.DAY_OF_MONTH, 1);
            end2 = cl.getTime();
            params.put("begin", begin);
            params.put("end", end2);
            List<BusViolationStatsWeek> dayList = hibernateDao.getList(new DaoCmd(Constant.MAPPER_BUS_VIOLATION_STATS_WEEK, params, BusViolationStatsWeek.class));
            begin = new Date(end2.getTime() + 1);
            if (dayList == null || dayList.isEmpty()) {
                legendData.forEach(o -> {
                    BusViolationStatsEchartView.Series evs = seriesMap.get(o);
                    if (evs == null) {
                        evs = new BusViolationStatsEchartView.Series();
                        evs.setName(o);
                        seriesMap.put(o, evs);
                    }
                    evs.getData().add(0);
                });
                continue;
            }

            list.forEach(o -> {
                BusViolationStatsWeek w = new BusViolationStatsWeek();
                w.setNum(0);
                w.setText(o.getText());
                w.setType(o.getType());

                AtomicReference<BusViolationStatsWeek> ar = new AtomicReference(w);
                for (BusViolationStatsWeek d : dayList) {
                    if (o.getText().equals(d.getText())) {
                        ar.set(d);
                        break;
                    }
                }
                BusViolationStatsWeek arw = ar.get();

                BusViolationStatsEchartView.Series evs = seriesMap.get(arw.getText());
                if (evs == null) {
                    evs = new BusViolationStatsEchartView.Series();
                    evs.setName(arw.getText());
                    seriesMap.put(arw.getText(), evs);
                }
                evs.getData().add(arw.getNum());
            });

        }

        legendData.forEach(o -> {

            BusViolationStatsEchartView.Series evs = seriesMap.get(o);
            seriesList.add(evs);
        });

        return view;
    }

    public BusViolationStatsEchartView month(Map<String, Object> params) {
        return weekOrMonth(params);
    }

    public BusViolationStatsEchartView year(Map<String, Object> params) {
        List<BusViolationStatsWeek> list = hibernateDao.getList(new DaoCmd(Constant.MAPPER_BUS_VIOLATION_STATS_WEEK, params, BusViolationStatsWeek.class));
        if (list == null || list.isEmpty()) {
            return new BusViolationStatsEchartView();
        }
        BusViolationStatsEchartView view = new BusViolationStatsEchartView();

        List<String> legendData = view.getLegendData();
        list.forEach(o -> {
            legendData.add(o.getText());
        });
        Date begin = (Date) params.get("begin");
        Date end = (Date) params.get("end");
        Date end2 = new Date(begin.getTime());
        List<BusViolationStatsEchartView.Series> seriesList = view.getSeries();

        Map<String, BusViolationStatsEchartView.Series> seriesMap = new HashMap<>();
        while (end2.before(end)) {
            Calendar cl = Calendar.getInstance();
            cl.setTime(end2);
            cl.add(Calendar.MONTH, 1);
            cl.set(Calendar.DAY_OF_MONTH, cl.getActualMaximum(Calendar.DAY_OF_MONTH));
            end2 = cl.getTime();
            params.put("begin", begin);
            params.put("end", end2);
            List<BusViolationStatsWeek> dayList = hibernateDao.getList(new DaoCmd(Constant.MAPPER_BUS_VIOLATION_STATS_WEEK, params, BusViolationStatsWeek.class));
            begin = new Date(end2.getTime() + 1);
            if (dayList == null || dayList.isEmpty()) {
                legendData.forEach(o -> {
                    BusViolationStatsEchartView.Series evs = seriesMap.get(o);
                    if (evs == null) {
                        evs = new BusViolationStatsEchartView.Series();
                        evs.setName(o);
                        seriesMap.put(o, evs);
                    }
                    evs.getData().add(0);
                });
                continue;
            }

            list.forEach(o -> {
                BusViolationStatsWeek w = new BusViolationStatsWeek();
                w.setNum(0);
                w.setText(o.getText());
                w.setType(o.getType());

                AtomicReference<BusViolationStatsWeek> ar = new AtomicReference(w);
                for (BusViolationStatsWeek d : dayList) {
                    if (o.getText().equals(d.getText())) {
                        ar.set(d);
                        break;
                    }
                }
                BusViolationStatsWeek arw = ar.get();

                BusViolationStatsEchartView.Series evs = seriesMap.get(arw.getText());
                if (evs == null) {
                    evs = new BusViolationStatsEchartView.Series();
                    evs.setName(arw.getText());
                    seriesMap.put(arw.getText(), evs);
                }
                evs.getData().add(arw.getNum());
            });

        }

        legendData.forEach(o -> {

            BusViolationStatsEchartView.Series evs = seriesMap.get(o);
            seriesList.add(evs);
        });

        return view;
    }
}
