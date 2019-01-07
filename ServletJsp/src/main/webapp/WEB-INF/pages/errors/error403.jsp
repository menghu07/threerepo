<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true" session="false" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>403 Not Authorized</title>
</head>
<body>
<p>message:<%=request.getServletPath()%></p>
<p>exception:<%=exception%></p>
<p>The Requested Resource is Not Authorized</p>
<h4>www.apeny.com</h4>
</body>
</html>