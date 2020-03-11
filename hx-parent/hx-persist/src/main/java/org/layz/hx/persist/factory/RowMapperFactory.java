package org.layz.hx.persist.factory;

import org.layz.hx.base.exception.HxRuntimeException;
import org.layz.hx.core.pojo.info.FieldColumnInfo;
import org.layz.hx.core.pojo.info.TableClassInfo;
import org.layz.hx.core.support.HxTableSupport;
import org.layz.hx.core.util.factory.DataReaderFactory;
import org.layz.hx.core.util.reader.DataReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RowMapperFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(RowMapperFactory.class);
	private static final Map<Object, RowMapper> store = new ConcurrentHashMap<Object, RowMapper>();
	/**
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	public static <T> RowMapper<T> getRowMapper(final Class<T> clazz){
		if(null == clazz) {
			throw new HxRuntimeException("class is null");
		}
		RowMapper rowMapper = store.get(clazz);
		if(null != rowMapper) {
			return rowMapper;
		}
		TableClassInfo tableClassInfo = HxTableSupport.getTableClassInfo(clazz);
		if(null == tableClassInfo) {
			throw new HxRuntimeException("tableClassInfo is null");
		}
		final List<FieldColumnInfo> fieldList = tableClassInfo.getFieldList();
		if(null == fieldList || fieldList.isEmpty()) {
			throw new HxRuntimeException("fieldList is empty");
		}
		LOGGER.debug("class: {}", clazz);
		rowMapper = new RowMapper<T>() {

			@Override
			public T mapRow(ResultSet rs, int rowNum) throws SQLException {
				DataReader dataReader = DataReaderFactory.getDataReader(rs);
				T t = null;
				try {
					t = clazz.newInstance();
					for (FieldColumnInfo fieldColumnInfo : fieldList) {
						try {
							Object object = dataReader.getObject(rs, fieldColumnInfo);
							object = fieldColumnInfo.getDataformater().format(object, fieldColumnInfo, null);
							object = fieldColumnInfo.getDataConverter().dataConvert(object, null);
							fieldColumnInfo.getMethodSet().invoke(t, object);
						} catch (Exception e) {
							LOGGER.info("set property error, class: {}, fieldName: {}", clazz, fieldColumnInfo.getFieldName());
						}
					}
				} catch (Exception e1) {
					LOGGER.error("newInstance is null, class: {}", clazz);
				}
				return t;
			}
		};
		store.put(clazz,rowMapper);
		return rowMapper;
	}

}
