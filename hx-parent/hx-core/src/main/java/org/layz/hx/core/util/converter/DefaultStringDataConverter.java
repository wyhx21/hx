package org.layz.hx.core.util.converter;

import java.util.Map;
/**
 * 将对象转换为String类型，并去除两端多余的空格
 *
 */
public class DefaultStringDataConverter implements DataConverter{

	@Override
	public boolean support(Class<?> clazz) {
		if(String.class == clazz) {
			return true;
		}
		return false;
	}

	@Override
	public Object dataConvert(Object value, Map<Object, Object> param) throws Exception{
		if(null == value) {
			return null;
		}
		if(value instanceof Number) {
			Double parDouble = Double.parseDouble(value.toString());
			Long longValue = parDouble.longValue();
			return longValue.toString();
		}
		return value.toString().trim();
	}

}
