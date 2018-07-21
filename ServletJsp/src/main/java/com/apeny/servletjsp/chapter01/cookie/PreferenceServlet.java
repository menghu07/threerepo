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
 * Cookie管理session
 *
 * @author ahu
 */
@WebServlet(name = "preferenceServlet", value = {"/cookiesession2/youlike/preferenceget.do", "/cookiesession3/youlike3/preferencepost.do"})
public class PreferenceServlet extends HttpServlet {

    public static final String MENU = "<div style='background:#f8f8f8;padding=15px'>"
            + "<a href='/ServletJsp/cookiesession4/youlike/cookieinfoget.do'>Cookie Info</a>" + HtmlConstant.CR
            + "<a href='/ServletJsp/cookiesession5/youlike/cookieclassget.do'>Cookie Class</a>" + HtmlConstant.CR
            + "<a href='/ServletJsp/cookiesession2/youlike/preferenceget.do'>Preference</a>" + HtmlConstant.CR
            + "</div>";

    private static final long serialVersionUID = 394L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] allCookies = req.getCookies();
        for (int i = 0; allCookies != null && i < allCookies.length; i++) {
            Cookie c = allCookies[i];
            System.out.println(req.getHeader("User-Agent") + " get method " + c.getName() + " path: "
                    + c.getPath() + "value: " + c.getName() + "=" + c.getValue());
        }
        StringBuilder builder = new StringBuilder();
        builder.append("<html>")
                .append("<head>")
                .append(HtmlConstant.BODY_CENTER)
                .append("<title>喜欢的设置</title>")
                .append("<style>table {/*水平居中*/ margin:0 auto; font-size:small; background:NavajoWhite}</style>")
                .append("</head>")
                .append("<body>")
                .append(HtmlConstant.CR + MENU)
                //提交到当前的servlet与去掉action相同
                .append("<form method='post' action='" + req.getContextPath() + "/cookiesession3/youlike3/preferencepost.do'>")
                .append("<table>")
                .append("<tr>")
                .append("<td>Title Font Size</td>")
                .append("<td>").append("<select name='titleFontSize'>").append("<option>8</option><option>16</option><option>24</option>"
                + "<option>32</option><option>36</option>").append("</select></td>")
                .append("<td>Title Style & Weight</td>")
                .append("<td>").append("<select name='titleSytleAndWeight' multiple='multiple'>").append("<option>italic</option><option>bold</option>")
                .append("</select></td>")
                .append("</tr>")
                .append("<tr><td>Max Records</td><td><select name='maxRecords'><optgroup label='low'>"
                        + "<option>3</option><option>5</option>"
                        + "</optgroup><optgroup label='high'>"
                        + "<option>8</option><option>10</option>"
                        + "</optgroup></select></td></tr>"
                        + "<tr><td colspan='2'><input name='submit' value='submit' type='submit'/></td></tr>")
                .append("</table>")
                .append("</form>")
                .append("</body>")
                .append("</html>");
        //设置响应头，允许跨域
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        PrintWriter pw = resp.getWriter();
        pw.print(builder);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String maxRecords = req.getParameter("maxRecords");
        String[] titleStyleAndWeight = req.getParameterValues("titleSytleAndWeight");
        String titleFontSize = req.getParameter("titleFontSize");
        Cookie[] allCookies = req.getCookies();
        for (int i = 0; allCookies != null && i < allCookies.length; i++) {
            Cookie c = allCookies[i];
            System.out.println("post method " + c.getName() + " path: " + c.getPath());
            if (c.getName().startsWith("_")) {
                continue;
            }
            //cookie maxAge = -1, 浏览器关闭才删除cookie
            //浏览器中直接删除 cookie maxAge = 0
            //浏览器关闭，要保存到文件中cookie maxAge > 0
            c.setMaxAge(0);
            //必须要在此处设置path，才可以删除之前添加的cookie，即path要与之前添加cookie的path相等
            c.setPath("/");
            resp.addCookie(c);
        }
        Cookie cookieMaxRecords = new Cookie("maxRecords", maxRecords);
        //保存到文件中maxAge > 0
        cookieMaxRecords.setMaxAge(600);
        //path是相对于整个网站的，而不是整个应用
        cookieMaxRecords.setPath("/");
        resp.addCookie(cookieMaxRecords);
        System.out.println("set cookie from post [maxRcords]: " + cookieMaxRecords.getPath());
        Cookie cookieTitleFontSize = new Cookie("titleFontSize", titleFontSize);
        cookieTitleFontSize.setMaxAge(600);
        cookieTitleFontSize.setPath("/");
        resp.addCookie(cookieTitleFontSize);
        if (titleStyleAndWeight != null) {
            for (String sw : titleStyleAndWeight) {
                if ("italic".equals(sw)) {
                    Cookie fontStyle = new Cookie("titleFontSytle", "italic");
                    fontStyle.setPath("/");
                    //不保存到文件中，生命周期为会话级
                    resp.addCookie(fontStyle);
                } else if ("bold".equals(sw)) {
                    Cookie fontWeight = new Cookie("titleFontWeight", "bold");
                    fontWeight.setPath("/");
                    resp.addCookie(fontWeight);
                }
            }
        }
        //显示界面
        StringBuilder builder = new StringBuilder();
        builder.append("<html>")
                .append("<head>")
                .append(HtmlConstant.BODY_CENTER)
                .append("<title>喜欢的设置</title>")
                .append("</head>")
                .append("<body>")
                .append(MENU)
                .append("Your preference has been set" + HtmlConstant.CR)
                .append("max records: " + maxRecords + HtmlConstant.CR)
                .append("font size: " + titleFontSize);
        if (titleStyleAndWeight != null) {
            builder.append("<ul>");
            for (String sw : titleStyleAndWeight) {
                builder.append("<li>" + sw + "</li>");
            }
        }
        builder.append("</ul></body>")
                .append("</html>");
        PrintWriter pw = resp.getWriter();
        pw.println(builder);
    }
}
