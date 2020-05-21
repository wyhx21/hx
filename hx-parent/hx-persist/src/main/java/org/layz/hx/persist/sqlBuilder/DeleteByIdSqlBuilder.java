package org.layz.hx.persist.sqlBuilder;

import org.layz.hx.core.pojo.info.TableClassInfo;
import org.layz.hx.persist.inte.Const;

public class DeleteByIdSqlBuilder implements SqlBuilder {

	@Override
	public String getType() {
		return Const.DELETE_BY_ID;
	}

	@Override
	public String buildSql(Object[] param, TableClassInfo tableClassInfo) {
		return new StringBuilder("delete from ")
				.append(tableClassInfo.getTableName())
				.append(" where ")
				.append(tableClassInfo.getId())
				.append(" = ? ;").toString();
	}

	@Override
	public Object[] buildArgs(Object[] param, TableClassInfo tableClassInfo) {
		return param;
	}
}
