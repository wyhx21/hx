package org.layz.hx.core.util.reader;

import org.layz.hx.core.pojo.info.FieldColumnInfo;

public interface DataReader {
	/**
	 * 支持的数据类型
	 * @param object
	 * @return
	 */
	boolean supportType(Object object);
	/**
	 * 获取数据的值
	 * @param object
	 * @param fieldColumnInfo
	 * @return
	 */
	Object getObject(Object object, FieldColumnInfo fieldColumnInfo);
	/**
	 * 当获取的值为 null 时，默认的值
	 * @return
	 */
	default Object defaultValue(){
		return null;
	}
}
