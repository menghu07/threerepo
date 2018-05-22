<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ProductDetail</title>
</head>
<body>
<table>
    <thead>
    <th>名称</th><th>值</th>
    </thead>
    <tr><td>产品名称</td><td>${product.name}</td></tr>
    <tr><td>产品描述</td><td>${product.description}</td></tr>
    <tr><td>产品价格</td><td>${product.price}</td></tr>
</table>
</body>
</html>
