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
		PrintWriter pw = res.getWriter();
		StringBuilder builder = new StringBuilder();
		builder.append("<html>")
		.append("<head>")
		.append("<title>posted form form list</title>")
		.append("</head>");
		builder.append("<body>")
		.append("<p>" + req.getAuthType() + "中文是否错误？" + getServletInfo() + "</p>" + br)
		.append("</body>")
		.append("</html>");		
		pw.print(builder);
		//response提交的方法
		//1 response.getWrite() response.getOutputStream() buffer缓冲区满啦8k
		//2 response.flushBuffer()
		//3 response.sendError()
		//4 reponse.sendRedirect()
		//提交response
		res.flushBuffer();
		//临时重定向指定URL
//		res.sendRedirect("/ServletJsp");
		//getWriter()调用后character encoding重新设置会失效，response提交后contentType()的调用会失效
		res.setContentType("text/json;charset=iso-8859-1");
	}
}