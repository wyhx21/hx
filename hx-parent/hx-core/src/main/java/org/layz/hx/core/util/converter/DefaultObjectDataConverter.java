package org.layz.hx.core.util.converter;

import java.util.Map;

/**
 * 格式不转换
 *
 */
public class DefaultObjectDataConverter implements DataConverter{

	@Override
	public boolean support(Class<?> clazz) {
		return false;
	}

	@Override
	public Object dataConvert(Object value, Map<Object, Object> param) {
		return value;
	}
}
