package org.layz.hx.core.util;

import org.layz.hx.base.inte.ResponseEnum;
import org.layz.hx.core.enums.HxResponseEnum;
import org.layz.hx.core.pojo.response.JsonObjectResponse;
import org.layz.hx.core.pojo.response.JsonResponse;

public class ResponseUtil {
	private ResponseUtil() {
		
	}
	/**
	 * 获取成功通用返回
	 * @return
	 */
	public static JsonResponse getSuccessResponse() {
		return getSuccessResponse(HxResponseEnum.SUCCESS_RESP);
	}
	/**
	 * 获取成功通用返回
	 * @return
	 */
	public static JsonResponse getSuccessResponse(ResponseEnum response) {
		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.setMsg(response);
		return jsonResponse;
	}
	
	public static JsonResponse getObjectResponse(Object data) {
		JsonObjectResponse response = new JsonObjectResponse();
		response.setMsg(HxResponseEnum.SUCCESS_RESP);
		response.setData(data);
		return response;
	}
	/**
	 * 获取失败返回
	 * @return
	 */
	public static JsonResponse getFailResponse() {
		return getFailResponse(HxResponseEnum.UNKNOW_ERROR);
	}

	public static JsonResponse getFailResponse(ResponseEnum response) {
		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.setMsg(response);
		return jsonResponse;
	}
}
