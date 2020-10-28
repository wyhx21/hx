package org.layz.hx.spring.mvc.filter;

import org.layz.hx.core.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RequestContextFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestContextFilter.class);
    private static final String authToken = "authToken";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String value = filterConfig.getInitParameter("sessionTimeOut");
        try {
            Long sessionTimeOut = Long.parseLong(value);
            LOGGER.info("sessionTimeOut is: {}", sessionTimeOut);
            RequestUtil.getInstance().setTimeOut(sessionTimeOut);
        } catch (Exception e) {
            LOGGER.info("sessionTimeOut error, value is: {}", value);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(request instanceof HttpServletRequest) {
            String token = ((HttpServletRequest) request).getHeader(authToken);
            RequestUtil.getInstance().setContext(token);
            try {
                chain.doFilter(request, response);
            } finally {
                RequestUtil.getInstance().remove();
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}
