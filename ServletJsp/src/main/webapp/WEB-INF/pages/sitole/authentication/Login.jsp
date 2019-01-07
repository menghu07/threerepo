<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Page</title>
</head>
<body>
<h4>Welcome to sitole</h4>
<form action="${pageContext.request.contextPath}/login.dox">
    <label>用户名：</label><input name="username"/><br/>
    <label>密&emsp;码：</label><input type="password" name="password"/><br/>
    <input type="submit" value="提交"/>
</form>
</body>
</html>
