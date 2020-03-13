package com.bms.sys.service;

import com.bms.common.config.flake.FlakeId;
import com.bms.entity.Menu;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO(类的简要说明)
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MenuServiceTest {

    @Autowired
    private MenuService menuService;

    @Test
    void insert() {
        Menu sys = new Menu();
        sys.setName("系统管理");
        sys.setPath("sys");
        sys.setIndex(100);
        sys = menuService.insert(sys);

        List<Menu> children = new ArrayList<>();

        Menu m = new Menu();
        m.setIndex(1);
        m.setName("用户管理");
        m.setPath("users");
        m.setParent(sys);
        m = menuService.insert(m);
        children.add(m);

        m = new Menu();
        m.setIndex(2);
        m.setName("角色管理");
        m.setPath("roles");
        m.setParent(sys);
        m = menuService.insert(m);
        children.add(m);

        sys.setChildren(children);
        menuService.updateById(sys.getId(),sys);

        Menu basic = new Menu();
        basic.setName("基础业务管理");
        basic.setPath("basic_business");
        basic.setIndex(1);
        basic = menuService.insert(basic);

        children = new ArrayList<>();

        m = new Menu();
        m.setIndex(1);
        m.setName("公交企业管理");
        m.setPath("bus_org");
        m.setParent(basic);
        m = menuService.insert(m);
        children.add(m);

        m = new Menu();
        m.setIndex(2);
        m.setName("从业人员管理");
        m.setPath("employee");
        m.setParent(basic);
        m = menuService.insert(m);
        children.add(m);

        m = new Menu();
        m.setIndex(3);
        m.setName("公交车辆管理");
        m.setPath("bus_vehicle");
        m.setParent(basic);
        m = menuService.insert(m);
        children.add(m);

        m = new Menu();
        m.setIndex(4);
        m.setName("公交路线管理");
        m.setPath("bus_rote");
        m.setParent(basic);
        m = menuService.insert(m);
        children.add(m);

        m = new Menu();
        m.setIndex(5);
        m.setName("公交场站管理");
        m.setPath("bus_terminal");
        m.setParent(basic);
        m = menuService.insert(m);
        children.add(m);

        m = new Menu();
        m.setIndex(6);
        m.setName("公交站点管理");
        m.setPath("bus_site");
        m.setParent(basic);
        m = menuService.insert(m);
        children.add(m);

        basic.setChildren(children);
        menuService.updateById(basic.getId(),basic);

    }
}