package org.layz.hx.spring.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware {
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringContextUtil.class);
	private static ApplicationContext applicationContext;

	/**
	 * _获取Spring容器对象
	 * @param name
	 * @return
	 */
	public static<T> T getBean(String name){
		if(null == applicationContext) {
			LOGGER.debug("beanFactory is null");
			return null;
		}
		if(null == name || name.length() < 1) {
			LOGGER.debug("name is blank");
			return null;
		}
		try {
			return (T) applicationContext.getBean(name);
		} catch (Throwable e) {
			LOGGER.debug("bean is not exsist, name: {}", name);
			return null;
		}
	}

	/**
	 * _获取Spring容器对象
	 * @param clazz
	 * @return
	 */
	public static<T> T getBean(Class<T> clazz){
		if(null == applicationContext) {
			LOGGER.debug("beanFactory is null");
			return null;
		}
		if(null == clazz) {
			LOGGER.debug("class is blank");
			return null;
		}
		try {
			return applicationContext.getBean(clazz);
		} catch (Throwable e) {
			LOGGER.debug("bean is not exsist, class: {}", clazz);
			return null;
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		applicationContext = context;
	}
}
