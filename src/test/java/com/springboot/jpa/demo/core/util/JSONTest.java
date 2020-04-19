package com.springboot.jpa.demo.core.util;

import com.springboot.jpa.demo.entity.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * JSON测试类.
 *
 * @author luojimeng
 * @date 2020/4/12
 */
class JSONTest {

    @Test
    void toJSONString() {
        List<User> list = new ArrayList<>();
        User user = new User();
        user.setId(1L);
        user.setAccount("test01");
        user.setPasswd("123456");
        user.setRealName("test01");
        list.add(user);

        String str = JSON.toJSONString(list);

        System.out.println(str);
    }
}