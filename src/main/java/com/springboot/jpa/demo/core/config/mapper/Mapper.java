package com.springboot.jpa.demo.core.config.mapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记此类为Mapper类.
 *
 * @author luojimeng
 * @date 2020/4/19
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Mapper {
}
