package com.bms.common.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO(类的简要说明)
 *
 * @author luojimeng
 * @date 2020/4/12
 */
class GPSUtilsTest {

    @Test
    void fm2du() {
        double du = GPSUtils.fm2du("1596.7755");
        System.out.println(du);
    }

    @Test
    void getEllipsoidalDistance() {
        double m1 = GPSUtils.getDistance(29.490295, 106.486654, 29.615467, 106.581515);
        System.out.println(m1);
    }
}