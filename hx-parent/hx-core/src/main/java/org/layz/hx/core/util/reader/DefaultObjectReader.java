package org.layz.hx.core.util.reader;

import java.lang.reflect.Method;

import org.layz.hx.core.pojo.info.FieldColumnInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 默认数据读取
 *
 */
public class DefaultObjectReader implements DataReader{
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultObjectReader.class);
	@Override
	public boolean supportType(Object object) {
		return false;
	}

	@Override
	public Object getObject(Object object, FieldColumnInfo fieldColumnInfo) {
		try {
			String key = fieldColumnInfo.getColumnName();
			String methodName = String.format("get%s%s", key.substring(0,1).toUpperCase(),key.substring(1));
			Method method = object.getClass().getMethod(methodName);
			return method.invoke(object);
		} catch (Exception e) {
			LOGGER.debug("getObject error, class:{}, columnName:{}", object.getClass(), fieldColumnInfo.getColumnName());
			return defaultValue();
		}
	}
}
