package org.layz.hx.core.util.factory;

import org.layz.hx.core.util.RequestUtil;
import org.layz.hx.core.util.requestInfo.MemoCacheRequest;
import org.layz.hx.core.util.requestInfo.RequestWrapper;

public class RequestInfoFactory {
    private static final String SPRING = "spring";
    private static final Long multiply = 60 * 1000L;
    private RequestInfoFactory() {}

    public static void init(String type, Long timeOut){
        org.layz.hx.core.wrapper.system.RequestWrapper requestWrapper;
        if(SPRING.equalsIgnoreCase(type)) {
            requestWrapper = RequestWrapper.getInstance();
        } else {
            requestWrapper = new MemoCacheRequest();
        }
        requestWrapper.setTimeOut(timeOut * multiply);
        RequestUtil.getInstance().setRequestWrapper(requestWrapper);
    }
}
