package org.layz.hx.core.util.factory;

import org.layz.hx.base.inte.Validator;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class ValidatorFactory {
    private static final List<Validator> store;

    static {
        store = new ArrayList<>();
        ServiceLoader<Validator> load = ServiceLoader.load(Validator.class);
        for (Validator validator : load) {
            store.add(validator);
        }
    }

    public static Validator getValidator(Annotation annotation){
        for (Validator validator : store) {
            if(validator.support(annotation)) {
                return validator;
            }
        }
        return null;
    }
}
