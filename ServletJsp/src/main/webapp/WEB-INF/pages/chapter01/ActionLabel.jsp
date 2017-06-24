<%@ page language="java" contentType="text/html; charset=Utf-8"
    pageEncoding="Utf-8" import="com.apeny.servletjsp.domain.Customer"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=Utf-8">
<title>Action Element</title>
</head>
<body>
<jsp:useBean id="customer" class="com.apeny.servletjsp.domain.Customer">
</jsp:useBean>
<jsp:setProperty property="id" name="customer" value="121"/>
<jsp:setProperty property="name" name="customer" value="huzi"/>
<jsp:setProperty property="city" name="customer" value="new york"/>
<p>customer: <%=customer%></p>
<p>customer Name:<jsp:getProperty property="name" name="customer"/>
<br/>Page Context:     pageContext.getRequest() == request <%=pageContext.getRequest() == request%>
</p>
<jsp:include page="../jspfs/IncludeAction.jsp">
	<jsp:param value="<%=request.getServletPath()%>" name="reference"/>
</jsp:include>
<jsp:forward page="../jspfs/ForwardAction.jsp">
	<jsp:param value="<%=request.getRequestURI() %>" name="reference"/>
</jsp:forward>
<%
	
%>
</body>
</html>