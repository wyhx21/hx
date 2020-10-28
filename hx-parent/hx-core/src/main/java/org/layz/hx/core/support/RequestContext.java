package org.layz.hx.core.support;

import org.layz.hx.base.inte.BaseLoginInfo;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestContext implements Serializable {
    private String key;
    private Long sessionTime;
    private Map<String,Object> cacheMap = new ConcurrentHashMap<>();
    private BaseLoginInfo loginInfo;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setSessionTime() {
        this.sessionTime = System.currentTimeMillis();
    }

    public Long getSessionTime() {
        return sessionTime;
    }

    public Map<String, Object> getCacheMap() {
        return cacheMap;
    }

    public BaseLoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(BaseLoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }
}
