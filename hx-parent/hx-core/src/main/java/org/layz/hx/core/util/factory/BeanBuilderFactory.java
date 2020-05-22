package org.layz.hx.core.util.factory;

import org.layz.hx.core.inte.BeanBuilder;
import org.layz.hx.core.util.builder.DefaultBeanBuilder;

import java.util.ServiceLoader;

public class BeanBuilderFactory {
    private static BeanBuilder beanBuilder;

    static {
        BeanBuilder builder = null;
        ServiceLoader<BeanBuilder> load = ServiceLoader.load(BeanBuilder.class);
        for (BeanBuilder item : load) {
            if(null == builder) {
                builder = new DefaultBeanBuilder();
            }
            item.setBuilder(builder);
            builder = item;
        }
        if(null == builder) {
            builder = new DefaultBeanBuilder();
        }
        beanBuilder = builder;
    }

    public static BeanBuilder getBuilder(){
        return beanBuilder;
    }
}
