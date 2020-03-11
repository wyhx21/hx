package org.layz.hx.core.util;

import org.layz.hx.base.info.FieldInfo;
import org.layz.hx.core.inte.FieldFilter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class ClassUtil {
    /**
     * @param list
     * @param tClass
     * @param clazz
     * @param fieldFilter
     * @param <T>
     */
    public static<T extends FieldInfo> void setFieldInfo(List<T> list, Class<T> tClass, Class clazz, FieldFilter fieldFilter) {
        if(null == clazz) {
            return;
        }
        for (Field field : clazz.getDeclaredFields()) {
            if(!fieldFilter.filter(field)) {
                continue;
            }
            try {
                T item = tClass.newInstance();
                item.setField(field);
                item.setFieldName(field.getName());
                item.setFieldType(field.getType());
                item.setMethodGet(getMethodGet(clazz,field.getName()));
                item.setMethodSet(getMethodSet(clazz,field.getName(),field.getType()));
                list.add(item);
            } catch (Exception e){}
        }
        setFieldInfo(list,tClass,clazz.getSuperclass(),fieldFilter);
    }

    private static Method getMethodSet(Class clazz,String fieldName,Class paramType) throws NoSuchMethodException {
        String methodName = "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
        return clazz.getMethod(methodName,paramType);
    }

    private static Method getMethodGet(Class clazz,String fieldName) throws NoSuchMethodException {
        String methodName = "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
        return clazz.getMethod(methodName);
    }
}
