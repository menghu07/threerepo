<%@page language="java" contentType="text/html;charset=UTF8" pageEncoding="utf-8"  %>
<html>
<body>
<h2>Hello World!</h2>
<%	String pageContextApp = PageContext.APPLICATION;
	request.getContextPath();
%>
<p><%=pageContextApp%>  <%=request.getContextPath() %></p>
<p>from ${sessionScope.referer}</p>
</body>
</html>
