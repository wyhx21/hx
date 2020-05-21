package org.layz.hx.persist.sqlBuilder;

import org.layz.hx.core.pojo.info.FieldColumnInfo;
import org.layz.hx.core.pojo.info.TableClassInfo;
import org.layz.hx.persist.inte.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class UpdateSqlBuilder implements SqlBuilder{
	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateSqlBuilder.class);

	@Override
	public String getType() {
		return Const.UPDATE;
	}

	@Override
	public String buildSql(Object[] param, TableClassInfo tableClassInfo) {
		Boolean isBegin = true;
		StringBuilder sqlBuilder = new StringBuilder("update ").append(tableClassInfo.getTableName());
		List<FieldColumnInfo> fieldList = tableClassInfo.getFieldList();
		for (FieldColumnInfo fieldColumnInfo : fieldList) {
			try {
				if(tableClassInfo.getId().contentEquals(fieldColumnInfo.getColumnName())) {
					continue;
				}
				if(isBegin) {
					sqlBuilder.append(" set ").append(fieldColumnInfo.getColumnName()).append(" = ?");
					isBegin = false;
				} else {
					sqlBuilder.append(", ").append(fieldColumnInfo.getColumnName()).append(" = ?");
				}
			} catch (Exception e) {
				LOGGER.error("methodGet: {}", fieldColumnInfo.getMethodGet().getName());
			}
		}
		sqlBuilder.append(" where ").append(tableClassInfo.getId()).append(" = ?;");
		return sqlBuilder.toString();
	}

	@Override
	public Object[] buildArgs(Object[] param, TableClassInfo tableClassInfo) {
		Object entity = param[0];
		List<FieldColumnInfo> fieldList = tableClassInfo.getFieldList();
		List<Object> args = new ArrayList<Object>();
		Object id = null;
		for (FieldColumnInfo fieldColumnInfo : fieldList) {
			Method methodGet = fieldColumnInfo.getMethodGet();
			try {
				Object value = methodGet.invoke(entity);
				if(tableClassInfo.getId().contentEquals(fieldColumnInfo.getColumnName())) {
					id = value;
					continue;
				}
				args.add(value);
			} catch (Exception e) {
				LOGGER.error("methodGet: {}", methodGet.getName());
			}
		}
		args.add(id);
		return args.toArray();
	}
}
