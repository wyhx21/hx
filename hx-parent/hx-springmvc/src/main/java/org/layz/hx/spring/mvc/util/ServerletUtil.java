package org.layz.hx.spring.mvc.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

public class ServerletUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerletUtil.class);
	private static final ServerletUtil instance = new ServerletUtil();

	private ServerletUtil(){}

	public static ServerletUtil getInstance(){
		return instance;
	}
	/**
	 * @return
	 */
	public HttpServletRequest getRequest(){
		return getServletRequestAttributes().getRequest();
	}
	/**
	 * @return
	 */
	public HttpServletResponse getResponse(){
		return getServletRequestAttributes().getResponse();
	}

	/**
	 * @return
	 */
	public HttpSession getSession(){
		return getRequest().getSession();
	}

	/**
	 * 获取获取客户端IP
	 * @return
	 */
	public String getRequestIp() {
		HttpServletRequest request = getServletRequestAttributes().getRequest();
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
	/**
	 * 文件下载
	 * @param path
	 * @throws IOException
	 */
	public void downLoadFile(String path) throws  IOException {
		downLoadFile(path, null);
	}
	/**
	 * 文件下载
	 * @param path
	 * @param fileName
	 * @throws IOException
	 */
	public void downLoadFile(String path, String fileName) throws IOException {
		LOGGER.debug("path: {}, fileName: {}", path, fileName);
		File report = new File(path);
		if (StringUtils.isEmpty(fileName)) {
			fileName = report.getName();
		}
		setFileName(fileName);
		try(ServletOutputStream output = getResponse().getOutputStream()){
			try(FileInputStream fis = new FileInputStream(report)) {
				byte[] buffer = new byte[1024];
				int length;
				while ((length = fis.read(buffer)) != -1) {
					output.write(buffer, 0, length);
					output.flush();
				}
			}
		}
	}
	/**
	 * 文件名设置
	 * @param fileName
	 * @throws IOException
	 */
	public void setFileName(String fileName) throws IOException {
		LOGGER.debug("fileName: {}", fileName);
		HttpServletResponse response = getResponse();
		// 设置response编码
		response.setCharacterEncoding("UTF-8");
		if (fileName.endsWith("zip")) {
			response.setContentType("application/zip");
		} else {
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		}
		String userAgent = getRequest().getHeader("user-agent");
		if (userAgent != null && (userAgent.indexOf("Firefox") >= 0
				|| userAgent.indexOf("Chrome") >= 0
				|| userAgent.indexOf("Safari") >= 0
				|| userAgent.indexOf("Opera") >= 0)) {
			fileName = new String((fileName).getBytes(), "ISO8859-1"); // 其他浏览器
		} else {
			fileName = URLEncoder.encode(fileName, "UTF8"); // IE
		}
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
	}

	private ServletRequestAttributes getServletRequestAttributes(){
		return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	}
}
