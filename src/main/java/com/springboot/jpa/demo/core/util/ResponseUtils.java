package com.springboot.jpa.demo.core.util;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * TODO(类的简要说明)
 *
 * @author luojimeng
 * @date 2020/3/17
 */
public abstract class ResponseUtils {

    public static void setHeader(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String encodeFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
        response.setHeader("Content-disposition", "attachment;filename=" + encodeFileName + ".xlsx");
    }
}
