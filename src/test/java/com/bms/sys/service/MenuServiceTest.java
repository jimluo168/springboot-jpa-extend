package com.bms.sys.service;

import com.bms.common.config.flake.FlakeId;
import com.bms.entity.Menu;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private FlakeId flakeId;

    @Test
    void insert() {
        Menu menu = new Menu();
        menu.setName("公交行业管理系统");
        menu.setPath("/industry");
        menuService.insert(menu);
    }
}