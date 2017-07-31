<%@ page import="com.apeny.servletjsp.domain.Book" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>All Listeners</title>
</head>
<body>
<p>applicationScope=${applicationScope.size()}<br/>java
    大神${applicationScope['org.springframework.web.context.support.ServletContextScope']}</p>
applicationScope
true ${applicationScope==applicationScope}:${applicationScope==applicationScope['org.springframework.web.context.support.ServletContextScope']}
<ul>
    <c:forEach items="${countries}" var="i">
        <li>${i.key}-${i.value}</li>
    </c:forEach>
<%
    session.setAttribute("book3", new Book());
    session.removeAttribute("book3");
%>
</ul>
</body>
</html>