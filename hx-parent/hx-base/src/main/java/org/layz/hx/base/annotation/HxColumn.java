package org.layz.hx.base.annotation;

import org.layz.hx.base.enums.Expression;
import org.layz.hx.base.inte.Const;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HxColumn {
	/**
	 * 列名，默认为字段名称
	 */
	String value() default "";
	/**
	 * 下标
	 */
	int index() default 0;
	/**
	 * 排序
	 */
	int sort() default 0;
	/**
	 * 数据格式化类型
	 * DataFormater
	 */
	String formaterType() default Const.FORMAT_DEFAULT;
	/**
	 * 参数
	 */
	String param() default "";

	/**
	 * 描述信息
	 */
	String definition() default "";

	/**
	 * 忽略持久化
	 */
	boolean ignore() default false;

	/**
	 * 表达式
	 */
	Expression expr() default Expression.eq;
}
