package org.layz.hx.core.util.reader;

import org.layz.hx.core.pojo.info.FieldColumnInfo;

/**
 * 
 * 数组对象读取
 *
 */
public class DefaultArrayReader implements DataReader{

	@Override
	public boolean supportType(Object object) {
		return object instanceof Object[];
	}

	@Override
	public Object getObject(Object object, FieldColumnInfo fieldColumnInfo) {
		int index = fieldColumnInfo.getColumn().index();
		Object[] arr = (Object[]) object;
		if(arr.length > index) {
			return arr[index];
		}
		return null;
	}
}
