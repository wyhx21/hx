package org.layz.hx.core.util.converter;

import java.util.Map;
/**
 * 将对象转换为Long类型
 *
 */
public class DefaultLongDataConverter implements DataConverter{

	@Override
	public boolean support(Class<?> clazz) {
		return Long.class == clazz || long.class == clazz;
	}

	@Override
	public Object dataConvert(Object value, Map<Object, Object> param) throws Exception{
		if(null == value) {
			return null;
		}else if(value instanceof Number) {
			return ((Number)value).longValue();
		}
		Double parseDouble = Double.parseDouble(value.toString().trim());
		return parseDouble.longValue();
	}

}
