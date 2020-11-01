package org.layz.hx.core.util.requestInfo;

import org.layz.hx.base.inte.BaseLoginInfo;
import org.layz.hx.core.support.RequestContext;

public class RequestWrapper implements org.layz.hx.core.wrapper.system.RequestWrapper {
    public static final RequestWrapper instance = new RequestWrapper();
    private Long timeOut;
    private RequestWrapper requestWrapper;

    public static RequestWrapper getInstance() {
        return instance;
    }

    public void setRequestWrapper(RequestWrapper requestWrapper) {
        requestWrapper.setTimeOut(this.timeOut);
        this.requestWrapper = requestWrapper;
    }

    @Override
    public String getToken() {
        return this.requestWrapper.getToken();
    }

    @Override
    public String getToken(String contextKey) {
        return this.requestWrapper.getToken(contextKey);
    }

    @Override
    public RequestContext getContextByToken(String token) {
        return this.requestWrapper.getContextByToken(token);
    }

    @Override
    public void setData(String token, RequestContext context) {
        this.requestWrapper.setData(token,context);
    }

    @Override
    public void setCache(String contextKey, String key, Object value) {
        this.requestWrapper.setCache(contextKey, key, value);
    }

    @Override
    public void setLoginInfo(String contextKey, BaseLoginInfo loginInfo) {
        this.requestWrapper.setLoginInfo(contextKey, loginInfo);
    }

    @Override
    public void removeByToken(String token) {
        this.requestWrapper.removeByToken(token);
    }

    @Override
    public void removeByKey(String contextKey) {
        this.requestWrapper.removeByKey(contextKey);
    }

    @Override
    public void removeCache(String contextKey, String key) {
        this.requestWrapper.removeCache(contextKey, key);
    }

    @Override
    public void tickOut(BaseLoginInfo baseLoginInfo) {
        this.requestWrapper.tickOut(baseLoginInfo);
    }

    @Override
    public void setTimeOut(Long timeOut) {
        this.timeOut = timeOut;
    }
}
