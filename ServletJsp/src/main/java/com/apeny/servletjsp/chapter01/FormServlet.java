package com.apeny.servletjsp.chapter01;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "FormServlet", urlPatterns={"/form/formdo/htmldo"})
public class FormServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1412443588881940526L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html;charset=utf-8");
		String br = "<br/>";
		PrintWriter pw = res.getWriter();
		String contextPath = req.getContextPath();
		String servletPath = req.getServletPath();
		StringBuilder builder = new StringBuilder();
		builder.append("<html>")
		.append("<head>")
		.append("<title>form form list</title>")
		.append("</head>")
		.append("<body>")
		.append("<form method='post' action='" + contextPath + servletPath + "'>")
		.append("Name:<input name='name' value='" + contextPath + "||" + servletPath + "'/>" + br)
		.append("Address:<textarea name='address' cols='40' rows='5'></textarea>" + br)
		.append("Country:<select name='country'>")
		.append("<option>USA</option>")
		.append("<option>Canada</option>")
		.append("</select>" + br)
		.append("Instructions:<textarea name='instruction' cols='40' rows='5'></textarea>" + br)
		.append("<textarea name='instruction' cols='40' rows='5'/></textarea>" + br)
		.append("CatalogRequest:<input type='checkbox' name='catalogRequest'/>" + br)
		.append("<input type='submit' name='submit' value='submit'/>")
		.append("<input type='reset' name='reset' value='reset'/>")
		.append("</form>")
		.append("</body>")
		.append("</html>");
		pw.println(builder);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("post here");
	}
}
