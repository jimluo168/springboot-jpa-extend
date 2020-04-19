package com.springboot.jpa.demo.core.util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类.
 *
 * @author luojimeng
 * @date 2020/3/26
 */
public abstract class DateUtil {
    public static final String DATE_FORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public final static String DATE_FORMAT_STR_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public final static String DATE_FORMAT_STR_PLAIN = "yyyy-MM-dd";
    public final static String TIME_FORMAT_STR_PLAIN = "HH:mm:ss";

    /**
     * 时区 GTM+8.
     */
    String TIME_ZONE_GMT8 = "GMT+8";
    /**
     * 时区 UTC.
     */
    String TIME_ZONE_UTC = "UTC";

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

    public static Date parseDate(String str) throws ParseException {
        return DateUtils.parseDate(str, Locale.US, DATE_FORMAT_STR_ISO8601, DATE_FORMAT_UTC, DATE_FORMAT_STR_PLAIN);
    }
}
