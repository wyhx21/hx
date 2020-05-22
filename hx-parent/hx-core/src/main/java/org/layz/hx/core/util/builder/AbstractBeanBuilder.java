package org.layz.hx.core.util.builder;

import org.layz.hx.core.inte.BeanBuilder;

public abstract class AbstractBeanBuilder implements BeanBuilder {
    protected BeanBuilder beanBuilder;

    @Override
    public void setBuilder(BeanBuilder builder) {
        this.beanBuilder = builder;
    }
}
