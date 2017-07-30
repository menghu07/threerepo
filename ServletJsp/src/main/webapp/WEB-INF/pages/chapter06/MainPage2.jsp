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
i can see ${header.referer}
<dobody:DoBody>${header.referer}</dobody:DoBody>
<br/>
i want to see session id sesion.getId() <%=session.getId()%><br/>
另一种写法:${pageContext.session.id} &nbsp; createtime${pageContext.session.creationTime}<br/>
JSESSIONID Session对应的cookie${cookie.JSESSIONID}<br/>属性写法${cookie['JSESSIONID']} id获取方法name=${cookie.JSESSIONID.name};value=${cookie.JSESSIONID.value}<br/>
NO sessionid错误写法${sessionScope.id}${sessionScope.sessionid}<%=session.getAttribute("sessionid")%>
<a href="/ServletJsp">go to home page</a>
</body>
</html>