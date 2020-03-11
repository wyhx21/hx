package org.layz.hx.core.util.converter;

import java.util.Map;
/**
 * 将对象转换为Integer类型
 *
 */
public class DefaultIntegerDataConverter implements DataConverter{

	@Override
	public boolean support(Class<?> clazz) {
		if(int.class == clazz || Integer.class == clazz) {
			return true;
		}
		return false;
	}

	@Override
	public Object dataConvert(Object value, Map<Object, Object> param) throws Exception{
		if(null == value) {
			return null;
		} else if(value instanceof Number) {
			return ((Number)value).intValue();
		}
		Double parseDouble = Double.parseDouble(value.toString().trim());
		return parseDouble.intValue();
	}

}
