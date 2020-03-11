package org.layz.hx.spring.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class SpringContextUtil implements BeanFactoryAware {
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringContextUtil.class);
	private static BeanFactory beanFactory;

	/**
	 * _获取Spring容器对象
	 * @param name
	 * @return
	 */
	public static<T> T getBean(String name){
		if(null == beanFactory) {
			LOGGER.debug("beanFactory is null");
			return null;
		}
		if(null == name || name.length() < 1) {
			LOGGER.debug("name is blank");
			return null;
		}
		try {
			return (T) beanFactory.getBean(name);
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
		if(null == beanFactory) {
			LOGGER.debug("beanFactory is null");
			return null;
		}
		if(null == clazz) {
			LOGGER.debug("class is blank");
			return null;
		}
		try {
			return beanFactory.getBean(clazz);
		} catch (Throwable e) {
			LOGGER.debug("bean is not exsist, class: {}", clazz);
			return null;
		}
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		SpringContextUtil.beanFactory = beanFactory;
	}
}
