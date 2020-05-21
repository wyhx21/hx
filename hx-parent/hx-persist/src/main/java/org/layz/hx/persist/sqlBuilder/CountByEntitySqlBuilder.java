package org.layz.hx.persist.sqlBuilder;

import org.layz.hx.core.pojo.info.TableClassInfo;
import org.layz.hx.persist.inte.Const;
import org.layz.hx.persist.util.SqlBuildUtil;

public class CountByEntitySqlBuilder implements SqlBuilder{

	@Override
	public String getType() {
		return Const.COUNT_BY_ENTITY;
	}

	@Override
	public String buildSql(Object[] param, TableClassInfo tableClassInfo) {
		StringBuilder builder = new StringBuilder("select count(0) total from ").append(tableClassInfo.getTableName());
		SqlBuildUtil.buildWhereSql(builder,tableClassInfo,param);
		return builder.toString();
	}

	@Override
	public Object[] buildArgs(Object[] param, TableClassInfo tableClassInfo) {
		return SqlBuildUtil.buildWhereArgs(tableClassInfo,param);
	}
}
