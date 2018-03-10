<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="dobody" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Main Page From TagExample</title>
</head>
<body>
<%--隐藏注释，source中看不到--%>
<!--html 注释-->
<!--动态注释today is <%=new Date()%>-->
i can see ${header.referer}
<dobody:DoBody>
${header.referer}
</dobody:DoBody>
i want to see session id <%=session.getId()%>ppp${cookie.JSESSIONID}
<br/>NO sessionid:${sessionScope.sessionid}<%=session.getAttribute("sessionid")%>
<a href="/ServletJsp">go to home page</a>
</body>
</html>