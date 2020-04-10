package com.bms.industry.sync.busbasic.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO(类的简要说明)
 *
 * @author luojimeng
 * @date 2020/4/10
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class LineApiTest {

    @Autowired
    private LineApi lineApi;

    @Test
    void getAll() throws IOException {
        lineApi.getAll();
        Assertions.assertTrue(true);
    }
}