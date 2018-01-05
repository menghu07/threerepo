<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<script src="<%=path%>/js/jquery/jquery-3.2.1.js"></script>
<script>
    //在DOM ready之后执行
    jQuery(function fun() {
        var url = "http://localhost:8080/ServletJsp/cookiesession2/youlike/preferenceget.do";
        $.get(url, function(data, status) {
            console.log(data);
            console.log(status)
        });
    });
    jQuery(function () {
        var url2 = "http://localhost:9802/DistributeSite/cors.do";
        $.get(url2, function(data, status) {
            console.log(data);
            console.log(status)
        });
    });
    //首先第一个方法
    $(function () {
        console.log("the same as ready");
    });
    $(document).ready(function () {
        console.log("在DOM准备好之后执行");
    });
    $.ready(function () {
        console.log("不会输出");
    });
    (function autoExec() {
        console.log("js自执行");
    })();
</script>
<p>HelloWorld</p>
</body>
</html>
