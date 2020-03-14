package com.bms.common.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配合OpLog标记.
 *
 * @author luojimeng
 * @date 2020/3/14
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OpLogModule {
    /**
     * 模块名称.
     * @return
     */
    String value() default "";
}
