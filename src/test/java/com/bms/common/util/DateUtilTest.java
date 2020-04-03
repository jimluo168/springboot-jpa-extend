package com.bms.common.util;


import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO(类的简要说明)
 *
 * @author luojimeng
 * @date 2020/4/2
 */
class DateUtilTest {

    @Test
    public void date() throws ParseException {
        System.setProperty("user.timezone", "UTC");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        String datestr = "2016-12-30T11:58:59.000+0800";
        Date date = DateUtils.parseDate(datestr, "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        System.out.println(date);
        System.out.println(DateFormatUtils.format(date, "HH:mm", TimeZone.getTimeZone("GMT+8")));
    }

}