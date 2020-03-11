package org.layz.hx.spring.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;

import java.util.Collection;
import java.util.Collections;

/**
 * Spring 缓存
 */
public class HxSpringCacheManager extends SimpleCacheManager{
	private static final Logger LOGGER = LoggerFactory.getLogger(HxSpringCacheManager.class);

	@Override
	protected Collection<? extends Cache> loadCaches() {
		return Collections.emptyList();
	}
	
	@Override
	protected Cache getMissingCache(String name) {
		LOGGER.info("getMissingCache, name: {}", name);
		return new ConcurrentMapCache(name);
	}
}
