package com.bms.sys.service;

import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.exception.ExceptionFactory;
import com.bms.common.util.JpaUtils;
import com.bms.entity.Role;
import com.bms.sys.Constant;
import com.bms.sys.dao.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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

    public PageList<Role> page(PageRequest pageRequest, String name) {
        Map<String, Object> params = new HashMap<>();
        String likeName = name;
        if (StringUtils.isNotBlank(likeName)) {
            likeName = likeName + "%";
        }
        params.put("name", likeName);
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_ROLE_PAGE, params));
    }

    public Role findById(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            return role.get();
        }
        throw ExceptionFactory.dataNotExistException();
    }

    public Role deleteById(Long id) {
        Role role = this.findById(id);
        role.setDeleted(DELETE_TRUE);
        return role;
    }

    public Role updateById(Long id, Role updateBody) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            Role value = role.get();
            JpaUtils.copyNotNullProperties(updateBody, value);
            return value;
        } else {
            throw ExceptionFactory.dataNotExistException();
        }
    }


}
