<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  %>
<html>
<body>
<h2>Hello World!</h2>
<%	String pageContextApp = PageContext.APPLICATION;
	request.getContextPath();
	String id = "from jsp";
	String contextPath = request.getContextPath();
%>
<p>pageContext=${pageContext.servletContext}</p>
${pageContext.request.setAttribute("fg", "new")}
<p>request has bean set attribute value = ${requestScope.fg}</p>
<p>JSP Scriptlet:<%=pageContextApp%> servletPath=<%=request.getServletPath()%> <br> contextPath=<%=request.getContextPath() %><br>URI=<%=request.getRequestURI()%><br>URL=<%=request.getRequestURL()%></p>
<p>EL id=${id} servletPath=${pageContext.request.servletPath} requestScope servletPath = servletPath=${requestScope.clear()} clear之后requestScope=${requestScope.entrySet()}</p>
<p>JSP Scriptlet variable 任意范围的属性pageContextApp=${pageContextApp} pageScope requestScope sessionScope applicationScope 作用范围逐渐增大，其方法的是属性</p>
<p>from ${sessionScope.referer}</p>
<p>绝对路径</p>
<a href="/ServletJsp/AjaxgetServlet/ajaxget.do">ajaxget.do</a>
<a href="/ServletJsp/cookiesession2/youlike/preferenceget.do">preferenceget</a>
<a href="/ServletJsp">indexNo jsp</a>
<a href="/ServletJsp/index.jsp">index with jsp</a>
<%pageContext.getOut().write(id);%>
<script src="<%=contextPath%>/js/jquery/jquery-3.2.1.js"></script>
</body>
</html>
