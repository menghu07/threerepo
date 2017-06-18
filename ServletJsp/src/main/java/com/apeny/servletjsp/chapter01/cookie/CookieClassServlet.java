package com.apeny.servletjsp.chapter01.cookie;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.apeny.servletjsp.constant.HtmlConstant;

/**
 * cookie class's methods
 * @author ahu
 *
 */
@WebServlet(name = "cookieclassservlet", value = {"/cookiesession5/youlike/cookieclassget.do"})
public class CookieClassServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5324927193296918071L;
	
	private static final String[] METHODS = {"clone", "getComment", "getDomain", "getMaxAge", "getName", "getPath",
			"getSecure", "getVersion", "getValue", "isHttpOnly"};

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//maxRecords 默认值5
		int maxRecords = 5;
		Cookie[] cookies = req.getCookies();
		String maxRecordsCookie = null;
		if (cookies != null) {
			for (Cookie c : cookies) {
				//取cookie时或者说request发送cookie时，会发送路径（path）最匹配的cookie
				if (c.getName().equals("maxRecords")) {
					maxRecordsCookie = c.getValue();
					break;
				}
			}
		}
		try {
			if (maxRecordsCookie != null) {				
				maxRecords = Integer.parseInt(maxRecordsCookie);
			}
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			//do nothing
		}
		StringBuilder builder = new StringBuilder();
		builder.append("<html>")
		.append("<head>")
		.append(HtmlConstant.BODY_CENTER)
		.append("<title>喜欢的设置</title>")
		.append("</head>")
		.append("<body>")
		.append(HtmlConstant.CR + PreferenceServlet.MENU)
		.append("javax.servlet.http.Cookie&#39;s methods:" + HtmlConstant.CR)
		.append("<ul>");
		for (int i = 0; i < maxRecords; i++) {
			builder.append("<li>" + METHODS[i] + "</li>");
		}
		builder.append("<ul>")
		.append("</body>")
		.append("</html>");
		PrintWriter pw = resp.getWriter();
		pw.println(builder);
	}
}
