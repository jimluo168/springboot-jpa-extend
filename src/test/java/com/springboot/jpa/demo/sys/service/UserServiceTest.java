package com.springboot.jpa.demo.sys.service;

import com.springboot.jpa.demo.entity.Role;
import com.springboot.jpa.demo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO(类的简要说明)
 *
 * @author luojimeng
 * @date 2020/3/13
 */
@SpringBootTest
@AutoConfigureMockMvc
//@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Test
    public void insert() {
        long roleId = 1L;
        Role role = roleService.findById(roleId);

        User user = new User();
        user.setAccount("test01");
        user.setPasswd("123456");
        user.setRealName("test01");
        user.setRemark("test01备注信息");
        user.setRole(role);
        userService.insert(user);
    }

}