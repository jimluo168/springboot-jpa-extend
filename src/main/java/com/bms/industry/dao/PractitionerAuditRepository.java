package com.bms.industry.dao;

import com.bms.entity.PractitionerAudit;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 从业人员 repository
 *
 * @author zouyongcan
 * @date 2020/3/17
 */
@Repository
public interface PractitionerAuditRepository extends PagingAndSortingRepository<PractitionerAudit, Long>, JpaSpecificationExecutor<PractitionerAudit> {
}
