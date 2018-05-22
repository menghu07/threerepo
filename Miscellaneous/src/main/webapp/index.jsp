<%@ page import="com.apeny.servletjsp.chapter01.AjaxGetServlet" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" buffer="16kb" session="false" autoFlush="false"
         %>
<html>
<%AjaxGetServlet servlet = new AjaxGetServlet();
    System.out.println("see this servlet: " + servlet);
%>

<body>
<h2>Hello World!</h2>
</body>
</html>
