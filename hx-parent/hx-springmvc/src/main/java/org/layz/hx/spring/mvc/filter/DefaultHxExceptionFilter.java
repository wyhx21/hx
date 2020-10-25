package org.layz.hx.spring.mvc.filter;

import com.alibaba.fastjson.JSONObject;
import org.layz.hx.base.util.StringUtil;
import org.layz.hx.core.pojo.response.JsonResponse;
import org.layz.hx.spring.mvc.handler.DefaultExceptionHandler;
import org.layz.hx.spring.mvc.handler.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class DefaultHxExceptionFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultHxExceptionFilter.class);
    private List<ExceptionHandler> exceptionHandlerList;
    private ExceptionHandler defaultExceptionHandler;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String className = filterConfig.getInitParameter("defaultExceptionHandler");
        if(StringUtil.isBlank(className)) {
            this.defaultExceptionHandler = new DefaultExceptionHandler();
        } else {
            try {
                this.defaultExceptionHandler = (ExceptionHandler)Class.forName(className).newInstance();
            } catch (Exception e) {
                LOGGER.error("{}, is not found", className);
            }
        }
        exceptionHandlerList = new ArrayList<>();
        ServiceLoader<ExceptionHandler> load = ServiceLoader.load(ExceptionHandler.class);
        for (ExceptionHandler exceptionHandler : load) {
            exceptionHandlerList.add(exceptionHandler);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Exception ex) {
            if(null == ex) {
                return;
            }
            JsonResponse jsonResult = getHandleResult(ex.getCause());
            if(null == jsonResult) {
                LOGGER.info("handle exception, jsonRespons is null", ex.getMessage());
                return;
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String res = JSONObject.toJSONString(jsonResult);
            try(PrintWriter writer = response.getWriter()){
                writer.write(res);
            }
        }
    }

    private JsonResponse getHandleResult(Throwable ex) {
        if(null != exceptionHandlerList && !exceptionHandlerList.isEmpty()) {
            for (ExceptionHandler exceptionHandler : exceptionHandlerList) {
                if(exceptionHandler.support(ex)) {
                    LOGGER.info("handle exception", ex);
                    return exceptionHandler.handle(ex);
                }
            }
        }
        LOGGER.error("defaultExceptionHandler", ex);
        if(null != defaultExceptionHandler) {
            return defaultExceptionHandler.handle(ex);
        }
        return null;
    }
}
