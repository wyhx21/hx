package org.layz.hx.persist.factory;

import org.layz.hx.core.pojo.info.TableClassInfo;
import org.layz.hx.core.support.HxTableSupport;
import org.layz.hx.persist.pojo.SqlParam;
import org.layz.hx.persist.sqlBuilder.SqlBuilder;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

public class SqlBuildFactory {
	private static Map<String, SqlBuilder> store;

	static {
		store = new ConcurrentHashMap<>();
		ServiceLoader<SqlBuilder> load = ServiceLoader.load(SqlBuilder.class);
		for (SqlBuilder builder : load) {
			store.put(builder.getType(),builder);
		}
	}
	public static SqlParam buildSql(Class<?> clazz,String key, Object... param) {
		TableClassInfo tableClassInfo = HxTableSupport.getTableClassInfo(clazz);
		SqlBuilder builder = store.get(key);
		StringBuilder sqlBuilder = builder.buildCacheSql(tableClassInfo, param);
		SqlParam sqlParam = builder.buildSql(sqlBuilder,tableClassInfo,param);
		return sqlParam;
	}
}
