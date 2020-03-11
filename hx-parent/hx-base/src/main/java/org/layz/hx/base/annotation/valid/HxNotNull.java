package org.layz.hx.base.annotation.valid;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HxNotNull {
    /**
     * 提示信息
     */
    String value() default "";
}
