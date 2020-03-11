package org.layz.hx.persist.sqlBuilder;

import org.layz.hx.core.pojo.info.TableClassInfo;
import org.layz.hx.persist.inte.Const;
import org.layz.hx.persist.pojo.SqlParam;

public class DeletedByEntitySqlBuilder extends AbstractSqlBuilder{

	@Override
	public String getType() {
		return Const.DELETE_BY_ENTITY;
	}

	@Override
	public SqlParam buildSql(StringBuilder cacheSql, TableClassInfo tableClassInfo, Object[] param) {
		cacheSql = new StringBuilder("delete from ").append(tableClassInfo.getTableName());
		return buildWhereSql(cacheSql, tableClassInfo, param);
	}

}
