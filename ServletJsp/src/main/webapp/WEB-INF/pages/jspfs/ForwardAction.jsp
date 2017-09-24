<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script>
        function fnn() {
            var xi = 23;
            console.log("you")
        }
        fnn();
    </script>
    <title>Forward Action Jsp</title>
</head>
<body>
<p>reference page: <%=request.getParameter("reference")%>
</p>
</body>
</html>