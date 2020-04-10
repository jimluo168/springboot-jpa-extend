package com.bms.industry.sync.busbasic.api;

import com.bms.industry.sync.Http;
import com.bms.industry.sync.busbasic.api.LoginApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO(类的简要说明)
 *
 * @author luojimeng
 * @date 2020/4/5
 */
@SpringBootTest
@AutoConfigureMockMvc
class LoginApiTest {

    @Autowired
    private Http http;
    @Autowired
    private LoginApi loginApi;

    @Test
    void login() throws IOException {
        http.setHeader("Content-Type", "application/x-www-form-urlencoded");

        String token = loginApi.login();
        assertNotNull(token);
    }
}