package org.layz.hx.persist.sqlBuilder;

import org.layz.hx.core.pojo.info.TableClassInfo;

public interface SqlBuilder {
	/**
	 * @return
	 */
	String getType();
	/**
	 * @param tableClassInfo
	 * @param param
	 * @return
	 */
	default String buildSql(TableClassInfo tableClassInfo, Object... param) {
		return this.buildSql(param,tableClassInfo);
	}
	/**
	 * @param tableClassInfo
	 * @param param
	 * @return
	 */
	default Object[] buildArgs(TableClassInfo tableClassInfo, Object... param) {
		return this.buildArgs(param,tableClassInfo);
	}
	/**
	 * @param param
	 * @param tableClassInfo
	 * @return
	 */
	default String buildSql(Object[] param, TableClassInfo tableClassInfo) {
		return null;
	}
	/**
	 * @param tableClassInfo
	 * @param param
	 * @return
	 */
	default Object[] buildArgs(Object[] param, TableClassInfo tableClassInfo) {
		return new Object[0];
	}
}
