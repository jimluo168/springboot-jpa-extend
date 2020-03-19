package com.bms.common.config.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

/**
 * 将下划线式参数进行驼峰式字段进行绑定包装.
 *
 * @author luojimeng
 * @date 2020/3/19
 */
public class ServletRequestSnakeCaseParameterWrapper extends HttpServletRequestWrapper {
    public static final String DEFAULT_PREFIX_SEPARATOR = "_";
    private final Vector<String> names = new Vector<>();
    private final Map<String, String[]> parameterValues = new HashMap<>();

    public ServletRequestSnakeCaseParameterWrapper(HttpServletRequest request) {
        super(request);
        Enumeration<String> parameterNames = super.getParameterNames();
        while (parameterNames != null && parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            String[] values = super.getParameterValues(name);
            String convertName = this.convertName(name);
            names.add(convertName);
            parameterValues.put(convertName, values);
        }
//        this.parameterNames = names.elements();
    }

    private String convertName(String snakeCaseName) {
        if (!snakeCaseName.contains(DEFAULT_PREFIX_SEPARATOR)) {
            return snakeCaseName;
        }
        StringBuilder stringBuilder = new StringBuilder();
        String[] name = snakeCaseName.split(DEFAULT_PREFIX_SEPARATOR);
        for (int i = 0; i < name.length; i++) {
            String s = name[i];
            if (i != 0) {
                s = toUpperFirstChar(s);
            }
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }

    private String toUpperFirstChar(String string) {
        char[] charArray = string.toCharArray();
        charArray[0] -= 32;
        return String.valueOf(charArray);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return this.names.elements();
    }

    @Override
    public String[] getParameterValues(String name) {
        return this.parameterValues.get(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return this.names.elements();
    }
}
