<%--
  Created by IntelliJ IDEA.
  User: apeny
  Date: 2018/5/26
  Time: 21:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form method="post" name="loginForm" action="${pageContext.request.contextPath}/download/login.do">
    用户名：<input name="username"/><br/>
    密码：<input name="password"/><br/>
    <input type="submit" value="提交"/><input type="reset" value="重置"/>
</form>
</body>
</html>
