package org.layz.hx.core.util.converter;

import java.util.Map;

/**
 * 将对象转换为Boolean
 *
 */
public class DefaultBooleanDataConverter implements DataConverter{

	@Override
	public boolean support(Class<?> clazz) {
		return Boolean.class == clazz || boolean.class == clazz;
	}

	@Override
	public Object dataConvert(Object value, Map<Object, Object> param) throws Exception{
		if(null == value) {
			return null;
		} else if(value instanceof Boolean) {
			return value;
		}
		return Boolean.parseBoolean(value.toString().trim());
	}

}
