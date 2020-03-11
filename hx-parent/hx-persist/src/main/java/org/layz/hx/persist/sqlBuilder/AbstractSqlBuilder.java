package org.layz.hx.persist.sqlBuilder;

import org.layz.hx.base.pojo.Pageable;
import org.layz.hx.base.util.StringUtil;
import org.layz.hx.core.pojo.info.FieldColumnInfo;
import org.layz.hx.core.pojo.info.TableClassInfo;
import org.layz.hx.persist.inte.Const;
import org.layz.hx.persist.pojo.SqlParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSqlBuilder implements SqlBuilder{
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSqlBuilder.class);
	
	@Override
	public StringBuilder buildCacheSql(TableClassInfo tableClassInfo, Object[] param) {
		return null;
	}
	
	protected StringBuilder builderSelect(TableClassInfo tableClassInfo) {
		String tableName = tableClassInfo.getTableName();
		List<FieldColumnInfo> fieldList = tableClassInfo.getFieldList();
		StringBuilder sqlBuilder = new StringBuilder();
		for (int i = 0; i < fieldList.size(); i++) {
			FieldColumnInfo fieldColumnInfo = fieldList.get(i);
			String columnName = fieldColumnInfo.getColumnName();
			sqlBuilder = sqlBuilder.append(Const.SEPARATOR).append(columnName);
		}
		sqlBuilder = new StringBuilder("select ")
				.append(sqlBuilder.substring(1))
				.append(" from ")
				.append(tableName);
		return sqlBuilder;
	}
	
	protected SqlParam buildWhereSql(StringBuilder cacheSql, TableClassInfo tableClassInfo, Object[] param) {
		Object object = param[0];
		Pageable pageable = (Pageable)param[1];
		
		SqlParam sqlParam = new SqlParam();
		
		List<FieldColumnInfo> fieldList = tableClassInfo.getFieldList();
		List<Object> argsList = new ArrayList<Object>();
		StringBuilder sqlBuilder = new StringBuilder(cacheSql);
		if(null != object) {
			int index = 0;
			for (FieldColumnInfo fieldColumnInfo : fieldList) {
				Class<?> fieldType = fieldColumnInfo.getFieldType();
				Method methodGet = fieldColumnInfo.getMethodGet();
				try {
					Object value = methodGet.invoke(object);
					if(null == value) {
						continue;
					}
					if(String.class == fieldType && StringUtil.isBlank(value.toString())) {
						continue;
					}
					if(index == 0) {
						sqlBuilder.append(" where ").append(fieldColumnInfo.getColumnName()).append(" = ?");
						index ++;
					} else {
						sqlBuilder.append(" and ").append(fieldColumnInfo.getColumnName()).append(" = ?");
					}
					argsList.add(value);
				} catch (Exception e) {
					LOGGER.error("buildSql error, method: {}", methodGet, e);
				}
			}
		}
		if(null != pageable) {
			String orderBy = pageable.getOrderBy();
			if(null != orderBy && orderBy.length() > 1) {
				sqlBuilder.append(" order by ").append(orderBy);
			}
			Long offset = pageable.getOffset();
			if(null != offset) {
				sqlBuilder.append(" limit ? , ? ");
				argsList.add(offset);
				argsList.add(pageable.getSize());
			}
		}
		sqlParam.setSql(sqlBuilder.toString());
		sqlParam.setArgs(argsList.toArray());
		return sqlParam;
	}
}
