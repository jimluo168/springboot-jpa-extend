package com.bms.common.util;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * JAVA Bean转换类.
 *
 * @author luojimeng
 * @date 2020/3/14
 */
public class BeanMapper {

    public static Map<String, Object> toMap(Object source) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        ReflectionUtils.doWithFields(source.getClass(), new ReflectionUtils.FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                field.setAccessible(true);
                map.put(field.getName(), field.get(source));
            }
        });
        return map;
    }

    public static <T> T toObj(Map<String, Object> map, Class<T> clz) throws IllegalAccessException, InstantiationException {
        T obj = clz.newInstance();
        ReflectionUtils.doWithFields(clz, new ReflectionUtils.FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                int mod = field.getModifiers();
                if (!Modifier.isStatic(mod) && !Modifier.isFinal(mod)) {
                    field.setAccessible(true);
                    field.set(obj, map.get(field.getName()));
                }
            }
        });
        return obj;
    }
}
