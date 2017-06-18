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

@WebServlet(name = "cookieinfoservlet", value = "/cookiesession4/youlike/cookieinfoget.do")
public class CookieInfoServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1203481654167102092L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Cookie[] cookies = req.getCookies();
		StringBuilder builder = new StringBuilder();
		builder.append("<html><head>");
		builder.append(HtmlConstant.BODY_CENTER);
		builder.append("<title>喜欢的设置</title>");
		builder.append("<style>.title{");
		if (cookies != null) {
			for (Cookie c : cookies) {
				String cookieName = c.getName();
				if ("titleFontSize".equals(cookieName)) {
					builder.append("font-size: " + c.getValue() + ";");
				} else if ("titleFontSytle".equals(cookieName)) {
					builder.append("font-style: " + c.getValue() + ";");
				} else if ("titleFontWeight".equals(cookieName)) {
					builder.append("font-weight: " + c.getValue() + ";");
				}
			}
		}
		builder.append("}")
		.append("</style></head>")
		.append("<body>")
		.append(HtmlConstant.CR + PreferenceServlet.MENU)
		.append("<div class='title'>")
		.append("================================" + HtmlConstant.CR)
		.append("Session Management With Cookie" + HtmlConstant.CR)
		.append("================================")
		.append("</div>");
		if (cookies != null) {
			builder.append("Cookie in this http response" + HtmlConstant.CR);
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().startsWith("_")) {
					continue;
				}
				builder.append(cookies[i].getName() + " : " + cookies[i].getValue() + HtmlConstant.CR);
			}
		} else {
			builder.append("There is no cookie in this http response" + HtmlConstant.CR);
		}
		builder.append("</body>")
		.append("</html>");
		PrintWriter pw = resp.getWriter();
		pw.println(builder);
	}
}
