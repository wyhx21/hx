package org.layz.hx.core.util;

import org.layz.hx.base.inte.BaseLoginInfo;
import org.layz.hx.core.support.RequestContext;
import org.layz.hx.core.wrapper.system.RequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestUtil.class);
    // 当前状态
    private static ThreadLocal<RequestContext> threadLocal = new ThreadLocal<>();

    private RequestWrapper requestWrapper;

    private static RequestUtil instance = new RequestUtil();

    private RequestUtil(){

    }

    public static RequestUtil getInstance(){
        return instance;
    }
    /*****************************************************************************************/
    /**
     * 查询当前登录信息
     * @return
     */
    public BaseLoginInfo getLoginInfo(){
        RequestContext context = threadLocal.get();
        if(null == context) {
            LOGGER.info("context is null");
            return null;
        }
        return context.getLoginInfo();
    }
    /**
     * 获取缓存
     * @return
     */
    public Object getCache(String key){
        RequestContext context = threadLocal.get();
        if(null == context) {
            LOGGER.info("context is null");
            return null;
        }
        return context.getCacheMap().get(key);
    }
    /*****************************************************************************************/
    /**
     *  根据 token 设置当前登录信息
     * @param token
     */
    public void setToken(String token){
        RequestContext context = requestWrapper.getContextByToken(token);
        if(null == context) {
            LOGGER.info("context is null, token: {}", token);
            remove();
            return;
        }
        threadLocal.set(context);
    }
    /**
     * @param loginInfo
     * 新增并生成 token
     * @return
     */
    public String login(BaseLoginInfo loginInfo){
        String sessionKey = loginInfo.getKey();
        // 如果已经存在，删除原有的
        String oldToken = requestWrapper.getToken(sessionKey);
        if(null != oldToken) {
            requestWrapper.removeByToken(oldToken);
        }
        String token = requestWrapper.getToken();
        RequestContext context = new RequestContext();
        context.setKey(sessionKey);
        context.setSessionTime();
        context.setLoginInfo(loginInfo);
        requestWrapper.setData(token,context);
        threadLocal.set(context);
        return token;
    }
    /**
     * 设置缓存
     * @param key
     * @param value
     */
    public void setCache(String key, Object value){
        RequestContext context = threadLocal.get();
        if(null == context) {
            LOGGER.info("context is null");
            return;
        }
        context.getCacheMap().put(key,value);
        requestWrapper.setCache(context.getKey(), key, value);
    }
    /**
     * 更新登录信息
     * @param loginInfo
     */
    public void setLoginInfo(BaseLoginInfo loginInfo){
        RequestContext context = threadLocal.get();
        if(null == context) {
            LOGGER.info("context is null");
            return;
        }
        context.setLoginInfo(loginInfo);
        requestWrapper.setLoginInfo(context.getKey(), loginInfo);
    }
    /*****************************************************************************************/

    /**
     * 清除缓存信息
     */
    public void removeCache(String key){
        RequestContext context = threadLocal.get();
        if(null == context) {
            LOGGER.info("context is null");
            return;
        }
        context.getCacheMap().remove(key);
        requestWrapper.removeCache(context.getKey(), key);
    }

    /**
     * 退出登录
     */
    public void loginOut(){
        RequestContext context = threadLocal.get();
        if(null == context) {
            LOGGER.info("context is null");
            return;
        }
        requestWrapper.removeByKey(context.getKey());
        remove();
    }

    /**
     * 踢出
     * @param baseLoginInfo
     */
    public void tickOut(BaseLoginInfo baseLoginInfo){
        requestWrapper.tickOut(baseLoginInfo);
    }
    /*****************************************************************************************/
    /**
     * threadLocal.remove()
     */
    public void remove(){
        threadLocal.remove();
    }

    public void setRequestWrapper(RequestWrapper requestWrapper) {
        this.requestWrapper = requestWrapper;
    }
}
