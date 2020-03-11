package org.layz.hx.base.util;

import org.layz.hx.base.exception.HxRuntimeException;
import org.layz.hx.base.inte.ResponseEnum;

import java.util.Collection;

public class Assert {

    private Assert(){}

    /**
     * flag == false throw HxRuntimeException
     * @param flag
     * @param responseEnum
     */
    public static void isTrue(boolean flag, ResponseEnum responseEnum){
        isFalse(!flag, responseEnum);
    }
    /**
     * flag == false throw HxRuntimeException
     * @param flag
     * @param msg
     */
    public static void isTrue(boolean flag, String msg){
        isFalse(!flag, msg);
    }
    /**
     * flag == true throw HxRuntimeException
     * @param flag
     * @param responseEnum
     */
    public static void isFalse(boolean flag, ResponseEnum responseEnum){
        if(flag) {
            throw new HxRuntimeException(responseEnum);
        }
    }

    /**
     * flag == true throw HxRuntimeException
     * @param flag
     * @param msg
     */
    public static void isFalse(boolean flag, String msg){
        if(flag) {
            throw new HxRuntimeException(msg);
        }
    }

    /**
     * obj != null throw HxRuntimeException
     * @param obj
     * @param responseEnum
     */
    public static void isNull(Object obj, ResponseEnum responseEnum){
        if(obj != null) {
            throw new HxRuntimeException(responseEnum);
        }
    }
    /**
     * obj != null throw HxRuntimeException
     * @param obj
     * @param msg
     */
    public static void isNull(Object obj, String msg){
        if(obj != null) {
            throw new HxRuntimeException(msg);
        }
    }
    /**
     * obj == null throw HxRuntimeException
     * @param obj
     * @param responseEnum
     */
    public static void isNotNull(Object obj, ResponseEnum responseEnum){
        if(obj == null) {
            throw new HxRuntimeException(responseEnum);
        }
    }
    /**
     * obj == null throw HxRuntimeException
     * @param obj
     * @param msg
     */
    public static void isNotNull(Object obj, String msg){
        if(obj == null) {
            throw new HxRuntimeException(msg);
        }
    }
    /**
     * str isBlank throw HxRuntimeException
     * @param str
     * @param responseEnum
     */
    public static void isBlank(String str, ResponseEnum responseEnum){
        if(StringUtil.isNotBlank(str)) {
            throw new HxRuntimeException(responseEnum);
        }
    }
    /**
     * str isBlank throw HxRuntimeException
     * @param str
     * @param msg
     */
    public static void isBlank(String str, String msg){
        if(StringUtil.isNotBlank(str)) {
            throw new HxRuntimeException(msg);
        }
    }
    /**
     * str isBlank throw HxRuntimeException
     * @param str
     * @param responseEnum
     */
    public static void isNotBlank(String str, ResponseEnum responseEnum){
        if(StringUtil.isBlank(str)) {
            throw new HxRuntimeException(responseEnum);
        }
    }
    /**
     * str isBlank throw HxRuntimeException
     * @param str
     * @param msg
     */
    public static void isNotBlank(String str, String msg){
        if(StringUtil.isBlank(str)) {
            throw new HxRuntimeException(msg);
        }
    }

    /**
     * collection is null or empty throw HxRuntimeException
     * @param collection
     * @param responseEnum
     */
    public static void isNotEmpty(Collection collection, ResponseEnum responseEnum){
        if(null == collection || collection.isEmpty()) {
            throw new HxRuntimeException(responseEnum);
        }
    }
    /**
     * collection is null or empty throw HxRuntimeException
     * @param collection
     * @param msg
     */
    public static void isNotEmpty(Collection collection, String msg){
        if(null == collection || collection.isEmpty()) {
            throw new HxRuntimeException(msg);
        }
    }
    
    /**
     * collection is not null and not empty throw HxRuntimeException
     * @param collection
     * @param responseEnum
     */
    public static void isEmpty(Collection collection, ResponseEnum responseEnum){
        if(null != collection && !collection.isEmpty()) {
            throw new HxRuntimeException(responseEnum);
        }
    }
    /**
     * collection is not null and not empty throw HxRuntimeException
     * @param collection
     * @param msg
     */
    public static void isEmpty(Collection collection, String msg){
    	if(null != collection && !collection.isEmpty()) {
            throw new HxRuntimeException(msg);
        }
    }
}
