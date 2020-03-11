package org.layz.hx.core.util.converter;

import java.util.Map;
/**
 * 将对象转换为Double类型
 *
 */
public class DefaultDoubleDataConverter implements DataConverter{

	@Override
	public boolean support(Class<?> clazz) {
		return Double.class == clazz || double.class == clazz;
	}

	@Override
	public Object dataConvert(Object value, Map<Object, Object> param) throws Exception{
		if(null == value) {
			return null;
		} else if(value instanceof Number) {
			return ((Number)value).doubleValue();
		}
		return Double.parseDouble(value.toString().trim());
	}

}
