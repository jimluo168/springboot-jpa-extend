package com.springboot.jpa.demo.sys.service;

import com.springboot.jpa.demo.entity.Menu;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO(类的简要说明)
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@SpringBootTest
@AutoConfigureMockMvc
//@Transactional
class MenuServiceTest {

    @Autowired
    private MenuService menuService;

    @Test
    void insert() {
        Menu sys = new Menu();
        sys.setName("系统管理");
        sys.setPath("sys");
        sys.setIndex(100);
        menuService.insert(sys);

        Menu user = new Menu();
        user.setIndex(1);
        user.setName("用户管理");
        user.setPath("users");
        user.setParent(sys);
        user = menuService.insert(user);

        Menu btn = new Menu();
        btn.setIndex(1);
        btn.setName("查询");
        btn.setType(Menu.TYPE_BTN);
        btn.setPermissionCode("user_list");
        btn.setParent(user);
        menuService.insert(btn);

        btn = new Menu();
        btn.setIndex(2);
        btn.setName("新增");
        btn.setType(Menu.TYPE_BTN);
        btn.setPermissionCode("user_create");
        btn.setParent(user);
        menuService.insert(btn);

        btn = new Menu();
        btn.setIndex(3);
        btn.setName("编辑");
        btn.setType(Menu.TYPE_BTN);
        btn.setPermissionCode("user_edit");
        btn.setParent(user);
        menuService.insert(btn);

        btn = new Menu();
        btn.setIndex(4);
        btn.setName("删除");
        btn.setType(Menu.TYPE_BTN);
        btn.setPermissionCode("user_delete");
        btn.setParent(user);
        menuService.insert(btn);

        btn = new Menu();
        btn.setIndex(5);
        btn.setName("重置密码");
        btn.setType(Menu.TYPE_BTN);
        btn.setPermissionCode("user_resetpasswd");
        btn.setParent(user);
        menuService.insert(btn);

        btn = new Menu();
        btn.setIndex(6);
        btn.setName("禁用/启用");
        btn.setType(Menu.TYPE_BTN);
        btn.setPermissionCode("user_status");
        btn.setParent(user);
        menuService.insert(btn);

        btn = new Menu();
        btn.setIndex(7);
        btn.setName("详情");
        btn.setType(Menu.TYPE_BTN);
        btn.setPermissionCode("user_details");
        btn.setParent(user);
        menuService.insert(btn);


        Menu role = new Menu();
        role.setIndex(2);
        role.setName("角色管理");
        role.setPath("roles");
        role.setParent(sys);
        menuService.insert(role);

        btn = new Menu();
        btn.setIndex(1);
        btn.setName("查询");
        btn.setType(Menu.TYPE_BTN);
        btn.setPermissionCode("role_list");
        btn.setParent(role);
        menuService.insert(btn);

        btn = new Menu();
        btn.setIndex(2);
        btn.setName("新增");
        btn.setType(Menu.TYPE_BTN);
        btn.setPermissionCode("role_create");
        btn.setParent(role);
        menuService.insert(btn);

        btn = new Menu();
        btn.setIndex(3);
        btn.setName("编辑");
        btn.setType(Menu.TYPE_BTN);
        btn.setPermissionCode("role_edit");
        btn.setParent(role);
        menuService.insert(btn);

        btn = new Menu();
        btn.setIndex(4);
        btn.setName("删除");
        btn.setType(Menu.TYPE_BTN);
        btn.setPermissionCode("role_delete");
        btn.setParent(role);
        menuService.insert(btn);

        btn = new Menu();
        btn.setIndex(5);
        btn.setName("详情");
        btn.setType(Menu.TYPE_BTN);
        btn.setPermissionCode("role_details");
        btn.setParent(role);
        menuService.insert(btn);


        Menu oplog = new Menu();
        oplog.setIndex(3);
        oplog.setName("操作日志");
        oplog.setPath("oplogs");
        oplog.setParent(sys);
        menuService.insert(oplog);

        btn = new Menu();
        btn.setIndex(1);
        btn.setName("查询");
        btn.setType(Menu.TYPE_BTN);
        btn.setPermissionCode("oplog_list");
        btn.setParent(oplog);
        menuService.insert(btn);

        // 字典管理
        Menu dict = new Menu();
        dict.setIndex(2);
        dict.setName("字典管理");
        dict.setPath("dict");
        dict.setParent(sys);
        menuService.insert(dict);

        btn = new Menu();
        btn.setIndex(1);
        btn.setName("查询");
        btn.setType(Menu.TYPE_BTN);
        btn.setPermissionCode("dict_list");
        btn.setParent(dict);
        menuService.insert(btn);

        btn = new Menu();
        btn.setIndex(2);
        btn.setName("新增");
        btn.setType(Menu.TYPE_BTN);
        btn.setPermissionCode("dict_create");
        btn.setParent(dict);
        menuService.insert(btn);

        btn = new Menu();
        btn.setIndex(3);
        btn.setName("编辑");
        btn.setType(Menu.TYPE_BTN);
        btn.setPermissionCode("dict_edit");
        btn.setParent(dict);
        menuService.insert(btn);

        btn = new Menu();
        btn.setIndex(4);
        btn.setName("删除");
        btn.setType(Menu.TYPE_BTN);
        btn.setPermissionCode("dict_delete");
        btn.setParent(dict);
        menuService.insert(btn);

        btn = new Menu();
        btn.setIndex(5);
        btn.setName("详情");
        btn.setType(Menu.TYPE_BTN);
        btn.setPermissionCode("dict_details");
        btn.setParent(dict);
        menuService.insert(btn);

        btn = new Menu();
        btn.setIndex(6);
        btn.setName("禁用/启用");
        btn.setType(Menu.TYPE_BTN);
        btn.setPermissionCode("dict_status");
        btn.setParent(dict);
        menuService.insert(btn);


    }
}