package org.layz.hx.core.util.reader;

import java.util.List;

import org.layz.hx.core.pojo.info.FieldColumnInfo;
/**
 * 集合读取
 *
 */
public class DefaultListReader implements DataReader{

	@Override
	public boolean supportType(Object object) {
		return object instanceof List;
	}

	@Override
	public Object getObject(Object object, FieldColumnInfo fieldColumnInfo) {
		List<?> list = (List<?>) object;
		int index = fieldColumnInfo.getColumn().index();
		if(list.size() > index) {
			return list.get(index);
		}
		return null;
	}

}
