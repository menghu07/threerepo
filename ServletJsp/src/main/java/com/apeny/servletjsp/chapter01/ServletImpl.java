package com.apeny.servletjsp.chapter01;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = {"/servletimpl"}, name = "Servlet Impl", 
initParams = {@WebInitParam(name = "name", value = "value1"), @WebInitParam(name = "email", value = "huzi@qq.com")},
description = "the servlet implements Servlet Interface", displayName = "Servlet Imlementation")
public class ServletImpl implements Servlet {
	
	private transient ServletConfig config;
	
	@Override
	public void destroy() {
		//do nothing
	}
	
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		this.config = servletConfig;
	}
	
	@Override
	public ServletConfig getServletConfig() {
		return config;
	}
	
	@Override
	public String getServletInfo() {
		return "ServletImpl";
	}
	
	@Override
	public void service(ServletRequest request, ServletResponse response) throws IOException, ServletException {
		System.out.println("hello Servlet impl is called");
		String servletName = config.getServletName();
		ServletContext context = request.getServletContext();
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		StringBuilder builder = new StringBuilder();
		String body = "";
		String cr = "<br/>";
		body += "看看中文支持displayName: " + config.getInitParameter("displayName") + cr;
		Enumeration<String> initNames = config.getInitParameterNames();
		while (initNames.hasMoreElements()) {
			String name = initNames.nextElement();
			body += "name: " + name + " = value:" + config.getInitParameter(name) + cr;
		}
		body += context.getInitParameter("name");
		body += "<br/>" + Arrays.toString(request.getParameterValues("names1")) + "" 
		+ request.getParameter("names1") + cr;
		body += "character encoding: " + response.getCharacterEncoding() + cr;
		
//		body += "context path : " + context.getContextPath() + "major version: " + context.getEffectiveMajorVersion() + "minor version: " + context.getEffectiveMinorVersion();
		
		
		builder.append("<html>")
		.append("<head>")
		.append("<h1>" + servletName + "</h1>")
		.append("</head>")
		.append("<body>")
		.append("<p>" + body + "</p>")
		.append("<p>" + context + "</p>")
		.append("</body>")
		.append("</html>");
		writer.println(builder);
	}
}
