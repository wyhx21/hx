package org.layz.hx.persist.sqlBuilder;

import org.layz.hx.core.pojo.info.TableClassInfo;
import org.layz.hx.persist.pojo.SqlParam;

public interface SqlBuilder {
	String getType();
	/**
	 * sql构建
	 * @param tableClassInfo
	 * @param param
	 * @return
	 */
	StringBuilder buildCacheSql(TableClassInfo tableClassInfo, Object[] param);
	/**
	 * sql构建
	 * @param cacheSql
	 * @param tableClassInfo
	 * @param param
	 * @return
	 */
	SqlParam buildSql(StringBuilder cacheSql,TableClassInfo tableClassInfo, Object[] param);
}
