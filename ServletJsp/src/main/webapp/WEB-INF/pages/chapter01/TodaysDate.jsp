<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" buffer="16kb" session="false" autoFlush="false"
	errorPage="/WEB-INF/pages/errors/error404.jsp"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.DateFormat, java.util.Enumeration"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Today's Date</title>
</head>
<body>
	<header><%@ include file="../jspfs/Header.jspf"%></header>
	<%--Jsp 注释，不会出现在页面中 --%>
	<!--Html 注释，不会出现在页面中 -->
	<p>这些注释出现在Html中</p>
	<%
		//	response.setContentType("text/json");
		String cr = "<br/>";
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
		String dateStr = dateFormat.format(new Date());
		out.write("Today's : " + dateStr);
		String userName = request.getParameter("userName");
		out.println("username=" + userName + "; pageContext=" + pageContext + "; application" + application);
		HttpJspPage localPage = (HttpJspPage) page;
		out.println(cr + "当前page: " + page + " ; ServletInfo: " + localPage.getServletInfo());
		out.println("<br/>自动引入 import package: java.lang/javax.servlet/javax.servlet.http/javax.servlet.jsp");
		Object o = new Object();
		pageContext.setAttribute("o1", o);
		out.println(cr + "输出page作用域的对象o1但使用的是request.getAttribute()：" + request.getAttribute("o1"));
		pageContext.setAttribute("o2", o, PageContext.REQUEST_SCOPE);
		out.println(cr + "输出request作用域的对象o2：" + request.getAttribute("o2"));
	%>
	<p>请求方法<%=request.getMethod()%></p>
	<p>URL:<%=request.getRequestURL() + "&" + request.getQueryString() %></p>
	<p>Protocol: <%=request.getProtocol()%></p>
	<%
		out.println("请求方法");
		for (Enumeration<String> e = request.getHeaderNames(); e.hasMoreElements();) {
			String header = e.nextElement();
			out.println(cr + "header name: " + header + ":" + request.getHeader(header));
		}
		out.println(cr + "Buffer size: " + response.getBufferSize());
		//必须要创建session才不会报错
		out.println(cr + "Session Id: " + request.getSession(true).getId());
		out.println(
				cr + "Servlet name: " + config.getServletName() + " servlet info: " + localPage.getServletInfo());
		out.println(cr + "Server info: " + application.getServerInfo());
	%>
	<%!public String getTodayFormat() {
		java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return fmt.format(new Date());
	}

	public void jspInit() {
		System.out.println("init : " + this);
	}

	public void jspDestroy() {
		System.out.println("destroy : " + this);
	}%>
	<p>
		Format Tody :
		<%=getTodayFormat()%>
		当前Page:<%=page%>
	</p>

	<footer><%@ include file="../jspfs/Copyright.jspf"%></footer>
</body>
</html>