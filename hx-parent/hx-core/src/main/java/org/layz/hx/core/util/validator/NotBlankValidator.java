package org.layz.hx.core.util.validator;

import org.layz.hx.base.annotation.valid.HxNotBlank;
import org.layz.hx.base.info.FieldValidInfo;
import org.layz.hx.base.inte.Validator;
import org.layz.hx.base.util.Assert;

import java.lang.annotation.Annotation;

/**
 * 字符串不能为空，不校验null
 */
public class NotBlankValidator implements Validator {
    @Override
    public boolean support(Annotation annotation) {
        return annotation instanceof HxNotBlank;
    }

    @Override
    public void validat(Object obj, Annotation annotation, FieldValidInfo fieldValidInfo) throws Throwable {
        Object value = fieldValidInfo.getMethodGet().invoke(obj);
        if(null == value) {
            return;
        }
        Assert.isNotBlank(value.toString(),((HxNotBlank)annotation).value());
    }
}
