package com.bms.sys.service;

import com.bms.common.config.flake.FlakeId;
import com.bms.entity.Organization;
import com.bms.entity.Role;
import com.bms.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;
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
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private RoleService roleService;

    @Test
    public void insert() {
        long roleId = 434272239429488640L;
        Role role = roleService.findById(roleId);
        long orgId = 433870687619387392L;
        Organization organization = organizationService.findById(orgId);
//        User user = new User();
//        user.setAccount("admin");
//        user.setPasswd("123456");
//        user.setOrganization(organization);
//        user.setRealName("管理员");
//        user.setRemark("管理员-不要删除");
//        user.setRole(role);
//        userService.insert(user);


        User user = new User();
//        user.setAccount("luojimeng");
//        user.setPasswd("123456");
//        user.setOrganization(organization);
//        user.setRealName("罗继猛");
//        user.setRemark("不要删除");
//        user.setRole(role);
//        userService.insert(user);
//
//        user = new User();
//        user.setAccount("zouyongcan");
//        user.setPasswd("123456");
//        user.setOrganization(organization);
//        user.setRealName("邹永灿");
//        user.setRemark("不要删除");
//        user.setRole(role);
//        userService.insert(user);
//
//        user = new User();
//        user.setAccount("luyueya");
//        user.setPasswd("123456");
//        user.setOrganization(organization);
//        user.setRealName("卢月牙");
//        user.setRemark("不要删除");
//        user.setRole(role);
//        userService.insert(user);
//
//        user = new User();
//        user.setAccount("zhouhaiming");
//        user.setPasswd("123456");
//        user.setOrganization(organization);
//        user.setRealName("周海明");
//        user.setRemark("不要删除");
//        user.setRole(role);
//        userService.insert(user);
//
//        user = new User();
//        user.setAccount("hutian");
//        user.setPasswd("123456");
//        user.setOrganization(organization);
//        user.setRealName("胡甜");
//        user.setRemark("不要删除");
//        user.setRole(role);
//        userService.insert(user);

//        user = new User();
//        user.setAccount("test01");
//        user.setPasswd("123456");
//        user.setOrganization(organization);
//        user.setRealName("测试人1");
//        user.setRemark("测试专用");
//        user.setRole(role);
//        userService.insert(user);
//
//
//        user = new User();
//        user.setAccount("test02");
//        user.setPasswd("123456");
//        user.setOrganization(organization);
//        user.setRealName("测试人1");
//        user.setRemark("测试专用");
//        user.setRole(role);
//        userService.insert(user);

//        user = new User();
//        user.setAccount("guyingyang");
//        user.setPasswd("123456");
//        user.setOrganization(organization);
//        user.setRealName("顾迎阳");
//        user.setRemark("");
//        user.setRole(role);
//        userService.insert(user);

        user = new User();
        user.setAccount("hangtian01");
        user.setPasswd("123456");
        user.setOrganization(organization);
        user.setRealName("航天测试01");
        user.setRemark("航天测试01 请勿其他人员删除和使用");
        user.setRole(role);
        userService.insert(user);

        user = new User();
        user.setAccount("hangtian02");
        user.setPasswd("123456");
        user.setOrganization(organization);
        user.setRealName("航天测试02");
        user.setRemark("航天测试02 请勿其他人员删除和使用");
        user.setRole(role);
        userService.insert(user);
    }

}