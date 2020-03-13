package com.bms.sys.service;

import com.bms.common.domain.BaseEntity;
import com.bms.entity.Menu;
import com.bms.entity.Role;
import com.bms.sys.dao.MenuRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO(类的简要说明)
 *
 * @author luojimeng
 * @date 2020/3/13
 */
@SpringBootTest
@AutoConfigureMockMvc
//@Transactional
class RoleServiceTest {

    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private MenuRepository menuRepository;


    @Test
    public void test() {
        long roleId = 434272239429488640L;
        Role role = roleService.findById(roleId);
        Iterable<Menu> all = menuRepository.findAll();
        role.setMenuList((List<Menu>) all);
        roleService.updateById(role.getId(), role);
    }
}