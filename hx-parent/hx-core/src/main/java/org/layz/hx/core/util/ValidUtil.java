package org.layz.hx.core.util;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.layz.hx.base.annotation.HxValid;
import org.layz.hx.base.info.FieldValidInfo;
import org.layz.hx.base.inte.Validator;
import org.layz.hx.core.support.HxValidSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidUtil.class);
	/**
     * 单个参数校验
     * @param obj
     */
    @SuppressWarnings("unchecked")
	public static void validParam(Object obj) throws Throwable{
        validParam(obj, Collections.EMPTY_LIST);
    }
	/**
     * 单个参数校验
     * @param obj
     * @param valid
     */
    public static void validParam(Object obj, HxValid valid) throws Throwable{
    	List<String> fieldList = Arrays.asList(valid.value());
        validParam(obj, fieldList);
    }

    /**
     * 单个参数校验
     * @param obj
     * @param fieldList
     */
    @SuppressWarnings("unchecked")
	public static void validParam(Object obj, List<String> fieldList) throws Throwable{
        List<FieldValidInfo> validInfo = HxValidSupport.getValidInfo(obj);
        if(null == validInfo || validInfo.isEmpty()) {
            return;
        }
        if(null == fieldList) {
        	fieldList = Collections.EMPTY_LIST;
        }
        LOGGER.debug("valid obj: {}", obj);
        for (FieldValidInfo fieldValidInfo : validInfo) {
            if(fieldList.isEmpty()) {
                validFieldInfo(fieldValidInfo,obj);
            } else if (fieldList.contains(fieldValidInfo.getFieldName())) {
                validFieldInfo(fieldValidInfo,obj);
            }
        }
    }
    /**
     * 单个字段校验
     * @param fieldValidInfo
     * @param obj
     */
    private static void validFieldInfo(FieldValidInfo fieldValidInfo, Object obj) throws Throwable{
        for (Map.Entry<Annotation, Validator> entry : fieldValidInfo.getValidatorMap().entrySet()) {
            entry.getValue().validat(obj,entry.getKey(),fieldValidInfo);
        }
    }
}
