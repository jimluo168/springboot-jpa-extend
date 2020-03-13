package com.bms.sys.service;

import com.bms.common.config.flake.FlakeId;
import com.bms.entity.Organization;
import com.bms.entity.User;
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
 * @date 2020/3/13
 */
@SpringBootTest
@AutoConfigureMockMvc
//@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private OrganizationService organizationService;


    @Test
    public void insert() {
        long orgId = 433870687619387392L;
        Organization organization = organizationService.findById(orgId);
        User user = new User();
        user.setAccount("admin");
        user.setPasswd("123456");
        user.setOrganization(organization);
        user.setRealName("管理员");
        user.setRemark("管理员-不要删除");
        userService.insert(user);
    }

}