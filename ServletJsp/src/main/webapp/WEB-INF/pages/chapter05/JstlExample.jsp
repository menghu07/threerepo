<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JavaServer Pages Tablib</title>
</head>
<body>
<p>
输出c:out
<c:out value="${requestScope['employe']}" escapeXml="true" default="${requestScope['employee'].name}"/>
<c:set var="emp2" value="${requestScope['employee']}" />
<c:set var="emp3" value="${requestScope['employee']}" />
<c:set var="con" value="New Line"></c:set>
<c:set target="${emp2}" property="name" value="${emp2.id}"/>
<br/>emp2:<c:out value="${emp2}" /> 
<br/>NewLine:<c:out value="${con}" />
<br/>updated emp2:<c:out value="${emp2}${employee}||${emp3}"/>
<c:remove var="emp3" scope="page"/>
<br/>after remove emp3:<c:out value="${emp3}">No Emp3</c:out>
<c:if test="${param.pass=='nopass'}" var="isName">
	Your userName is 胡子
</c:if>
<br/>we want to see if userName == '胡子':<c:out value="${isName}||${param['userName']}">not 胡子</c:out>
${isName ? "You Login Success." : "You Login Fail."}
</p>
<p>
	<c:choose>
		<c:when test="${param.userName=='胡子'}">
			<a href="#">胡子Choose</a>
		</c:when>
		<c:when test="${param.userName=='终止'}">
			<a href="#">终止Choose</a>
		</c:when>
		<c:otherwise>
			<a href="#">Default Choose</a>
		</c:otherwise>
	</c:choose>
</p>
</body>
</html>