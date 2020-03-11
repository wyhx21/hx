package org.layz.hx.persist.sqlBuilder;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.layz.hx.core.pojo.info.FieldColumnInfo;
import org.layz.hx.core.pojo.info.TableClassInfo;
import org.layz.hx.persist.inte.Const;
import org.layz.hx.persist.pojo.SqlParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateSqlBuilder extends AbstractSqlBuilder{
	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateSqlBuilder.class);

	@Override
	public String getType() {
		return Const.UPDATE;
	}

	@Override
	public SqlParam buildSql(StringBuilder cacheSql, TableClassInfo tableClassInfo, Object[] param) {
		Object entity = param[0];
		Boolean notnull = (Boolean) param[1];
		List<FieldColumnInfo> fieldList = tableClassInfo.getFieldList();
		Boolean isBegin = true;
		List<Object> args = new ArrayList<Object>();
		Object id = null;
		
		cacheSql = new StringBuilder("update ").append(tableClassInfo.getTableName());
		for (FieldColumnInfo fieldColumnInfo : fieldList) {
			Method methodGet = fieldColumnInfo.getMethodGet();
			try {
				Object value = methodGet.invoke(entity);
				if(tableClassInfo.getId().contentEquals(fieldColumnInfo.getColumnName())) {
					id = value;
					continue;
				}
				if(notnull) {
					if(null == value) {
						continue;
					}
					if(String.class == fieldColumnInfo.getFieldType() && value.toString().length() < 1) {
						continue;
					}
				}
				if(isBegin) {
					cacheSql.append(" set ").append(fieldColumnInfo.getColumnName()).append(" = ?");
					isBegin = false;
				} else {
					cacheSql.append(", ").append(fieldColumnInfo.getColumnName()).append(" = ?");
				}
				args.add(value);
			} catch (Exception e) {
				LOGGER.error("methodGet: {}", methodGet.getName());
			}
		}
		args.add(id);
		cacheSql.append(" where ").append(tableClassInfo.getId()).append(" = ?;");
		
		SqlParam sqlParam = new SqlParam();
		sqlParam.setArgs(args.toArray());
		sqlParam.setSql(cacheSql.toString());
		return sqlParam;
	}

}
