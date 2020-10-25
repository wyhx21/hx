package org.layz.hx.spring.mvc.auth.annotation;

import org.layz.hx.spring.mvc.auth.enums.Logical;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePermission {
    /**
     * 权限值
     */
    String[] value();

    /**
     * 权限逻辑
     */
    Logical type() default Logical.OR;
}
