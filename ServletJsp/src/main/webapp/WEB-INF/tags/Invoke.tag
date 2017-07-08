<%@ tag pageEncoding="utf-8" %>
<%@ attribute name="productDetails" fragment="true" required="true" %>
<%@ variable name-given="productName" %>
<%@ variable name-given="description" %>
<%@ variable name-given="price" %>
<%
	jspContext.setAttribute("productName", "Pelesonic DVD Player");
	jspContext.setAttribute("description", "Dolby digital");
	jspContext.setAttribute("price", "9090");
%>
<jsp:invoke fragment="productDetails"></jsp:invoke>
