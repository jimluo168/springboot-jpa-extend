package com.springboot.jpa.demo.core.web.annotation;

import java.lang.annotation.*;

/**
 * 验证用户是否拥有权限.
 *
 * @author luojimeng
 * @date 2020/3/11
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface RequiresPermissions {

    /**
     * 权限编码.
     *
     * @return
     */
    String[] value() default {};

    /**
     * 逻辑关系 默认 OR.
     *
     * @return
     */
    Logical logical() default Logical.OR;

}
