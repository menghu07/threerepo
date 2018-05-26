<%--
  Created by IntelliJ IDEA.
  User: apeny
  Date: 2018/5/23
  Time: 7:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>ProductForm</title>
</head>
<body>
<c:if test="${requestScope.errors != null}">
    <p id="errors">
    <ul>
        <c:forEach var="err" items="${requestScope.errors}">
            <li>${err}</li>
        </c:forEach>
    </ul>
    </p>
</c:if>
<form action="${pageContext.servletContext.contextPath}/${requestScope.action}" method="post">
    Product Name: <input name="name" value="${form.name}"/><br/>
    Product Description: <input name="description" value="${form.description}"/><br/>
    Product Price: <input name="price" value="${form.price}"/><br/>
    <input type="submit" value="提交"/>
    <input type="reset" value="重置"/>
</form>
</body>
</html>
