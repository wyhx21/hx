package org.layz.hx.core.util.factory;

import org.layz.hx.core.inte.RequestInfo;
import org.layz.hx.core.util.RequestUtil;
import org.layz.hx.core.util.requestInfo.MemoCacheRequest;
import org.layz.hx.core.util.requestInfo.RequestWrapper;

public class RequestInfoFactory {
    private static final String SPRING = "spring";
    private RequestInfoFactory() {}

    public static void init(String type, Long timeOut){
        RequestInfo requestInfo;
        if(SPRING.equalsIgnoreCase(type)) {
            requestInfo = RequestWrapper.getInstance();
        } else {
            requestInfo = new MemoCacheRequest();
        }
        requestInfo.setTimeOut(timeOut);
        RequestUtil.getInstance().setRequestInfo(requestInfo);
    }
}
