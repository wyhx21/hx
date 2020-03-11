package org.layz.hx.core.util.reader;

import org.layz.hx.core.pojo.info.FieldColumnInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;

public class DefaultResultSetReader implements DataReader{
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultResultSetReader.class);
	@Override
	public boolean supportType(Object object) {
		return object instanceof ResultSet;
	}

	@Override
	public Object getObject(Object object, FieldColumnInfo fieldColumnInfo) {
		try {
			ResultSet resultSet = (ResultSet) object;
			Class<?> type = fieldColumnInfo.getFieldType();
			if (String.class == type) {
				return resultSet.getString(fieldColumnInfo.getColumnName());
			} else if (Date.class == type) {
				return resultSet.getTimestamp(fieldColumnInfo.getColumnName());
			} else if (Integer.class == type || int.class == type) {
				return resultSet.getInt(fieldColumnInfo.getColumnName());
			} else if (Long.class == type || long.class == type) {
				return resultSet.getLong(fieldColumnInfo.getColumnName());
			} else if (Double.class == type || double.class == type) {
				return resultSet.getDouble(fieldColumnInfo.getColumnName());
			} else if (Boolean.class == type || boolean.class == type) {
				return resultSet.getBoolean(fieldColumnInfo.getColumnName());
			} else if (BigDecimal.class == type) {
				return resultSet.getBigDecimal(fieldColumnInfo.getColumnName());
			} else if (Float.class == type || float.class == type) {
				return resultSet.getFloat(fieldColumnInfo.getColumnName());
			} else {
				return resultSet.getObject(fieldColumnInfo.getColumnName());
			}
		} catch(Exception e) {
			// LOGGER.debug("getObject error, column: {}", fieldColumnInfo.getColumnName() , e.getMessage());
		}
		return defaultValue();
	}

}
