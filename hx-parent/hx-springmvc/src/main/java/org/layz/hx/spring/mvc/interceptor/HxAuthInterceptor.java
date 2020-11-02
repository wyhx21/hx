package org.layz.hx.spring.mvc.interceptor;

import org.layz.hx.base.exception.HxRuntimeException;
import org.layz.hx.base.util.Assert;
import org.layz.hx.base.util.StringUtil;
import org.layz.hx.core.enums.HxResponseEnum;
import org.layz.hx.spring.mvc.auth.annotation.RequirePermission;
import org.layz.hx.spring.mvc.auth.annotation.RequireUrlPermission;
import org.layz.hx.spring.mvc.auth.enums.Logical;
import org.layz.hx.spring.mvc.wrapper.AuthWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class HxAuthInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(HxAuthInterceptor.class);
    private static final String NONE = "";
    private static final String ONE = "/";
    private static final String TWO = "//";
    @Value("${permissionGroup:}")
    private String permissionGroup;
    private AuthWrapper authWrapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(null == handler) {
            return true;
        }
        if(handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            validatorUrlPermission(request, method);
            validatorRequirePermission(method);
        }
        return true;
    }

    /**
     * URL 权限控制
     * @param request
     * @param method
     */
    private void validatorUrlPermission(HttpServletRequest request, Method method){
        RequireUrlPermission permission = method.getAnnotation(RequireUrlPermission.class);
        if(null == permission) {
            return;
        }
        validPermissionGroup(permission.group());
        String contextPath = request.getContextPath();
        String requestURI = request.getRequestURI();
        String url = requestURI.replace(contextPath,NONE).replaceAll(TWO,ONE);
        List<String> authUrl = authWrapper.authUrl();
        Assert.isNotEmpty(authUrl, HxResponseEnum.NOT_AUTH_ERROR);
        for (String auth : authUrl) {
            if(url.startsWith(auth)) {
                return;
            }
        }
        LOGGER.info("authValid error, url is: {}", url);
        Assert.isFalse(true, HxResponseEnum.NOT_AUTH_ERROR);
    }

    private void validatorRequirePermission(Method method){
        RequirePermission permission = method.getAnnotation(RequirePermission.class);
        if(null == permission) {
            return;
        }
        validPermissionGroup(permission.group());
        Logical type = permission.type();
        String[] value = permission.value();
        List<String> authCode = authWrapper.authCode();
        try {
            Assert.isNotEmpty(authCode, HxResponseEnum.NOT_AUTH_ERROR);
            if(type == Logical.AND) {
                for (String auth : value) {
                    Assert.isTrue(authCode.contains(auth),HxResponseEnum.NOT_AUTH_ERROR);
                }
            } else if (type == Logical.OR) {
                for (String auth : value) {
                    if(authCode.contains(auth)) {
                        return;
                    }
                }
                Assert.isFalse(true, HxResponseEnum.NOT_AUTH_ERROR);
            }
        } catch (HxRuntimeException e) {
            LOGGER.info("authValid error, require permission is: {}", Arrays.toString(value));
            throw e;
        }
    }

    /**
     * 权限组校验
     * @param group
     */
    private void validPermissionGroup(String group){
        if(StringUtil.isNotBlank(group)) {
            try {
                Assert.isTrue(group.equals(this.permissionGroup), HxResponseEnum.NOT_AUTH_ERROR);
            } catch (HxRuntimeException e) {
                LOGGER.info("validPermissionGroup error, require group: {}, systemGroup is: {}", group, this.permissionGroup);
                throw e;
            }
        }
    }

    public void setAuthWrapper(AuthWrapper authWrapper) {
        this.authWrapper = authWrapper;
    }
}
