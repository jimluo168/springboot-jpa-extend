package com.springboot.jpa.demo.sys.dao;

import com.springboot.jpa.demo.entity.Role;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 角色管理.
 *
 * @author luojimeng
 * @date 2020/4/19
 */
@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
    boolean existsByNameAndIdNotAndDeleted(String name, Long id, int deleted);

    boolean existsByNameAndDeleted(String name, int deleted);
}
