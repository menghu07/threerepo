package com.apeny.servletjsp.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 设置编码方式
 * @author ahu
 *
 */
public class CharacterEncodingFilter implements Filter {

	/**
	 * 是否或略请求中字符集设置
	 */
	private boolean ignore;
	
	/**
	 * 字符集编码
	 */
	private String encoding;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		FilterConfig config = filterConfig;
		String ignore = config.getInitParameter("ignore");
		if (ignore != null) {
			this.ignore = Boolean.parseBoolean(ignore) || "yes".equalsIgnoreCase(ignore);
		}
		encoding = config.getInitParameter("encoding");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (ignore || request.getCharacterEncoding() == null) {
			request.setCharacterEncoding(encoding);
		}
		setResponseContentType(response);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		ignore = false;
		encoding = null;
	}
	
	/**
	 * 设置响应中的文本格式和字符集
	 * @param response
	 */
	private void setResponseContentType(ServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
	}
}
