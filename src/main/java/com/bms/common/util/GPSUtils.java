package com.bms.common.util;

/**
 * GPS工具类.
 *
 * @author luojimeng
 * @date 2020/4/12
 */
public class GPSUtils {

    /**
     * 将经纬度的分秒转换为度数.
     *
     * @return
     */
    public static double fm2du(String fm) {
        String[] splits = fm.split("\\.");
        int f = Integer.parseInt(splits[0]);
        int m = Integer.parseInt(splits[1]);
        return (f + m / 60d) / 60d;
    }
}
