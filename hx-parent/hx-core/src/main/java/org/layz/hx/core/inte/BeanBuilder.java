package org.layz.hx.core.inte;

import org.layz.hx.core.pojo.info.TableClassInfo;

import java.util.Map;

public interface BeanBuilder {
    /**
     * @param builder
     */
    void setBuilder(BeanBuilder builder);

    /**
     * @param clazz
     * @param source
     * @param tableClassInfo
     * @param cacheMap
     * @param <T>
     * @return
     */
    <T> T getBean(Class<T> clazz, Object source, TableClassInfo tableClassInfo, Map<Object, Object> cacheMap);
}
