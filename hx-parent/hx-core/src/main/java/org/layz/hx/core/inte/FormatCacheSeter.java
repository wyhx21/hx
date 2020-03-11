package org.layz.hx.core.inte;

import java.util.List;
import java.util.Map;

import org.layz.hx.core.pojo.info.FieldColumnInfo;

public interface FormatCacheSeter {
	/**
	 * 格式化类型
	 * @return
	 */
	String fromatType();
	/**
	 * 设置缓存
	 * @param cacheMap
	 * @param collect
	 * @param list
	 */
	void setCache(Map<Object, Object> cacheMap,List<FieldColumnInfo> collect, List<?> list);
}
