package org.layz.hx.persist.factory;

import org.layz.hx.persist.sqlBuilder.SqlBuilder;
import org.layz.hx.persist.sqlBuilder.SqlBuilderDecorator;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

public class SqlBuildFactory {
	private static Map<String, SqlBuilder> store;

	static {
		store = new ConcurrentHashMap<>();
		ServiceLoader<SqlBuilder> load = ServiceLoader.load(SqlBuilder.class);
		for (SqlBuilder builder : load) {
			store.put(builder.getType(),new SqlBuilderDecorator(builder));
		}
	}

	public static SqlBuilder getSqlBuilder(String type){
		return store.get(type);
	}
}
