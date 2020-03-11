package org.layz.hx.core.support;

import org.layz.hx.base.annotation.HxColumn;
import org.layz.hx.base.annotation.HxTable;
import org.layz.hx.core.pojo.info.FieldColumnInfo;
import org.layz.hx.core.pojo.info.TableClassInfo;
import org.layz.hx.core.util.ClassUtil;
import org.layz.hx.core.util.converter.DataConverter;
import org.layz.hx.core.util.factory.DataConverterFactory;
import org.layz.hx.core.util.factory.DataformaterFactory;
import org.layz.hx.core.util.formater.Dataformater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HxTableSupport {
	private static final Logger LOGGER = LoggerFactory.getLogger(HxTableSupport.class);
	private static final Map<Object,TableClassInfo> store = new ConcurrentHashMap<>();
	/**
	 * _获取领域模型对象
	 * @param clazz
	 * @return
	 */
	public static TableClassInfo getTableClassInfo(Class<?> clazz) {
		if(null == clazz) {
			return null;
		}
		TableClassInfo cacheInfo = store.get(clazz);
		if(null != cacheInfo) {
			return cacheInfo;
		}
		LOGGER.info("getTableClassInfo, clazz: {}", clazz);
		HxTable table = null;
		String tableName = null,id = "id";
		if(clazz.isAnnotationPresent(HxTable.class)) {
			LOGGER.debug("obtain HxTable table");
			table = clazz.getAnnotation(HxTable.class);
			tableName = table.value();
			id = table.id();
		}
		if(null == tableName || tableName.length() < 1) {
			tableName = clazz.getSimpleName();
		}
		List<FieldColumnInfo> fieldList = new ArrayList<FieldColumnInfo>();
		obtainFieldList(fieldList,clazz);
		fieldList.sort(Comparator.comparing(o -> o.getColumn().sort()));
		
		cacheInfo = new TableClassInfo();
		cacheInfo.setTable(table);
		cacheInfo.setClazz(clazz);
		cacheInfo.setTableName(tableName);
		cacheInfo.setId(id);
		cacheInfo.setFieldList(fieldList);

		store.put(clazz,cacheInfo);
		return cacheInfo;
	}
	/**
	 * _设置字段信息
	 * @param fieldList
	 * @param clazz
	 */
	private static void obtainFieldList(List<FieldColumnInfo> fieldList, Class<?> clazz) {
		ClassUtil.setFieldInfo(fieldList,FieldColumnInfo.class,clazz,a -> a.isAnnotationPresent(HxColumn.class));
		for (FieldColumnInfo fieldColumnInfo : fieldList) {
			HxColumn hxColumn = fieldColumnInfo.getField().getAnnotation(HxColumn.class);
			String columnName = hxColumn.value();
			if(columnName.length() < 1) {
				columnName = fieldColumnInfo.getFieldName();
			}
			Dataformater dataformater = DataformaterFactory.getDataformater(hxColumn.formaterType());
			DataConverter converter = DataConverterFactory.getConverter(fieldColumnInfo.getFieldType());

			fieldColumnInfo.setColumn(hxColumn);
			fieldColumnInfo.setColumnName(columnName);
			fieldColumnInfo.setDataConverter(converter);
			fieldColumnInfo.setDataformater(dataformater);
		}
	}

}
