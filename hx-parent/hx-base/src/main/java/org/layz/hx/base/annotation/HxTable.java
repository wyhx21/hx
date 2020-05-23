package org.layz.hx.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HxTable {
	/**
	 * _表名，默认类名
	 */
	String value() default "";
	/**
	 * _主键，默认id
	 */
	String id() default "id";
	/**
	 * 描述信息
	 */
	String definition() default "DEFAULT CHARSET=utf8 COMMENT='table'";
}
