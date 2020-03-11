package org.layz.hx.spring.mvc.util;

import org.layz.hx.core.enums.SessionEnum;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {
	/**
	 * 获取用户访问的url
	 * @return
	 */
	public static String getURI(){
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return req.getRequestURI();
	}
	
	/**
	 * 设置session
	 * @return
	 */
	public static void setSessionAttribute(SessionEnum sessionEnum, Object obj) {
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		req.getSession().setAttribute(sessionEnum.getValue(), obj);
	}
	/**
	 * 从session中获取数据
	 * @return
	 */
	public static Object getSessionAttribute(SessionEnum sessionEnum) {
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return req.getSession().getAttribute(sessionEnum.getValue());
	}
	/**
	 * 退出登录
	 */
	public static void removeSessionAttribute(SessionEnum sessionEnum){
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		req.getSession().removeAttribute(sessionEnum.getValue());
	}
	/**
	 * 获取获取客户端IP
	 * @return
	 */
	public static String getRequestIp() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}  
		return ip;
	}
	
}
