package com.bms.industry.sync.busbasic.api;

import com.bms.industry.sync.Http;
import com.bms.industry.sync.SyncProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
 * @date 2020/4/9
 */
@Transactional
class CompanyApiTest extends AbstractApiTest {

    @Autowired
    private CompanyApi companyApi;

    @Test
    void getAll() throws IOException {
        companyApi.getAll();
        Assertions.assertTrue(true);
    }
}