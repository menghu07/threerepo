package com.apeny.servletjsp.chapter01;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "HttpServlet", value = "/httpservlet", initParams = @WebInitParam(name = "GenericServlet", value = "/httpservlet"), loadOnStartup = 1)
public class HttpServletImpl extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -187568497831976529L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("hello Servlet impl is called");
		ServletConfig config = getServletConfig();
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
		body += "<br/>" + Arrays.toString(request.getParameterValues("names1")) + "" + request.getParameter("names1")
				+ cr;
		body += "character encoding: " + response.getCharacterEncoding() + cr;
		body += "request uri " + (request).getRequestURI() + " context path : " + context.getContextPath()
				+ " major version: " + context.getEffectiveMajorVersion() + "minor version: "
				+ context.getEffectiveMinorVersion() + cr;
		body += "real path(file path): " + context.getRealPath("/") + "; servlet path : " + request.getServletPath();
		builder.append("<html>").append("<head>").append("<h1>" + servletName + "</h1>").append("</head>")
				.append("<body>").append("<p>" + body + "</p>").append("<p>" + context + "</p>").append("</body>")
				.append("</html>");
		writer.println(builder);
	}
}
