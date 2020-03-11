package org.layz.hx.spring.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.layz.hx.base.annotation.HxValid;
import org.layz.hx.base.info.FieldValidInfo;
import org.layz.hx.base.inte.Validator;
import org.layz.hx.core.support.HxValidSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Aspect
@Order(100)
public class HxValidAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(HxValidAspect.class);

    @Pointcut("@annotation(org.layz.hx.base.annotation.HxValid)")
    public void pointCount(){}

    @Before(value = "pointCount() && @annotation(valid)")
    public void doBefore(JoinPoint joinPoint, HxValid valid) throws Throwable{
        Object[] args = joinPoint.getArgs();
        int[] paramIndex = valid.index();
        for (int index : paramIndex) {
            if(args.length < index) {
                continue;
            }
            Object param = args[index];
            if(null == param) {
                continue;
            }
            validParam(param,valid);
        }
    }

    /**
     * 单个参数校验
     * @param obj
     * @param valid
     */
    private void validParam(Object obj, HxValid valid) throws Throwable{
        List<FieldValidInfo> validInfo = HxValidSupport.getValidInfo(obj);
        if(null == validInfo || validInfo.isEmpty()) {
            return;
        }
        LOGGER.debug("valid obj: {}", obj);
        List<String> fieldList = Arrays.asList(valid.value());
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
    private void validFieldInfo(FieldValidInfo fieldValidInfo, Object obj) throws Throwable{
        for (Map.Entry<Annotation, Validator> entry : fieldValidInfo.getValidatorMap().entrySet()) {
            entry.getValue().validat(obj,entry.getKey(),fieldValidInfo);
        }
    }
}
