package org.layz.hx.spring.mvc.converter;

import java.util.Date;

import org.layz.hx.core.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

public class DateConverter implements Converter<String, Date>{
	private static final Logger LOGGER = LoggerFactory.getLogger(DateConverter.class);
	private static final String REGEX = "[- :]";
	private static final String NONE = "";
	private String[] patterns;
	
	@Override
	public Date convert(String source) {
		if(null == source) {
			LOGGER.debug("convert source is null");
			return null;
		}
		LOGGER.debug("convert source: {}", source);
		Date date = null;
		source = source.replaceAll(REGEX, NONE);
		for (String pattern : patterns) {
			try {
				date = DateUtil.parse(source,pattern);
				break;
			} catch (Exception e) {
				LOGGER.debug("convert error,pattern: {},source: {},msg: {}", pattern, source, e.getMessage());
			}
		}
		return date;
	}
	
	public void setPatterns(String value) {
		this.patterns = value.split(",");
	}
}
