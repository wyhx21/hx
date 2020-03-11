package org.layz.hx.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HxValid {
    /**
     * 校验的字段
     */
    String[] value() default {};

    /**
     * 校验的参数位置
     */
    int[] index() default {0};
}
