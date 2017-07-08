<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/Tags.tld" prefix="smp3"%>
<%@ taglib uri="/WEB-INF/Functions.tld" prefix="fun3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tagm" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Tag Example</title>
</head>
<body>
	SimpleTagImpl implements
	<smp3:simpTag/>
	<c:out value="${fvar}"><p>fff</p></c:out>
	<smp3:dataFormatterTag items="CHINA:BEIJING,USA:WASHINGTON,CANADA:WATTAWA" head2="City" head1="Country">
		<option value="${value}">${name}</option>
	</smp3:dataFormatterTag>
	<p>Reverse String: ${fun3:rev("can who i")}</p>
	Today is my dateformat:<tagm:DateFormat/>
	<p>jsp 动作指令</p>
	<jsp:element name="Element">
	   <jsp:attribute name="type">属性</jsp:attribute>
	   <jsp:body>Element Body</jsp:body>
	</jsp:element>
	<p>jsp${pageContext}; request:${pageContext.request} </p>
	<tagm:Encode tagsf="shortDate4">
		<jsp:attribute name="input"><br/><p>changing line;${pageContext}</p></jsp:attribute>
		<jsp:body>
		in long format: ${longDate}<br/>
		in small format: ${shortDate4}
		</jsp:body>
	</tagm:Encode>
	<a href="./MainPage2.ch06do">go to MainPage</a>
	<p>Invoke Example call here</p>
	<tagm:Invoke>
		<jsp:attribute name="productDetails">
			<table border="1">
				<tr><td>Name</td><td>${productName}</td></tr>
				<tr><td>Discription</td><td>${description}</td></tr>
				<tr><td>Price</td><td>${price}</td></tr>
			</table>
		</jsp:attribute>
	</tagm:Invoke>
	<%@ include file="../htmls/IncludedHtml.html" %>
</html>