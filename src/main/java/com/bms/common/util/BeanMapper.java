package com.bms.common.util;

import org.apache.commons.beanutils.BeanMap;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO(类的简要说明)
 *
 * @author luojimeng
 * @date 2020/3/14
 */
public class BeanMapper {

    public static Map<String, Object> toMap(Object source) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(source));
        }
        return map;
    }

    public static <T> T toObj(Map<String, Object> map, Class<T> clz) throws IllegalAccessException, InstantiationException {
        T obj = clz.newInstance();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }
        return obj;
    }
}
