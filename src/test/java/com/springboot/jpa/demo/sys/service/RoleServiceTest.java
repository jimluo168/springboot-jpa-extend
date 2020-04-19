package com.springboot.jpa.demo.sys.service;

import com.springboot.jpa.demo.entity.Menu;
import com.springboot.jpa.demo.entity.Role;
import com.springboot.jpa.demo.sys.dao.MenuRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TODO(类的简要说明)
 *
 * @author luojimeng
 * @date 2020/3/13
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RoleServiceTest {

    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuRepository menuRepository;

    @Test
    public void insert() {
        Role role = new Role();
        role.setName("超级管理员");
        roleService.insert(role);
    }


    @Test
    public void authorize() {
        long roleId = 1L;
        Role role = roleService.findById(roleId);
        Iterable<Menu> all = menuRepository.findAll();
        role.setMenuList((List<Menu>) all);
        roleService.updateById(role.getId(), role);
    }
}