<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="../errors/error404.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Expression Language</title>
</head>
<body>
<p>A + b = 看看结果${2 + 5}</p>
<p>Header: ${header.host }</p>
<p>ServletPath: ${pageContext["request"].servletPath}</p>
<p>Expression Rule: ${pageContext[null] } + ${null["request"] }</p>
<p>initParam:${initParam.contextConfigLocation }</p>
<p>param: ${param.userName } paramValues: ${paramValues.userName }</p>
<p>Cookie sessionid: requestSessionId ${pageContext.request.requestedSessionId } + cookie sessionId : ${cookie["JSESSIONID"].value}</p>
<p>requestScope userName: ${requestScope.userName} + servletPath : ${requestScope.servletPath }</p>
<p>Arithmetic Calculation: ${3 + 3} ${9 / 8 }</p>
<p>Relation Calculation: ${3 == 9} </p>
<p>Logic Calculation: ${header.host == null || header.method == null}</p>
<p>Cookie Value: ${cookie.JSESSIONID.value }</p>
<p>Condition Operator: ${ cookie.jsessionid == null ? "NULL" : cookie.jseesionid } + empty operator <b>${empty requestScope.noProperty }</b></p>
<p>Accept-Language: ${header['accept-language'] }</p>
<p>Session id: ${pageContext.session.id }</p>
<p>Employee: ${requestScope.employee.name} , city: ${requestScope.employee.address.city }</p>
<p>Canada's Capital: ${capitals['Canada'] }</p>
</body>
</html>