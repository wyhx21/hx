package org.layz.hx.core.util.reader;

import java.util.Map;

import org.layz.hx.core.pojo.info.FieldColumnInfo;

/**
 * map 格式化
 *
 */
public class DefaultMapReader implements DataReader{

	@Override
	public boolean supportType(Object object) {
		return object instanceof Map;
	}

	@Override
	public Object getObject(Object object, FieldColumnInfo fieldColumnInfo) {
		@SuppressWarnings("unchecked")
		Map<String, ? extends Object> map = (Map<String, ? extends Object>) object;
		String key = fieldColumnInfo.getColumnName();
		return map.get(key);
	}
}
