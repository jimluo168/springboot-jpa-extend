package com.bms.industry.sync.busbasic.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 站点.
 *
 * @author luojimeng
 * @date 2020/4/10
 */
//@Transactional
class StationApiTest extends AbstractApiTest {

    @Autowired
    private StationApi stationApi;

    @Test
    void getAll() throws IOException {
        stationApi.getAll();
        Assertions.assertTrue(true);
    }
}