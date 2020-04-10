package com.bms.industry.sync.busbasic.api;

import com.bms.industry.sync.Http;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * API基础测试类.
 *
 * @author luojimeng
 * @date 2020/4/9
 */
@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractApiTest {

    @Autowired
    private Http http;
    @Autowired
    private LoginApi loginApi;

    @BeforeEach
    void setUp() throws IOException {
        http.setHeader("Content-Type", "application/x-www-form-urlencoded");
        String accessToken = loginApi.login();
        http.setHeader("accessToken", accessToken);

    }
}
