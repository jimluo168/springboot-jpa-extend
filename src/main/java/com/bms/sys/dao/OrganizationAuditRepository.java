package com.bms.sys.dao;

import com.bms.entity.Organization;
import com.bms.entity.OrganizationAudit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 机构组织DAO.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@Repository
public interface OrganizationAuditRepository extends PagingAndSortingRepository<OrganizationAudit, Long> {
}
