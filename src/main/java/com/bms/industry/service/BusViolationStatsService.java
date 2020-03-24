package com.bms.industry.service;

import com.bms.Constant;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.industry.view.BusViolationStatsCompany;
import com.bms.industry.view.BusViolationStatsDriver;
import com.bms.industry.view.BusViolationStatsType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
}
