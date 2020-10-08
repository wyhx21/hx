package org.layz.hx.core.util.builder;

import org.layz.hx.core.pojo.info.FieldColumnInfo;
import org.layz.hx.core.pojo.info.TableClassInfo;
import org.layz.hx.core.util.factory.DataReaderFactory;
import org.layz.hx.core.util.reader.DataReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultBeanBuilder extends AbstractBeanBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultBeanBuilder.class);
    @Override
    public <T> T getBean(Class<T> clazz, Object source, TableClassInfo tableClassInfo, Map<Object, Object> cacheMap) {
        if(null == source || null == clazz) {
            LOGGER.debug("clazz is null or source is null");
            return null;
        }
        T instance;
        try {
            instance = clazz.newInstance();
        } catch (Exception e) {
            LOGGER.debug("newInstance error, class: {}", clazz, e);
            return null;
        }
        if(null == tableClassInfo) {
            LOGGER.debug("tableClassInfo is null, class: {}", clazz);
            return instance;
        }
        if(null == cacheMap) {
            cacheMap = new HashMap<Object, Object>();
        }
        List<FieldColumnInfo> fieldList = tableClassInfo.getFieldList();
        if(null == fieldList || fieldList.isEmpty()) {
            LOGGER.debug("fieldList is empty, class: {}", clazz);
            return instance;
        }
        DataReader dataReader = DataReaderFactory.getDataReader(source);
        for (FieldColumnInfo fieldColumnInfo : fieldList) {
            try {
                if(fieldColumnInfo.getColumn().ignore()) {
                    continue;
                }
                Object value = dataReader.getObject(source, fieldColumnInfo);
                value = fieldColumnInfo.getDataformater().format(instance,value, fieldColumnInfo, cacheMap);
                value = fieldColumnInfo.getDataConverter().dataConvert(value, cacheMap);
                fieldColumnInfo.getMethodSet().invoke(instance, value);
            } catch (Exception e) {
                LOGGER.debug("set field error, class: {}, field: {}", clazz, fieldColumnInfo.getFieldName());
            }
        }
        return instance;
    }
}
