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
 * 车队API.
 *
 * @author luojimeng
 * @date 2020/4/9
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TeamApiTest {

    @Autowired
    private TeamApi teamApi;

    @Test
    void getAll() throws IOException {
        teamApi.getAll();
        Assertions.assertTrue(true);
    }
}