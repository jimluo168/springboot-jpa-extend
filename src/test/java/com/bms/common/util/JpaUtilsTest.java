package com.bms.common.util;

import com.bms.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author luojimeng
 * @date 2020/3/29
 */
class JpaUtilsTest {

    @Test
    void copyNotNullProperties() {
        User user = new User();
        user.setId(1L);
        user.setRealName("xxx");

        User target = new User();
        target.setId(2L);
        target.setRealName("aaa");
        target.setPasswd("passwd");
        target.setSalt("salt");
        target.setAccount("test01");
        JpaUtils.copyNotNullProperties(user, target);
        System.out.println(target);
    }
}