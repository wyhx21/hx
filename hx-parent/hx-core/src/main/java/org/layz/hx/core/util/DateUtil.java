package org.layz.hx.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
	private DateUtil() {
		LOGGER.debug("private init");
	}
	private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);
	private static final Map<String, SimpleDateFormat> formatMap = new ConcurrentHashMap<>();
	public static final String TIMESTAMP = "yyyy-MM-dd HH:mm:ss";

	/**
	 * @param source
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date parse(String source, String pattern) throws ParseException {
		return getDateformat(pattern).parse(source);
	}

	/**
	 * @param source
	 * @param pattern
	 * @return
	 */
	public static String format(Object source,String pattern){
		return getDateformat(pattern).format(source);
	}

	private static SimpleDateFormat getDateformat(String pattern) {
		SimpleDateFormat dateFormat = formatMap.get(pattern);
		if(null != dateFormat) {
			return dateFormat;
		}
		synchronized (formatMap) {
			dateFormat = new SimpleDateFormat(pattern);
			formatMap.put(pattern,dateFormat);
		}
		return dateFormat;
	}
}
