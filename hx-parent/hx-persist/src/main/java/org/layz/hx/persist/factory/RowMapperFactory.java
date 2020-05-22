package org.layz.hx.persist.factory;

import org.layz.hx.base.exception.HxRuntimeException;
import org.layz.hx.core.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
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
		LOGGER.debug("class: {}", clazz);
		rowMapper = new RowMapper<T>() {

			@Override
			public T mapRow(ResultSet rs, int rowNum) throws SQLException {
				return BeanUtil.getBean(clazz, rs);
			}
		};
		store.put(clazz,rowMapper);
		return rowMapper;
	}

}
