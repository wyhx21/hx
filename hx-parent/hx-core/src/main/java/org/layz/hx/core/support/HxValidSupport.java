package org.layz.hx.core.support;

import org.layz.hx.base.annotation.HxSupperClass;
import org.layz.hx.base.info.FieldValidInfo;
import org.layz.hx.base.inte.Validator;
import org.layz.hx.core.util.factory.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HxValidSupport {
    private static final Logger LOGGER = LoggerFactory.getLogger(HxValidSupport.class);
    private static final Map<Object, List<FieldValidInfo>> store = new ConcurrentHashMap<>();

    /**
     * 获取注解信息
     * @param obj
     * @return
     */
    public static List<FieldValidInfo> getValidInfo(Object obj){
        if(null == obj) {
            return null;
        }
        Class<?> clazz = obj.getClass();
        List<FieldValidInfo> infoList = store.get(clazz);
        if(null != infoList) {
            return infoList;
        }
        LOGGER.debug("clazz: {}", clazz);
        infoList = new ArrayList<>();
        setNewInfoList(infoList,clazz);
        store.put(clazz,infoList);
        return infoList;
    }

    /**
     * 设置注解信息
     * @param infoList
     * @param clazz
     */
    private static void setNewInfoList(List<FieldValidInfo> infoList, Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            FieldValidInfo validInfo= null;
            try {
                Annotation[] annotations = field.getAnnotations();
                if(annotations.length <1) {
                    continue;
                }
                for (Annotation annotation : annotations) {
                    Validator validator = ValidatorFactory.getValidator(annotation);
                    if(null == validator) {
                        continue;
                    }
                    if (null == validInfo) {
                        validInfo = getNewValidatorInfo(clazz,field);
                        Map<Annotation,Validator> validatorMap = new HashMap<>();
                        validatorMap.put(annotation,validator);
                        validInfo.setValidatorMap(validatorMap);
                    } else {
                        validInfo.getValidatorMap().put(annotation,validator);
                    }
                }
            } catch (Throwable e) {
                LOGGER.error("obtain FieldColumnInfo error,fieldName: {}",field.getName(), e);
            }
            if(null != validInfo
                && null != validInfo.getValidatorMap()
                && !validInfo.getValidatorMap().isEmpty()) {
                infoList.add(validInfo);
            }
        }
        Class<?> superclass = clazz.getSuperclass();
        if(superclass.isAnnotationPresent(HxSupperClass.class)) {
            setNewInfoList(infoList, superclass);
        }
    }

    /**
     * @param clazz
     * @param field
     * @return
     * @throws Throwable
     */
    private static FieldValidInfo getNewValidatorInfo(Class<?> clazz, Field field) throws Throwable{
        Class<?> fieldType = field.getType();
        String fieldName = field.getName();
        String setMethodName = String.format("set%s%s", fieldName.substring(0,1).toUpperCase(),fieldName.substring(1));
        String getMethodName = String.format("get%s%s", fieldName.substring(0,1).toUpperCase(),fieldName.substring(1));
        Method methodGet = clazz.getMethod(getMethodName);
        Method methodSet = clazz.getMethod(setMethodName, fieldType);

        FieldValidInfo fieldInfo = new FieldValidInfo();
        fieldInfo.setFieldName(fieldName);
        fieldInfo.setFieldType(fieldType);
        fieldInfo.setMethodGet(methodGet);
        fieldInfo.setMethodSet(methodSet);
        return fieldInfo;
    }
}
