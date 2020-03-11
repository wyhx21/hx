package org.layz.hx.core.util.factory;

import org.layz.hx.core.util.reader.DataReader;
import org.layz.hx.core.util.reader.DefaultObjectReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class DataReaderFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(DataReaderFactory.class);
	private static final List<DataReader> store;
	private static final DataReader defaultReader = new DefaultObjectReader();
	
	static {
		store = new ArrayList<>();
		ServiceLoader<DataReader> load = ServiceLoader.load(DataReader.class);
		for (DataReader dataReader : load) {
			store.add(dataReader);
		}
	}
	
	public static DataReader getDataReader(Object object){
		for (DataReader dataReader : store) {
			if(dataReader.supportType(object)) {
				return dataReader;
			}
		}
		// LOGGER.debug("use defaultReader");
		return defaultReader;
	}
}
