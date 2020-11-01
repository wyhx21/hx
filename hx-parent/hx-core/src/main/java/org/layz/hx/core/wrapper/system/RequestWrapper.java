package org.layz.hx.core.wrapper.system;

import org.layz.hx.base.inte.BaseLoginInfo;
import org.layz.hx.core.support.RequestContext;

public interface RequestWrapper {
    /**
     * 获取新的 token
     * @return
     */
    String getToken();

    /**
     * 获取token
     * @param contextKey
     * @return
     */
    String getToken(String contextKey);

    /**
     * 根据token查询
     * @param token
     * @return
     */
    RequestContext getContextByToken(String token);

    /**
     * 设置值
     * @param token
     * @param context
     */
    void setData(String token, RequestContext context);

    /**
     * 缓存设置
     * @param contextKey
     * @param key
     * @param value
     */
    void setCache(String contextKey, String key, Object value);

    /**
     * 登录信息设置
     * @param contextKey
     * @param loginInfo
     */
    void setLoginInfo(String contextKey, BaseLoginInfo loginInfo);

    /**
     * 根据token 删除
     * @param token
     */
    void removeByToken(String token);

    /**
     * 删除
     * @param contextKey
     */
    void removeByKey(String contextKey);

    /**
     * 清除缓存
     * @param contextKey
     * @param key
     */
    void removeCache(String contextKey, String key);

    /**
     * 剔除
     * @param baseLoginInfo
     */
    void tickOut(BaseLoginInfo baseLoginInfo);

    /**
     * 设置超时时间
     * @param timeOut
     */
    default void setTimeOut(Long timeOut) {

    }
}
