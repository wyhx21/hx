package org.layz.hx.persist.sqlBuilder;

import org.layz.hx.core.pojo.info.TableClassInfo;
import org.layz.hx.persist.inte.Const;
import org.layz.hx.persist.util.SqlBuildUtil;

public class FindByIdSqlBuilder implements SqlBuilder{

	@Override
	public String getType() {
		return Const.FIND_BY_ID;
	}

	@Override
	public String buildSql(Object[] param, TableClassInfo tableClassInfo) {
		StringBuilder sql = SqlBuildUtil.builderSelect(tableClassInfo)
				.append(" where ").append(tableClassInfo.getId()).append(" = ?;");
		return sql.toString();
	}

	@Override
	public Object[] buildArgs(Object[] param, TableClassInfo tableClassInfo) {
		return param;
	}

}
