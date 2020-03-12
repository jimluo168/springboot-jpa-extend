package com.bms.sys.dao;

import com.bms.entity.Organization;
import com.bms.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 机构组织DAO.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@Repository
public interface OrganizationRepository extends PagingAndSortingRepository<Organization, Long>, JpaSpecificationExecutor<Organization> {
//    @Query(value = "from Organization o where 1=1 and if(:name!='',o.name) like ?1 and o.level = ?2 and o.deleted=?3 order by o.createDate desc")
//    Page<Organization> page(String name, int level, int deleted, Pageable pageable);
}
