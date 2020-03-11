package org.layz.hx.core.util.factory;

import org.layz.hx.core.util.converter.DataConverter;
import org.layz.hx.core.util.converter.DefaultObjectDataConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
/**
 * 对象转换工厂
 *
 */
public class DataConverterFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(DataConverterFactory.class);
	private static final List<DataConverter> store;
	private static final DataConverter defaultConverter = new DefaultObjectDataConverter();
	
	private DataConverterFactory(){
		
	}
	
	static {
		LOGGER.info("init DataConverter");
		store = new ArrayList<>();
		ServiceLoader<DataConverter> load = ServiceLoader.load(DataConverter.class);
		for (DataConverter dataConverter : load) {
			store.add(dataConverter);
		}
	}
	
	public static DataConverter getConverter(Class<?> clazz){
		for (DataConverter converter : store) {
			if(converter.support(clazz)) {
				return converter;
			}
		}
		LOGGER.debug("use defaultConverter");
		return defaultConverter;
	}
}
