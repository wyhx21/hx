package org.layz.hx.core.util.request;

import org.layz.hx.base.inte.BaseLoginInfo;
import org.layz.hx.base.util.StringUtil;
import org.layz.hx.core.support.RequestContext;
import org.layz.hx.core.wrapper.system.RequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MemoCacheRequest implements RequestWrapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemoCacheRequest.class);
    private Long timeOut;
    /**
     * key:token
     * value: context
     */
    private Map<String,RequestContext> contextMap = new ConcurrentHashMap<>();
    /**
     * key:contextKey
     * value: token
     */
    private Map<String,String> sessionKeyMap = new ConcurrentHashMap<>();

    @Override
    public String getToken() {
        while (true) {
            String token = UUID.randomUUID().toString();
            if(null == this.contextMap.get(token)) {
                return token;
            }
        }
    }

    @Override
    public String getToken(String contextKey) {
        if(StringUtil.isBlank(contextKey)) {
            LOGGER.debug("contextKey is blank");
            return null;
        }
        return this.sessionKeyMap.get(contextKey);
    }

    @Override
    public RequestContext getContextByToken(String token) {
        if(StringUtil.isBlank(token)) {
            LOGGER.debug("token is blank");
            return null;
        }
        RequestContext context = this.contextMap.get(token);
        if(null == context) {
            return null;
        }
        boolean isTimeOut = this.validTimeOut(context);
        if(isTimeOut) {
            LOGGER.debug("token is timeOut");
            return null;
        }
        context.setSessionTime();
        return context;
    }


    @Override
    public void setData(String token, RequestContext context) {
        if(StringUtil.isBlank(token)) {
            LOGGER.info("token is blank");
            return;
        }
        if(null == context || StringUtil.isBlank(context.getKey())) {
            LOGGER.info("context is error");
            return;
        }
        this.clear();
        this.contextMap.put(token,context);
        this.sessionKeyMap.put(context.getKey(), token);
    }

    @Override
    public void setCache(String contextKey, String key, Object value) {
        if(null == value) {
            this.removeCache(contextKey, key);
        }
        RequestContext context = this.getContextByKey(contextKey);
        if(null != context) {
            context.getCacheMap().put(key, value);
        }
    }

    @Override
    public void setLoginInfo(String contextKey, BaseLoginInfo loginInfo) {
        RequestContext context = this.getContextByKey(contextKey);
        if(null != context) {
            context.setLoginInfo(loginInfo);
        }
    }

    @Override
    public void removeByToken(String token) {
        if(StringUtil.isBlank(token)) {
            LOGGER.info("token is blank");
            return;
        }
        RequestContext context = this.contextMap.get(token);
        this.contextMap.remove(token);
        if(null == context) {
            return;
        }
        String contextKey = context.getKey();
        if(StringUtil.isBlank(contextKey)) {
            return;
        }
        this.sessionKeyMap.remove(contextKey);
    }

    @Override
    public void removeByKey(String contextKey) {
        if(StringUtil.isBlank(contextKey)) {
            return;
        }
        String token = sessionKeyMap.get(contextKey);
        sessionKeyMap.remove(contextKey);
        if(StringUtil.isBlank(token)) {
            return;
        }
        contextMap.remove(token);
    }

    @Override
    public void removeCache(String contextKey, String key) {
        RequestContext context = this.getContextByKey(contextKey);
        if(null != context) {
            context.getCacheMap().remove(key);
        }
    }

    @Override
    public void tickOut(BaseLoginInfo baseLoginInfo) {
        if(null == baseLoginInfo) {
            LOGGER.debug("baseLoginInfo is null");
            return;
        }
        String sessionKey = baseLoginInfo.getKey();
        if(StringUtil.isBlank(sessionKey)) {
            LOGGER.debug("sessionKey is blank");
            return;
        }
        String token = this.sessionKeyMap.get(sessionKey);
        this.sessionKeyMap.remove(sessionKey);
        if(StringUtil.isBlank(token)) {
            return;
        }
        this.contextMap.remove(token);
    }

    @Override
    public void setTimeOut(Long timeOut) {
        this.timeOut = timeOut;
    }

    /**
     * 清除
     */
    private void clear(){
        Collection<RequestContext> contextCollection = this.contextMap.values();
        for (RequestContext context : contextCollection) {
            validTimeOut(context);
        }
    }
    /**
     * 超时校验
     * @param context
     * @return true -> 已超时
     */
    private boolean validTimeOut(RequestContext context) {
        if((System.currentTimeMillis() - context.getSessionTime()) > this.timeOut) {
            this.removeByKey(context.getKey());
            return true;
        }
        return false;
    }

    /**
     * @param contextKey
     * @return
     */
    private RequestContext getContextByKey(String contextKey){
        if(StringUtil.isBlank(contextKey)){
            LOGGER.info("contextKey is blank");
            return null;
        }
        String token = sessionKeyMap.get(contextKey);
        if(StringUtil.isBlank(token)) {
            LOGGER.info("token is blank");
            return null;
        }
        return contextMap.get(token);
    }
}
