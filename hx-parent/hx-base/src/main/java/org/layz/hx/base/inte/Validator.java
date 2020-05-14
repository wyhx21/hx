package org.layz.hx.base.inte;

import org.layz.hx.base.info.FieldValidInfo;

import java.lang.annotation.Annotation;

public interface Validator {
    /**
     * 支持的注解
     * @param annotation
     * @return
     */
    boolean support(Annotation annotation);

    /**
     * 数据校验
     * @param obj
     * @param annotation
     * @param fieldValidInfo
     */
    void validate(Object obj, Annotation annotation, FieldValidInfo fieldValidInfo) throws Throwable;
}
