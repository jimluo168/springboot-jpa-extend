package com.bms.industry.dao;

import com.bms.entity.BusSiteAudit;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 站点审核 repository
 *
 * @author zouyongcan
 * @date 2020/3/18
 */
public interface BusSiteAuditRepository extends PagingAndSortingRepository<BusSiteAudit, Long>, JpaSpecificationExecutor<BusSiteAudit> {
}
