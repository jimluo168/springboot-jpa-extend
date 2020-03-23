package com.bms.industry.service;

import com.bms.common.dao.HibernateDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


}
