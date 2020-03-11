package org.layz.hx.spring.mvc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DownLoadUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(DownLoadUtil.class);
	/**
	 * 文件下载
	 * @param request
	 * @param response
	 * @param path
	 * @throws IOException
	 */
	public static void downLoadFile(HttpServletRequest request,
			HttpServletResponse response, String path) throws  IOException {
		downLoadFile(request, response, path, null);
	}
	/**
	 * 文件下载
	 * @param request
	 * @param response
	 * @param path
	 * @param fileName
	 * @throws IOException
	 */
	public static void downLoadFile(HttpServletRequest request,
			HttpServletResponse response, String path, String fileName) throws IOException {
		LOGGER.debug("path: {}, fileName: {}", path, fileName);
		File report = new File(path);
		if (StringUtils.isEmpty(fileName)) {
			fileName = report.getName();
		}
		setFileName(request, response, fileName);
		try(ServletOutputStream output = response.getOutputStream()){
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
	 * @param request
	 * @param response
	 * @param fileName
	 * @throws IOException
	 */
	public static void setFileName(HttpServletRequest request,
			HttpServletResponse response, String fileName) throws IOException {
		LOGGER.debug("fileName: {}", fileName);
		// 设置response编码
		response.setCharacterEncoding("UTF-8");
		if (fileName.endsWith("zip")) {
			response.setContentType("application/zip");
		} else {
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		}
		String userAgent = request.getHeader("user-agent");
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
}
