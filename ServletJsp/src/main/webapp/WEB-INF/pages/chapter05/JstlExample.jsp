<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="../errors/error404.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JavaServer Pages Tablib</title>
</head>
<body>
	<p>
		输出c:out
		<c:out value="${requestScope['employe']}" escapeXml="true"
			default="${requestScope['employee'].name}" />
		<c:set var="emp2" value="${requestScope['employee']}" />
		<c:set var="emp3" value="${requestScope['employee']}" />
		<c:set var="con" value="New Line"></c:set>
		<c:set target="${emp2}" property="name" value="${emp2.id}" />
		<br />emp2:
		<c:out value="${emp2}" />
		<br />NewLine:
		<c:out value="${con}" />
		<br />updated emp2:
		<c:out value="${emp2}${employee}||${emp3}" />
		<c:remove var="emp3" scope="page" />
		<br />after remove emp3:
		<c:out value="${emp3}">No Emp3</c:out>
		<c:if test="${param.pass=='nopass'}" var="isName">
	Your userName is 胡子
</c:if>
		<br />we want to see if userName == '胡子':
		<c:out value="${isName}||${param['userName']}">not 胡子</c:out>
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
		<tr>
			<th>ISBN</th>
			<th>Title</th>
		</tr>
		<c:forEach var="book" begin="0" end="2" step="1">
			<a>${book+1}<c:out value="${books[book]}">NO BOOK</c:out></a>
			<tr>
				<td>${books[book].isbn}</td>
				<td>${books[book].title}</td>
			</tr>
			<c:remove var="book" />
		</c:forEach>
		<c:set var="index1" value="1"></c:set>
		<c:forEach var="book" items="${books}" varStatus="stats">
			<p>
				items[${index1}]:status:${stats.count}
				<c:out value="${book}">No Book Item</c:out>
			</p>
			<c:set var="index1" value="${index1 + 1}"></c:set>
			<c:if test="${stats.count % 2 == 0}">
				<tr style="background: #ffeeee">
			</c:if>
			<c:if test="${stats.count % 2 != 0}">
				<tr style="background: #00bbff">
			</c:if>
			<td>${book.isbn}</td>
			<td>${book.title}</td>
			</tr>
		</c:forEach>
	</table>
	<a href="#">
		<p>&apos;&quot;&amp;&lt;&gt; ISBN Only</p> <c:forEach var="book"
			items="${books}" varStatus="stats">
        ${book.isbn}
        <c:if test="${!stats.last}">
            ,
        </c:if>
		</c:forEach>
	</a>
	<table>
		<tr style="background: #0099ee">
			<th>Country</th>
			<th>Capital</th>
		</tr>
		<c:forEach var="capital" items="${capitals}" varStatus="stats">
			<c:if test="${stats.count % 2 == 0}">
				<tr style="background: #ffeeee">
			</c:if>
			<c:if test="${stats.count % 2 != 0}">
				<tr style="background: #00bbff">
			</c:if>
			<td>${capital.key }</td>
			<td>${capital.value}</td>
			</tr>
		</c:forEach>
	</table>
	<table>
		<tr style="background: #0099ee">
			<th>Country</th>
			<th>Big Cities</th>
		</tr>
		<c:forEach var="bigCites2" items="${bigCities}" varStatus="stats">
			<c:if test="${stats.count % 2 == 0}">
				<tr style="background: #ffeeee">
			</c:if>
			<c:if test="${stats.count % 2 != 0}">
				<tr style="background: #00bbff">
			</c:if>
			<td>${bigCites2.key }</td>
			<td><c:forEach var="city" items="${bigCites2.value}"
					varStatus="stat">
                ${city}
                <c:if test="${!stat.last}">,</c:if>
				</c:forEach></td>
			</tr>
		</c:forEach>
	</table>
	<a href="#">Defined Tokens: <c:forTokens items="I,O,U,Z,K,H"
			delims="," var="name" varStatus="stat">${name}<c:if
				test="${!stat.last}">,</c:if>
		</c:forTokens>
	</a>
	<fmt:formatNumber var="num1" type="number" value="1298989901.88602"
		pattern="#,##00.####" maxIntegerDigits="7" minFractionDigits="4" />
	<br />Number1模式#不包括0>>${num1}
	<br />
	<fmt:formatNumber var="num2" type="currency" value="192345.89790"
		currencyCode="CNY" />
	<br />Currency2>>${num2}
	<br />
	<fmt:formatNumber var="num3" type="percent" value="192345.89790"
		minFractionDigits="2" />
	<br />Percentage3>>${num3}
	<br />
	<c:set var="ii" value="ff\u2030"></c:set>
	<c:out value="${ii }"></c:out>
	<fmt:formatNumber var="num4" value="192345.89790" pattern="0.##" />
	<br />Percentage4 1000>>${num4}
	<br />
	<fmt:formatDate value="${now}" timeStyle="default" dateStyle="default"
		var="fomdate1" type="both" timeZone="EST"/>
	<br />FormatDate1 fomdate1>>${fomdate1}
	<fmt:formatDate value="${now}" timeStyle="default" dateStyle="default"
		var="fomdate2" type="both" />
	<br />FormatDate2 fomdate2>>${fomdate2}
	<br />
	<fmt:parseNumber var="parseNumb1" value="${num2}" type="currency"
		parseLocale="zh_CN"></fmt:parseNumber>
	<br />ParseNumber parseNumb1>>${parseNumb1}
	<br />
	<fmt:setTimeZone value="EST+9:00" var="timeZone1" />
	<fmt:formatDate var="date1" value="${now}" type="both"
		dateStyle="full" timeStyle="full" timeZone="${timeZone1}" />
	<br />format date1>>${date1}
	<fmt:timeZone value="GMT">
	<fmt:formatDate var="date2" value="${now}" type="both"
		dateStyle="full" timeStyle="full"/>
	</fmt:timeZone>
	<br />format Date format2>>${date2}
	<fmt:parseDate value="${fomdate1}" var="parseDate3" type="both" dateStyle="default" timeStyle="default" timeZone="EST"/>
	<br />parseDate3 parseDate3 from formdate1>>${parseDate3}
	</p>
	<p>
	--------------------Function----------------------------
	</p>
	<p>
	<c:set var="str1" value="   Hello '<World ,ff"/>
	<c:set var="str2" value="far"></c:set>
	<br/>contains:${fn:contains(str1, "lo")}
	<br/>str1 是变量fn:containsIgnoreCase:${fn:containsIgnoreCase(str1, "lO")}
	<br/><p>escapeXml:${str2.concat(fn:escapeXml(str1).concat("fff"))}END</p>
	<br/>endsWith:${fn:endsWith(str1, "ld")}
	<br/>replace e to E:${fn:escapeXml(fn:replace(str1, "e", "E"))}
	<c:set var="spt" value="${fn:split(fn:escapeXml(str1), ',')}" />
	<br/>
	<c:forEach var="it" items="${spt}">
		split item:${it}<br/>
	</c:forEach>
	<br/>subStringAfter is:${fn:escapeXml(fn:substringAfter(str1, "or"))}
	
	</p>
</body>
</html>