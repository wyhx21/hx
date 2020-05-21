package org.layz.hx.persist.sqlBuilder;

import org.layz.hx.core.pojo.info.TableClassInfo;
import org.layz.hx.persist.inte.Const;
import org.layz.hx.persist.util.SqlBuildUtil;

public class DeletedByEntitySqlBuilder implements SqlBuilder{

	@Override
	public String getType() {
		return Const.DELETE_BY_ENTITY;
	}

	@Override
	public String buildSql(Object[] param, TableClassInfo tableClassInfo) {
		StringBuilder sql = new StringBuilder("delete from ").append(tableClassInfo.getTableName());
		SqlBuildUtil.buildWhereSql(sql,tableClassInfo,param);
		return sql.toString();
	}

	@Override
	public Object[] buildArgs(Object[] param, TableClassInfo tableClassInfo) {
		return SqlBuildUtil.buildWhereArgs(tableClassInfo,param);
	}

}
