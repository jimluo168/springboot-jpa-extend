package com.springboot.jpa.demo.core.util;

import org.junit.jupiter.api.Test;

/**
 * TODO(类的简要说明)
 *
 * @author luojimeng
 * @date 2020/4/12
 */
class GPSUtilsTest {

    @Test
    void fen2du() {
        double du = GPSUtils.fen2du("6400.7158");
        System.out.println(du);
    }

    @Test
    void getEllipsoidalDistance() {
        double m1 = GPSUtils.getDistance(26.596263, 106.658968, 26.599362, 106.667395);
        System.out.println(m1);
    }
}