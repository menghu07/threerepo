<%--
  Created by IntelliJ IDEA.
  User: apeny
  Date: 2018/5/27
  Time: 19:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login 4 Tomcat</title>
</head>
<body>
<form action="j_security_check" method="post">
    用户名：<input name="j_username"/><br/>
    密码：<input type="password" name="j_password"/><br/>
    <input type="submit" value="提交"/><input type="reset" value="重置"/>
</form>
</body>
</html>
