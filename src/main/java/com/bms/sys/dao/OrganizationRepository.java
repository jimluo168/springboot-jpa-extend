package com.bms.sys.dao;

import com.bms.entity.Organization;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 机构组织DAO.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@Repository
public interface OrganizationRepository extends PagingAndSortingRepository<Organization, Long>, JpaSpecificationExecutor<Organization> {
    Organization findByName(String name);

    int countByNameAndDeleted(String name, int deleted);

    int countByNameAndIdNotAndDeleted(String name, Long id, int deleted);

    Organization findByoId(String oId);

    List<Organization> findByParentIsNullAndDeletedOrderByIndexAsc(int deleted);

    List<Organization> findByParentIdInAndDeletedOrderByIndexAsc(List<Long> parentIdList, int deleted);
}
