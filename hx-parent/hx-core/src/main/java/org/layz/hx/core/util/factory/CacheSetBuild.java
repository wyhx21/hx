package org.layz.hx.core.util.factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

import org.layz.hx.core.inte.FormatCacheSeter;
import org.layz.hx.core.pojo.info.FieldColumnInfo;

public class CacheSetBuild {
	private static final List<FormatCacheSeter> store;
	
	static {
		store = new ArrayList<FormatCacheSeter>();
		ServiceLoader<FormatCacheSeter> load = ServiceLoader.load(FormatCacheSeter.class);
		for (FormatCacheSeter formatCacheSeter : load) {
			store.add(formatCacheSeter);
		}
	}
	
	public static void setCache(List<FieldColumnInfo> columnInfos, Object obj,Map<Object, Object> cacheMap) {
		setCacheList(columnInfos, Collections.singletonList(obj), cacheMap);
	}
	
    public static void setCacheList(List<FieldColumnInfo> columnInfos, List<?> list,Map<Object, Object> cacheMap) {
		if(null == list || list.isEmpty()) {
			return;
		}
    	Map<String, List<FieldColumnInfo>> collect = columnInfos.stream().collect(Collectors.groupingBy(FieldColumnInfo::getFormatType));
		for(FormatCacheSeter seter : store) {
			List<FieldColumnInfo> entryValue = collect.get(seter.fromatType());
			if(null != entryValue && !entryValue.isEmpty()) {
				seter.setCache(cacheMap, entryValue, list);
			}
		}
	}
}
