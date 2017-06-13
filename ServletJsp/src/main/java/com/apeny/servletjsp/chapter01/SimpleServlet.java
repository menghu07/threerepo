package com.apeny.servletjsp.chapter01;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SimpleServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4685571643065148776L;
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		System.out.println("post here");
		String br = "<br/>";
		res.setContentType("text/html;charset=utf-8");
		PrintWriter pw = res.getWriter();
		StringBuilder builder = new StringBuilder();
		builder.append("<html>")
		.append("<head>")
		.append("<title>posted form form list</title>")
		.append("</head>")
		.append("<body>")
		.append("<p>" + req.getAuthType() + "" + br)
		.append("</body>")
		.append("</html>");		
		pw.print(builder);
	}
}
