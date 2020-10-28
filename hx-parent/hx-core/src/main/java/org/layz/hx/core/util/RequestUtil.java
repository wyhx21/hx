package org.layz.hx.core.util;

import org.layz.hx.base.inte.BaseLoginInfo;
import org.layz.hx.base.util.StringUtil;
import org.layz.hx.core.support.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class RequestUtil {
    private Long timeOut = 30 * 60 * 1000L;
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestUtil.class);
    // 当前状态
    private static ThreadLocal<RequestContext> threadLocal = new ThreadLocal<>();
    // key:token
    private Map<String,RequestContext> contextMap = new ConcurrentHashMap<>();
    // sessionKey,token
    private Map<String,String> sessionKeyMap = new ConcurrentHashMap<>();

    private static RequestUtil instance = new RequestUtil();

    private RequestUtil(){

    }

    public static RequestUtil getInstance(){
        return instance;
    }

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
    }

    /**
     * 清除缓存信息
     */
    public void clearCache(){
        RequestContext context = threadLocal.get();
        if(null == context) {
            LOGGER.info("context is null");
            return;
        }
        context.getCacheMap().clear();
    }

    /**
     *  根据 token 设置当前登录信息
     * @param token
     */
    public void setContext(String token){
        if(StringUtil.isBlank(token)) {
            LOGGER.info("token is blank");
            remove();
            return;
        }
        RequestContext context = contextMap.get(token);
        if(null == context) {
            LOGGER.info("context is null, token: {}", token);
            remove();
            return;
        }
        // 判断是否超时
        if(timeOutValid(context)) {
            return;
        }
        context.setSessionTime();
        threadLocal.set(context);
    }

    /**
     * 更新登录信息
     * @param loginInfo
     */
    public void update(BaseLoginInfo loginInfo){
        RequestContext context = threadLocal.get();
        if(null == context) {
            LOGGER.info("context is null");
            return;
        }
        context.setLoginInfo(loginInfo);
    }
    /**
     * @param loginInfo
     * 新增并生成 token
     * @return
     */
    public String login(BaseLoginInfo loginInfo){
        this.clean();
        String sessionKey = loginInfo.getKey();
        // 如果已经存在，删除原有的
        String oldToken = sessionKeyMap.get(sessionKey);
        if(null != oldToken) {
            sessionKeyMap.remove(sessionKey);
            contextMap.remove(oldToken);
        }
        String token = getToken();
        RequestContext context = new RequestContext();
        context.setKey(sessionKey);
        context.getSessionTime();
        context.setSessionTime();
        context.setLoginInfo(loginInfo);
        sessionKeyMap.put(sessionKey,token);
        contextMap.put(token, context);
        threadLocal.set(context);
        return token;
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
        remove(context.getKey());
    }

    public void remove(){
        threadLocal.remove();
    }
    /**
     * 获取新的token
     * @return
     */
    private String getToken(){
        while (true) {
            String token = UUID.randomUUID().toString();
            RequestContext context = contextMap.get(token);
            if(null == context) {
                return token;
            }
        }
    }

    /**
     * 清除超时信息
     */
    public void clean(){
        Collection<RequestContext> values = contextMap.values();
        for (RequestContext context : values) {
            timeOutValid(context);
        }
    }

    public void setTimeOut(Long timeOut) {
        this.timeOut = timeOut * 60 * 1000;
    }

    /**
     * 超时校验
     * @param context
     * @return
     */
    private boolean timeOutValid(RequestContext context){
        if((System.currentTimeMillis() - context.getSessionTime()) > timeOut) {
            remove(context.getKey());
            return true;
        }
        return false;
    }

    /**
     * 删除信息
     * @param sessionKey
     */
    private void remove(String sessionKey){
        String token = sessionKeyMap.get(sessionKey);
        contextMap.remove(token);
        sessionKeyMap.remove(sessionKey);
        remove();
    }
}
