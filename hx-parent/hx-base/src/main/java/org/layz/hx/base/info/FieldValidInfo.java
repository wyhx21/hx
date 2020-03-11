package org.layz.hx.base.info;

import org.layz.hx.base.inte.Validator;

import java.lang.annotation.Annotation;
import java.util.Map;

public class FieldValidInfo extends FieldInfo{

    private Map<Annotation, Validator> validatorMap;

    public Map<Annotation, Validator> getValidatorMap() {
        return validatorMap;
    }

    public void setValidatorMap(Map<Annotation, Validator> validatorMap) {
        this.validatorMap = validatorMap;
    }
}
