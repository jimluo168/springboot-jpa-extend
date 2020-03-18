package com.bms.industry.dao;

import com.bms.entity.BusTerminalAudit;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 场站审核 repository
 *
 * @author zouyongcan
 * @date 2020/3/17
 */
@Repository
public interface BusTerminalAuditRepository extends PagingAndSortingRepository<BusTerminalAudit, Long>, JpaSpecificationExecutor<BusTerminalAudit> {
}
