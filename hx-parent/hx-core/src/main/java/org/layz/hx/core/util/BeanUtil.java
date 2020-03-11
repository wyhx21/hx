package org.layz.hx.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.layz.hx.base.pojo.Page;
import org.layz.hx.core.pojo.info.FieldColumnInfo;
import org.layz.hx.core.pojo.info.TableClassInfo;
import org.layz.hx.core.support.HxTableSupport;
import org.layz.hx.core.util.factory.CacheSetBuild;
import org.layz.hx.core.util.factory.DataReaderFactory;
import org.layz.hx.core.util.reader.DataReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(BeanUtil.class);
	private BeanUtil() {
		
	}
	/**
	 * @param <T>
	 * @param clazz
	 * @param source
	 * @return
	 */
	public static<T> T getBean(Class<T> clazz, Object source) {
		TableClassInfo tableClassInfo = HxTableSupport.getTableClassInfo(clazz);
		return getBean(clazz, source, tableClassInfo);
	}
	/**
	 * 
	 * @param <T>
	 * @param clazz
	 * @param source
	 * @param tableClassInfo
	 * @return
	 */
	public static<T> T getBean(Class<T> clazz, Object source, TableClassInfo tableClassInfo) {
		Map<Object, Object> cacheMap = new HashMap<Object, Object>();
		CacheSetBuild.setCache(tableClassInfo.getFieldList(), source, cacheMap);
		return getBean(clazz, source, tableClassInfo, cacheMap);
	}
	/**
	 * @param <T>
	 * @param clazz
	 * @param source
	 * @return
	 */
	public static<T> Page<T> getPage(Class<T> clazz, Page<?> source) {
		if(null == source) {
			return null;
		}
		Page<T> page = new Page<T>();
		page.setPage(source.getPage());
		page.setSize(source.getSize());
		page.setTotal(source.getTotal());
		page.setData(getList(clazz, source.getData()));
		return page;
	}
	/**
	 * @param <T>
	 * @param clazz
	 * @param source
	 * @return
	 */
	public static<T> List<T> getList(Class<T> clazz, List<?> source) {
		TableClassInfo tableClassInfo = HxTableSupport.getTableClassInfo(clazz);
		return getList(clazz, source, tableClassInfo);
	}
	/**
	 * @param <T>
	 * @param clazz
	 * @param source
	 * @param tableClassInfo
	 * @return
	 */
	public static<T> List<T> getList(Class<T> clazz, List<?> source, TableClassInfo tableClassInfo) {
		return getList(clazz, source, tableClassInfo, null);
	}
	/**
	 * @param <T>
	 * @param clazz
	 * @param source
	 * @param tableClassInfo
	 * @param cacheMap
	 * @return
	 */
	public static<T> List<T> getList(Class<T> clazz, List<?> source, TableClassInfo tableClassInfo, Map<Object, Object> cacheMap){
		// LOGGER.debug("clazz: {}", clazz);
		if(null == source || null == clazz || source.isEmpty()) {
			LOGGER.debug("clazz is null or source is empty");
			return null;
		}
		if(null == cacheMap) {
			cacheMap = new HashMap<Object, Object>();
		}
		CacheSetBuild.setCacheList(tableClassInfo.getFieldList(), source, cacheMap);
		List<T> resultList = new ArrayList<T>(source.size());
		for (Object object : source) {
			T bean = getBean(clazz, object, tableClassInfo, cacheMap);
			if(null != bean) {
				resultList.add(bean);
			}
		}
		return resultList;
	}

	/**
	 * 参数转换
	 * @param <T>
	 * @param clazz
	 * @param source
	 * @param tableClassInfo
	 * @param cacheMap
	 * @return
	 */
	private static<T> T getBean(Class<T> clazz, Object source, TableClassInfo tableClassInfo, Map<Object, Object> cacheMap) {
		// LOGGER.debug("clazz: {}", clazz);
		if(null == source || null == clazz) {
			LOGGER.debug("clazz is null or source is null");
			return null;
		}
		T instance; 
		try {
			instance = clazz.newInstance();
		} catch (Exception e) {
			LOGGER.debug("newInstance error, class: {}", clazz, e);
			return null;
		}
		if(null == tableClassInfo) {
			LOGGER.debug("tableClassInfo is null, class: {}", clazz);
			return instance;
		}
		if(null == cacheMap) {
			cacheMap = new HashMap<Object, Object>();
		}
		List<FieldColumnInfo> fieldList = tableClassInfo.getFieldList();
		if(null == fieldList || fieldList.isEmpty()) {
			LOGGER.debug("fieldList is empty, class: {}", clazz);
			return instance;
		}
		DataReader dataReader = DataReaderFactory.getDataReader(source);
		for (FieldColumnInfo fieldColumnInfo : fieldList) {
			try {
				Object value = dataReader.getObject(source, fieldColumnInfo);
				value = fieldColumnInfo.getDataformater().format(value, fieldColumnInfo, cacheMap);
				value = fieldColumnInfo.getDataConverter().dataConvert(value, cacheMap);
				fieldColumnInfo.getMethodSet().invoke(instance, value);
			} catch (Exception e) {
				LOGGER.debug("set field error, class: {}, field: {}", clazz, fieldColumnInfo.getFieldName());
			}
		}
		return instance;
	}
}
