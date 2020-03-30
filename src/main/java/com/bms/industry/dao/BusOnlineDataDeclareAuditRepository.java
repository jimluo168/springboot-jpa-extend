package com.bms.industry.dao;

import com.bms.entity.BusOnlineDataDeclareAudit;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 申报管理
 *
 * @author zouyongcan
 * @date 2020/3/30
 */
@Repository
public interface BusOnlineDataDeclareAuditRepository extends PagingAndSortingRepository<BusOnlineDataDeclareAudit, Long> {
}
