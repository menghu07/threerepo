<%--
  Created by IntelliJ IDEA.
  User: apeny
  Date: 2018/5/23
  Time: 7:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ProductForm</title>
</head>
<body>
<form action="${pageContext.servletContext.contextPath}/chapter10/product_save.do" method="post">
    Product Name: <input name="name"/><br/>
    Product Description: <input name="description"/><br/>
    Product Price: <input name="price"/><br/>
    <input type="submit" value="提交"/>
</form>
</body>
</html>
