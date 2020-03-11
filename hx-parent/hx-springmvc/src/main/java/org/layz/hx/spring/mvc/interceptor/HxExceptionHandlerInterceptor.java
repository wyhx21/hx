package org.layz.hx.spring.mvc.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.layz.hx.core.pojo.response.JsonResponse;
import org.layz.hx.spring.mvc.handler.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;


public class HxExceptionHandlerInterceptor implements HandlerInterceptor{
	private static final Logger LOGGER = LoggerFactory.getLogger(HxExceptionHandlerInterceptor.class);
	private List<ExceptionHandler> exceptionHandlerList;
	private ExceptionHandler defaultExceptionHandler;
	
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		if(null == ex) {
			return;
		}
		JsonResponse jsonResult = getHandleResult(ex);
		if(null == jsonResult) {
			LOGGER.info("handle exception, jsonRespons is null", ex.getMessage());
			return;
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		String res = JSONObject.toJSONString(jsonResult);
		try(PrintWriter writer = response.getWriter()){
			writer.write(res);
		}
	}

	private JsonResponse getHandleResult(Exception ex) {
		if(null != exceptionHandlerList && !exceptionHandlerList.isEmpty()) {
			for (ExceptionHandler exceptionHandler : exceptionHandlerList) {
				if(exceptionHandler.support(ex)) {
					LOGGER.info("handle exception", ex);
					return exceptionHandler.handle(ex);
				}
			}
		}
		LOGGER.error("defaultExceptionHandler", ex);
		if(null != defaultExceptionHandler) {
			return defaultExceptionHandler.handle(ex);
		}
		return null;
	}

	public void setExceptionHandlerList(List<ExceptionHandler> exceptionHandlerList) {
		this.exceptionHandlerList = exceptionHandlerList;
	}
	
	public void setDefaultExceptionHandler(ExceptionHandler defaultExceptionHandler) {
		this.defaultExceptionHandler = defaultExceptionHandler;
	}
}
