package com.bms.sys.service;

import com.bms.Constant;
import com.bms.ErrorCodes;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.util.JpaUtils;
import com.bms.entity.Role;
import com.bms.sys.dao.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

import static com.bms.common.domain.BaseEntity.DELETE_TRUE;

/**
 * Created by zouyongcan on 2020/3/13
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    private final FlakeId flakeId;

    private final HibernateDao hibernateDao;

    public Role insert(Role role) {
        role.setId(flakeId.next());
        return roleRepository.save(role);
    }

    @Transactional(readOnly = true)
    public PageList<Role> page(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_ROLE_PAGE, queryParams));
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
    public boolean existsName(String name, Long id) {
        if (id == null) {
            return roleRepository.countByName(name) > 0;
        }
        return roleRepository.countByNameAndIdNot(name, id) > 0;
    }
}
