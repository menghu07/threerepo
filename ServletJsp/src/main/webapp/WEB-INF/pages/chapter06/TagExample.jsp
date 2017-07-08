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
	<tagm:Encode tagsf="<br/>&">
		<jsp:attribute name="input"><br/><p>changing line;${pageContext}</p></jsp:attribute>
		<jsp:body>
		in long format: ${longDate}<br/>
		in small format: ${shortDate}
		</jsp:body>
	</tagm:Encode>
	
	<%@ include file="../htmls/IncludedHtml.html" %>
</html>