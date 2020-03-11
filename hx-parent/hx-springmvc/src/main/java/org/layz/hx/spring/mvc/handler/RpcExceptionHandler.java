package org.layz.hx.spring.mvc.handler;

import org.layz.hx.core.enums.HxResponseEnum;
import org.layz.hx.core.pojo.response.JsonResponse;
import org.layz.hx.core.util.ResponseUtil;

public class RpcExceptionHandler implements ExceptionHandler {
    @Override
    public boolean support(Throwable ex) {
        return ex.getClass().getName().equals("org.apache.dubbo.rpc.RpcException");
    }

    @Override
    public JsonResponse handle(Throwable ex) {
        return ResponseUtil.getFailResponse(HxResponseEnum.RPC_ERROR);
    }
}
