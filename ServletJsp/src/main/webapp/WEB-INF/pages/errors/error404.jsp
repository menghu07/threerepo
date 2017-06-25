<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true" session="false" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>404 Not Found</title>
</head>
<body>
<p>message:<%=request.getServletPath()%></p>
<p>exception:<%=exception%></p>
<p>The Requested Resource is not Available</p>
<h4>www.apeny.com</h4>
</body>
</html>