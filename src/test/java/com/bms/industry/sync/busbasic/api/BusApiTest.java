package com.bms.industry.sync.busbasic.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO(类的简要说明)
 *
 * @author luojimeng
 * @date 2020/4/10
 */
//@Transactional
class BusApiTest extends AbstractApiTest {

    @Autowired
    private BusApi busApi;

    @Test
    void getAll() throws IOException {
        busApi.getAll();
        Assertions.assertTrue(true);
    }
}