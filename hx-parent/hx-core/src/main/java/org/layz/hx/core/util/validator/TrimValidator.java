package org.layz.hx.core.util.validator;

import org.layz.hx.base.annotation.valid.HxTrim;
import org.layz.hx.base.info.FieldValidInfo;
import org.layz.hx.base.inte.Validator;

import java.lang.annotation.Annotation;

public class TrimValidator implements Validator {
    @Override
    public boolean support(Annotation annotation) {
        return annotation instanceof HxTrim;
    }

    @Override
    public void validat(Object obj, Annotation annotation, FieldValidInfo fieldValidInfo) throws Throwable {
        Object value = fieldValidInfo.getMethodGet().invoke(obj);
        if(null == value) {
            return;
        }
        String trim = value.toString().trim();
        fieldValidInfo.getMethodSet().invoke(obj,trim);
    }
}
