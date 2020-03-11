package org.layz.hx.spring.mvc.handler;

import org.layz.hx.core.pojo.response.JsonResponse;
import org.layz.hx.core.util.ResponseUtil;

public class DefaultExceptionHandler implements ExceptionHandler{

	@Override
	public boolean support(Throwable ex) {
		return ex instanceof Throwable;
	}

	@Override
	public JsonResponse handle(Throwable ex) {
		return ResponseUtil.getFailResponse();
	}

}
