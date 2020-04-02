package com.bms.common.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类.
 *
 * @author luojimeng
 * @date 2020/3/26
 */
public abstract class DateUtil {
    /**
     * UTC 转换 GMT+8时间.
     *
     * @param utcDate UTC时间
     * @return GMT+8 时间.
     */
    public static Date utc2gmt8(Date utcDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(utcDate);
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
        return calendar.getTime();
    }

    public static Date gmt82utc(Date gmt8Date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(gmt8Date);
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) - 8);
        return calendar.getTime();
    }
}
