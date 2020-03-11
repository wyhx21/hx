package org.layz.hx.base.annotation;

import org.layz.hx.base.inte.poi.ContentStyle;
import org.layz.hx.base.inte.poi.FormaterType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HxPoiColumn {
    /**
     * 排序
     */
    int value() default 0;

    /**
     * 表头
     */
    String title() default "";

    /**
     * 宽度
     */
    int width() default  3000;

    /**
     * 占用的列数
     */
    int cols() default 1;

    /**
     * 样式
     */
    int cellType() default ContentStyle.defaultStyle;

    /**
     * 字段
     */
    String field() default "";

    /**
     * 格式化转换器
     */
    int format() default FormaterType.defaultType;

    /**
     * 参数
     */
    String param() default "";
}
