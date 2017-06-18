package com.apeny.servletjsp.chapter01;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Form 表单提交
 * @author ahu
 *
 */
@WebServlet(name = "FormServlet", urlPatterns={"/form/formdo/get.do", "/form/formdo/post.do"})
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
//		.append("<form method='post' action='" + contextPath + servletPath + "'>")
		.append("<form method='post' action='post.do'>")
		.append("Name:<input name='name' value='CONTEXTPATH=" + contextPath + "||SERVLETPATH=" + servletPath + "||URI="+ req.getRequestURI() + "||URL=" + req.getRequestURL() + "'/>" + br)
		.append("Address:<textarea name='address' cols='40' rows='5'></textarea>" + br)
		.append("Country:<select name='country'>")
		.append("<option>USA</option>")
		.append("<option>Canada</option>")
		.append("</select>" + br)
		.append("Instructions:<textarea name='instructions' cols='40' rows='5'></textarea>" + br)
		.append("<textarea name='instructions' cols='40' rows='5'/></textarea>" + br)
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
		String br = "<br/>";
		res.setContentType("text/html;charset=utf-8");
		PrintWriter pw = res.getWriter();
		StringBuilder builder = new StringBuilder();
		builder.append("<html>")
		.append("<head>")
		.append("<title>posted form form list</title>")
		.append("</head>")
		.append("<body>")
		.append("<table>" + br)
		.append("<tr><td>Name</td><td>" + req.getParameter("name") + "</td></tr>" + br)
		.append("<tr><td>Address</td><td>" + req.getParameter("address") + "</td></tr>" + br)
		.append("<tr><td>Country</td><td>" + req.getParameter("country") + "</td></tr>" + br)
		.append("<tr><td>Shipping Instrutions</td><td>");
		String[] instrutions = req.getParameterValues("instrutions");
		if (instrutions != null) {
			for (String s : instrutions) {
				pw.println(s + br);
			}
		}
		builder.append("</td></tr>" + br)
		.append("<tr><td>Catalog Request</td><td>" + (req.getParameter("catalogRequest") == null ? "NO" : "YES") + "</td></tr>" + br)		
		.append("</table>")
		.append("</body>")
		.append("</html>");		
		pw.print(builder);
	}
}