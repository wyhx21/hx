package org.layz.hx.core.util.converter;

import java.util.Map;
/**
 * 将对象转换为Float类型
 *
 */
public class DefaultFloatDataConverter implements DataConverter{

	@Override
	public boolean support(Class<?> clazz) {
		return Float.class == clazz || float.class == clazz;
	}

	@Override
	public Object dataConvert(Object value, Map<Object, Object> param) throws Exception{
		if(null == value) {
			return null;
		} else if(value instanceof Number) {
			return ((Number)value).floatValue();
		}
		return Float.parseFloat(value.toString().trim());
	}

}
