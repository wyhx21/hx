package org.layz.hx.persist.sqlBuilder;

import org.layz.hx.base.inte.entity.AutoKeyEntity;
import org.layz.hx.core.pojo.info.FieldColumnInfo;
import org.layz.hx.core.pojo.info.TableClassInfo;
import org.layz.hx.persist.inte.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PersistEntitySqlBuilder implements SqlBuilder {
	private static final Logger LOGGER = LoggerFactory.getLogger(PersistEntitySqlBuilder.class);

	@Override
	public String getType() {
		return Const.PERSIST_ENTITY;
	}

	@Override
	public String buildSql(Object[] param, TableClassInfo tableClassInfo) {
		StringBuilder sqlBuilder = new StringBuilder("insert into ").append(tableClassInfo.getTableName()).append(" ( ");
		Object entity = param[0];
		String id = tableClassInfo.getId();
		boolean begin = true;
		boolean autoKey = AutoKeyEntity.class.isInstance(entity);
		StringBuilder paramBuilder = new StringBuilder(" ( ");
		List<FieldColumnInfo> fieldList = tableClassInfo.getFieldList();
		for (FieldColumnInfo fieldColumnInfo : fieldList) {
			String columnName = fieldColumnInfo.getColumnName();
			if(autoKey && id.equals(columnName)) {
				continue;
			}
			if(begin) {
				sqlBuilder.append(columnName);
				paramBuilder.append("? ");
				begin = false;
			} else {
				sqlBuilder.append(" , ").append(columnName);
				paramBuilder.append(", ? ");
			}
		}
		sqlBuilder.append(" ) ").append(" VALUES ").append(paramBuilder).append(" ); ");
		return sqlBuilder.toString();
	}

	@Override
	public Object[] buildArgs(Object[] param, TableClassInfo tableClassInfo) {
		Object entity = param[0];
		String id = tableClassInfo.getId();
		boolean autoKey = AutoKeyEntity.class.isInstance(entity);
		List<FieldColumnInfo> fieldList = tableClassInfo.getFieldList();
		List<Object> arges = new ArrayList<Object>();
		for (FieldColumnInfo fieldColumnInfo : fieldList) {
			String columnName = fieldColumnInfo.getColumnName();
			if(autoKey && id.equals(columnName)) {
				continue;
			}
			try {
				Object value = fieldColumnInfo.getMethodGet().invoke(entity);
				arges.add(value);
			} catch (Exception e) {
				LOGGER.error("buildSql error, columnName: {}", columnName, e);
			}
		}
		return arges.toArray();
	}
}
