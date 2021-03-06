package org.layz.hx.core.util.formater;

import org.layz.hx.core.pojo.info.FieldColumnInfo;

import java.util.Map;

public interface Dataformater {
	/**
	 * 支持的数据
	 * @param formatType
	 * @return
	 */
	boolean support(String formatType);
	/**
	 * 数据转换
	 * @param instance
	 * @param object
	 * @param fieldInfo
	 * @param cache
	 * @return
	 */
	default Object format(Object instance,Object object,FieldColumnInfo fieldInfo, Map<Object, Object> cache) {
		return format(object, fieldInfo, cache);
	}	
	/**
	 * 数据转换
	 * @param object
	 * @param fieldInfo
	 * @param cache
	 * @return
	 */
	default Object format(Object object,FieldColumnInfo fieldInfo, Map<Object, Object> cache) {
		return object;
	}
}
