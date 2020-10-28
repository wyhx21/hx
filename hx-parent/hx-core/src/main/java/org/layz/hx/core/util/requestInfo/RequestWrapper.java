package org.layz.hx.core.util.requestInfo;

import org.layz.hx.base.inte.BaseLoginInfo;
import org.layz.hx.core.inte.RequestInfo;
import org.layz.hx.core.support.RequestContext;

public class RequestWrapper implements RequestInfo {
    public static final RequestWrapper requestInfo = new RequestWrapper();
    private Long timeOut;
    private RequestInfo request;

    public static RequestWrapper getInstance() {
        return requestInfo;
    }

    public void setRequestInfo(RequestInfo requestInfo) {
        requestInfo.setTimeOut(this.timeOut);
        this.request = requestInfo;
    }

    @Override
    public String getToken() {
        return this.request.getToken();
    }

    @Override
    public String getToken(String contextKey) {
        return this.request.getToken(contextKey);
    }

    @Override
    public RequestContext getContextByToken(String token) {
        return this.request.getContextByToken(token);
    }

    @Override
    public void setData(String token, RequestContext context) {
        this.request.setData(token,context);
    }

    @Override
    public void setCache(String contextKey, String key, Object value) {
        this.request.setCache(contextKey, key, value);
    }

    @Override
    public void setLoginInfo(String contextKey, BaseLoginInfo loginInfo) {
        this.request.setLoginInfo(contextKey, loginInfo);
    }

    @Override
    public void removeByToken(String token) {
        this.request.removeByToken(token);
    }

    @Override
    public void removeByKey(String contextKey) {
        this.request.removeByKey(contextKey);
    }

    @Override
    public void removeCache(String contextKey, String key) {
        this.request.removeCache(contextKey, key);
    }

    @Override
    public void tickOut(String sessionKey) {
        this.request.tickOut(sessionKey);
    }

    @Override
    public void setTimeOut(Long timeOut) {
        this.timeOut = timeOut;
    }
}
