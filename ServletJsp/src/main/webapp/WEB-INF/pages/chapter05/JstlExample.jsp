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
<p>
    <table>
        <tr><th>ISBN</th><th>Title</th></tr>
    <c:forEach var="book" begin="0" end="2" step="1">
        <a>${book+1}<c:out value="${books[book]}">NO BOOK</c:out></a>
        <tr><td>${books[book].isbn}</td><td>${books[book].title}</td></tr>
        <c:remove var="book"/>
    </c:forEach>
    <c:set var="index1" value="1"></c:set>
    <c:forEach var="book" items="${books}" varStatus="stats">
        <p>items[${index1}]:status:${stats.count}<c:out value="${book}">No Book Item</c:out></p>
        <c:set var="index1" value="${index1 + 1}"></c:set>
        <c:if test="${stats.count % 2 == 0}">
            <tr style="background:#ffeeee">    
        </c:if>
        <c:if test="${stats.count % 2 != 0}">
            <tr style="background:#00bbff">
        </c:if>
        <td>${book.isbn}</td><td>${book.title}</td></tr>
    </c:forEach>
    
    </table>
    <a href="#">
    <p>&apos;&quot;&amp;&lt;&gt; ISBN Only</p>
    <c:forEach var="book" items="${books}" varStatus="stats">
        ${book.isbn}
        <c:if test="${!stats.last}">
            ,
        </c:if>
    </c:forEach>
    </a>
</p>
</body>
</html>