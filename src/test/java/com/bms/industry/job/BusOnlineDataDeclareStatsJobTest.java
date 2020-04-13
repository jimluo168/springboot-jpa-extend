package com.bms.industry.job;

import com.bms.entity.BusOnlineDataDeclare;
import com.bms.entity.BusOnlineDataDeclareStats;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO(类的简要说明)
 *
 * @author luojimeng
 * @date 2020/4/13
 */
@SpringBootTest
@AutoConfigureMockMvc
//@Transactional
class BusOnlineDataDeclareStatsJobTest {

    @Autowired
    private BusOnlineDataDeclareStatsJob job;

    @Test
    void yearOfMonth() {
        job.yearOfMonth();
    }

    @Test
    void quarter() {
        job.quarter();
    }

    @Test
    void month() {
        job.month();
    }

    @Test
    void day() {
        job.day();
    }
}