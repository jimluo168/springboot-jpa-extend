package com.bms.common.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Jpa工具类.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
public class JpaUtils {

    /**
     * 对不是null值de逆行拷贝.
     *
     * @param src
     * @param target
     */
    public static void copyNotNullProperties(Object src, Object target, String... ignoreProperties) {
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);
        List<String> nullList = Arrays.asList(getNullPropertyNames(src));
        nullList.addAll(ignoreList);
        BeanUtils.copyProperties(src, target, nullList.toArray(new String[0]));
    }

    private static String[] getNullPropertyNames(Object object) {
        final BeanWrapperImpl wrapper = new BeanWrapperImpl(object);
        return Stream.of(wrapper.getPropertyDescriptors())
                .map(PropertyDescriptor::getName)
                .filter(propertyName -> wrapper.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }
}
