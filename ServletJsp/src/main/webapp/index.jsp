<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  %>
<html>
<body>
<h2>Hello World!</h2>
<%	String pageContextApp = PageContext.APPLICATION;
	request.getContextPath();
%>
<p><%=pageContextApp%> servletPath=<%=request.getServletPath()%> <br> contextPath=<%=request.getContextPath() %><br>URI=<%=request.getRequestURI()%><br>URL=<%=request.getRequestURL()%></p>
<p>from ${sessionScope.referer}</p>
<p>绝对路径</p>
<a href="/ServletJsp/AjaxgetServlet/ajaxget.do">ajaxget.do</a>
<a href="/ServletJsp/cookiesession2/youlike/preferenceget.do">preferenceget</a>
<a href="/ServletJsp">indexNo jsp</a>
<a href="/ServletJsp/index.jsp">index with jsp</a>
</body>
</html>
