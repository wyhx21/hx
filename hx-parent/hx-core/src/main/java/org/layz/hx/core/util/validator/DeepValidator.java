package org.layz.hx.core.util.validator;

import org.layz.hx.base.annotation.valid.HxDeepValid;
import org.layz.hx.base.info.FieldValidInfo;
import org.layz.hx.base.inte.Validator;
import org.layz.hx.core.util.ValidUtil;

import java.lang.annotation.Annotation;

public class DeepValidator implements Validator {
    @Override
    public boolean support(Annotation annotation) {
        return HxDeepValid.class.isInstance(annotation);
    }

    @Override
    public void validate(Object obj, Annotation annotation, FieldValidInfo fieldValidInfo) throws Throwable {
        Object value = fieldValidInfo.getMethodGet().invoke(obj);
        if(null == value) {
            return;
        }
        ValidUtil.validParam(value);
    }
}
