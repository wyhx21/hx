package org.layz.hx.core.util.converter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
/**
 * 将对象转换为Date类型
 *
 */
public class DefaultDateDataConverter implements DataConverter{
	
	private final SimpleDateFormat simpleDateFormat;
	
	public DefaultDateDataConverter(){
		this.simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	}
	@Override
	public boolean support(Class<?> clazz) {
		return Date.class == clazz;
	}

	@Override
	public Object dataConvert(Object value, Map<Object, Object> param) throws Exception{
		if(null == value) {
			return null;
		} else if(value instanceof Date) {
			return value;
		}
		String str = value.toString().replaceAll("[ -/\\,:]", "");
		return this.simpleDateFormat.parse(str);
	}
}
