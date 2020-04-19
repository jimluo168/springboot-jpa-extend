package com.springboot.jpa.demo.sys.service;

import com.springboot.jpa.demo.ErrorCodes;
import com.springboot.jpa.demo.core.config.flake.FlakeId;
import com.springboot.jpa.demo.core.domain.PageList;
import com.springboot.jpa.demo.core.domain.PageRequest;
import com.springboot.jpa.demo.core.util.JpaUtils;
import com.springboot.jpa.demo.entity.Role;
import com.springboot.jpa.demo.sys.dao.RoleMapper;
import com.springboot.jpa.demo.sys.dao.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.springboot.jpa.demo.core.domain.BaseEntity.DELETE_FALSE;
import static com.springboot.jpa.demo.core.domain.BaseEntity.DELETE_TRUE;

/**
 * 角色管理.
 *
 * @author luojimeng
 * @date 2020/4/19
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final FlakeId flakeId;
    private final RoleMapper roleMapper;


    public Role insert(Role role) {
        role.setId(flakeId.next());
        return roleRepository.save(role);
    }

    @Transactional(readOnly = true)
    public PageList<Role> page(PageRequest pageRequest, Role role) {
        return roleMapper.findAll(pageRequest, role);
    }

    @Transactional(readOnly = true)
    public Role findById(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            return role.get();
        }
        throw ErrorCodes.build(ErrorCodes.DATA_NOT_EXIST);
    }

    public Role deleteById(Long id) {
        Role role = this.findById(id);
        role.setDeleted(DELETE_TRUE);
        return role;
    }

    public Role updateById(Long id, Role updateBody) {
        Role role = this.findById(id);
        JpaUtils.copyNotNullProperties(updateBody, role);
        return role;
    }

    @Transactional(readOnly = true)
    public boolean existsByName(String name, Long id) {
        if (id == null) {
            return roleRepository.existsByNameAndDeleted(name, DELETE_FALSE);
        }
        return roleRepository.existsByNameAndIdNotAndDeleted(name, id, DELETE_FALSE);
    }
}
