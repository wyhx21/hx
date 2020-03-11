package org.layz.hx.base.annotation;

import org.layz.hx.base.inte.poi.ContentStyle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HxPoiFile {
    /**
     * 标题
     */
    String value() default "";

    /**
     * 工作表名
     */
    String sheetName() default "";

    /**
     * 样式
     */
    int cellStyle() default ContentStyle.defaultStyle;

    /**
     */
    HxPoiColumn[] columns() default {};
}
