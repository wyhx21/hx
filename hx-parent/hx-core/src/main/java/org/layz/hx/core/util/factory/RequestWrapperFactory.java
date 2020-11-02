package org.layz.hx.core.util.factory;

import org.layz.hx.core.util.RequestUtil;
import org.layz.hx.core.util.request.MemoCacheRequest;
import org.layz.hx.core.util.request.RequestWrapperImpl;

public class RequestWrapperFactory {
    private static final String SPRING = "spring";
    private static final Long multiply = 60 * 1000L;
    private RequestWrapperFactory() {}

    public static void init(String type, Long timeOut){
        org.layz.hx.core.wrapper.system.RequestWrapper requestWrapper;
        if(SPRING.equalsIgnoreCase(type)) {
            requestWrapper = RequestWrapperImpl.getInstance();
        } else {
            requestWrapper = new MemoCacheRequest();
        }
        requestWrapper.setTimeOut(timeOut * multiply);
        RequestUtil.getInstance().setRequestWrapper(requestWrapper);
    }
}
