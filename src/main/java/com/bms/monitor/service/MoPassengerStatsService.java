package com.bms.monitor.service;

import com.bms.Constant;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.util.BeanMapper;
import com.bms.entity.BusRoute;
import com.bms.entity.Organization;
import com.bms.industry.service.BusRouteService;
import com.bms.monitor.view.*;
import com.bms.sys.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 客流量动态.
 *
 * @author luojimeng
 * @date 2020/4/16
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class MoPassengerStatsService {
    private final HibernateDao hibernateDao;

    @Transactional(readOnly = true)
    public PageList<MoPassengerListView> pageList(PageRequest pageRequest, MoPassengerListView view) {
        return hibernateDao.findAll(pageRequest, new DaoCmd("mo_passenger_stats_page_list", BeanMapper.toMap(view), MoPassengerListView.class));
    }

    @Transactional(readOnly = true)
    public PageList<MoPassengerListView> pageTop(PageRequest pageRequest, MoPassengerListView view) {
        return hibernateDao.findAll(pageRequest, new DaoCmd("mo_passenger_stats_page_top", BeanMapper.toMap(view), MoPassengerListView.class));
    }

    @Transactional(readOnly = true)
    public List<MoPassengerListView> all(MoPassengerListView view) {
        return hibernateDao.getList(new DaoCmd("mo_passenger_stats_all", BeanMapper.toMap(view), MoPassengerListView.class));
    }
}
