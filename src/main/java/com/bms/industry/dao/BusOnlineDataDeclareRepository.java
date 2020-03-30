package com.bms.industry.dao;

import com.bms.entity.BusOnlineDataDeclare;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 申报管理
 *
 * @author zouyongcan
 * @date 2020/3/30
 */
@Repository
public interface BusOnlineDataDeclareRepository extends PagingAndSortingRepository<BusOnlineDataDeclare, Long> {
}
