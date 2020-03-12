package com.bms.sys.dao;

import com.bms.entity.Organization;
import com.bms.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * TODO(类的简要说明)
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@Repository
public interface OrganizationRepository extends PagingAndSortingRepository<Organization, Long> {
}
