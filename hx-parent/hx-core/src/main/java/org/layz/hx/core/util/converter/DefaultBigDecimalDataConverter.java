package org.layz.hx.core.util.converter;

import java.math.BigDecimal;
import java.util.Map;

public class DefaultBigDecimalDataConverter implements DataConverter{

	@Override
	public boolean support(Class<?> clazz) {
		return BigDecimal.class == clazz;
	}

	@Override
	public Object dataConvert(Object value, Map<Object, Object> param) throws Exception {
		if(null == value) {
			return null;
		} else if(value instanceof BigDecimal) {
			return value;
		}
		return new BigDecimal(value.toString().trim());
	}

}
