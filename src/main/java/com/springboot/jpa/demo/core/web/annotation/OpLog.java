package com.springboot.jpa.demo.core.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志记录器.
 *
 * @author luojimeng
 * @date 2020/3/14
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OpLog {
    /**
     * 功能名称.
     *
     * @return
     */
    String value() default "";

    /**
     * 模块名称.
     *
     * @return
     */
    String module() default "";

    /**
     * 是否在方法之后进行记录 默认 在进入方法前就进行记录.
     *
     * @return
     */
    boolean after() default false;
}
