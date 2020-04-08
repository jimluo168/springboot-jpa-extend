package com.bms.industry.dao;

import com.bms.entity.BusOnlineDataDeclare;
import com.bms.entity.BusOnlineDataDeclareStats;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 数据申报统计表.
 *
 * @author zouyongcan
 * @date 2020/3/30
 */
@Repository
public interface BusOnlineDataDeclareStatsRepository extends PagingAndSortingRepository<BusOnlineDataDeclareStats, Long> {
}
