package org.layz.hx.core.util.factory;

import org.layz.hx.core.util.formater.Dataformater;
import org.layz.hx.core.util.formater.DefaultDataformater;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class DataformaterFactory {
	private static final List<Dataformater> repository;
	private static final Dataformater defaultFormater = new DefaultDataformater();
	
	static {
		repository = new ArrayList<>();
		ServiceLoader<Dataformater> load = ServiceLoader.load(Dataformater.class);
		for (Dataformater dataformater : load) {
			repository.add(dataformater);
		}
	}
	/**
	 * 获取转换器
	 * @param formatType
	 * @return
	 */
	public static Dataformater getDataformater(String formatType) {
		for (Dataformater dataformater : repository) {
			if(dataformater.support(formatType)) {
				return dataformater;
			}
		}
		return defaultFormater;
	}
}
