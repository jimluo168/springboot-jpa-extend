package com.bms.industry.dao;

import com.bms.entity.SuggestAudit;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 投诉建议审核
 *
 * @author zouyongcan
 * @date 2020/3/19
 */
@Repository
public interface SuggestAuditRepository extends PagingAndSortingRepository<SuggestAudit, Long>, JpaSpecificationExecutor<SuggestAudit> {
}
