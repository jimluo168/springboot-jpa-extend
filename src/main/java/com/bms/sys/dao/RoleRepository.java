package com.bms.sys.dao;

import com.bms.entity.Role;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zouyongcan on 2020/3/13
 */
@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
    int countByNameAndIdNotAndDeleted(String name, Long id, int deleted);

    int countByNameAndDeleted(String name, int deleted);
}
