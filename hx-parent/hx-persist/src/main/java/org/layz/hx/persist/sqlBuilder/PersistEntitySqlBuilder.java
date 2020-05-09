package org.layz.hx.persist.sqlBuilder;

import org.layz.hx.base.inte.entity.AutoKeyEntity;
import org.layz.hx.core.pojo.info.FieldColumnInfo;
import org.layz.hx.core.pojo.info.TableClassInfo;
import org.layz.hx.persist.inte.Const;
import org.layz.hx.persist.pojo.SqlParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PersistEntitySqlBuilder extends AbstractSqlBuilder{
	private static final Logger LOGGER = LoggerFactory.getLogger(PersistEntitySqlBuilder.class);

	@Override
	public String getType() {
		return Const.PERSIST_ENTITY;
	}

	@Override
	public SqlParam buildSql(StringBuilder cacheSql, TableClassInfo tableClassInfo, Object[] param) {
		cacheSql = new StringBuilder("insert into ").append(tableClassInfo.getTableName()).append(" ( ");
		Object entity = param[0];
		List<FieldColumnInfo> fieldList = tableClassInfo.getFieldList();
		String id = tableClassInfo.getId();
		boolean autoKey = AutoKeyEntity.class.isInstance(entity);
		StringBuilder paramBuilder = new StringBuilder(" ( ");
		List<Object> arges = new ArrayList<Object>();
		boolean begin = true;
		for (FieldColumnInfo fieldColumnInfo : fieldList) {
			String columnName = fieldColumnInfo.getColumnName();
			if(autoKey && id.equals(columnName)) {
				continue;
			}
			try {
				Method methodGet = fieldColumnInfo.getMethodGet();
				Object value = methodGet.invoke(entity);
				if(begin) {
					cacheSql.append(columnName);
					paramBuilder.append("? ");
					begin = false;
				} else {
					cacheSql.append(" , ").append(columnName);
					paramBuilder.append(", ? ");
				}
				arges.add(value);
			} catch (Exception e) {
				LOGGER.error("buildSql error, columnName: {}", columnName, e);
			}
		}
		
		cacheSql.append(" ) ").append(" VALUES ").append(paramBuilder).append(" ); ");
		
		SqlParam sqlParam = new SqlParam();
		sqlParam.setSql(cacheSql.toString());
		sqlParam.setArgs(arges.toArray());
		sqlParam.setIdStr(id);
		return sqlParam;
	}

}
