package com.bms.common.web.annotation;

import java.lang.annotation.*;

/**
 * 验证用户是否登录.
 *
 * @author luojimeng
 * @date 2020/3/11
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequiresPermissions {

    /**
     * 权限编码.
     *
     * @return
     */
    String value() default "";
}
