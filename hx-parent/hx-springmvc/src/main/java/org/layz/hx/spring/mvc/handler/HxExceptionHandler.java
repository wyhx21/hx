package org.layz.hx.spring.mvc.handler;

import org.apache.commons.lang3.StringUtils;
import org.layz.hx.base.exception.HxRuntimeException;
import org.layz.hx.base.inte.ResponseEnum;
import org.layz.hx.core.enums.HxResponseEnum;
import org.layz.hx.core.pojo.response.JsonResponse;

public class HxExceptionHandler implements ExceptionHandler{

	@Override
	public boolean support(Throwable ex) {
		return ex instanceof HxRuntimeException;
	}

	@Override
	public JsonResponse handle(Throwable ex) {
		HxRuntimeException e = (HxRuntimeException)ex;
		String code = e.getCode();
		String message = e.getMessage();
		if(StringUtils.isBlank(code)) {
			code = HxResponseEnum.UNKNOW_ERROR.getCode();
		}
		if(StringUtils.isBlank(message)) {
			message = HxResponseEnum.UNKNOW_ERROR.getMsg();
		}
		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.setRespCode(code);
		jsonResponse.setRespMsg(message);
		jsonResponse.setSuccess(ResponseEnum.FAIL);
		return jsonResponse;
	}
}
