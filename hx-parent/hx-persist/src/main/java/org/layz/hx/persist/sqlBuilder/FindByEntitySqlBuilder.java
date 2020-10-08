package org.layz.hx.persist.sqlBuilder;

import org.layz.hx.core.pojo.info.TableClassInfo;
import org.layz.hx.persist.inte.Const;
import org.layz.hx.persist.util.SqlBuildUtil;

public class FindByEntitySqlBuilder implements SqlBuilder{

	@Override
	public String getType() {
		return Const.FIND_BY_ENTITY;
	}

	@Override
	public String buildSql(Object[] param, TableClassInfo tableClassInfo) {
		StringBuilder builder = SqlBuildUtil.builderSelect(tableClassInfo);
		SqlBuildUtil.buildWhereSql(builder,param);
		return builder.toString();
	}

	@Override
	public Object[] buildArgs(Object[] param, TableClassInfo tableClassInfo) {
		return SqlBuildUtil.buildWhereArgs(param);
	}
}
