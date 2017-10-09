<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=Utf-8"
         pageEncoding="Utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    %>
    <c:set var="req" value="${pageContext.request}"/>
    <c:set var="basePathURL" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}"/>
    <!--WEB-INF下的文件不能通过地址访问，webapp下出WEB-INF外其他都可以直接用地址访问（可以做限制）-->
    <meta http-equiv="Content-Type" content="text/html; charset=Utf-8">
    <title>Action Element</title>
    <!--相对路径表示法-->
    <!--
    <script src="../../js/jquery/jquery-3.2.1.js"></script>
    -->
    <script src="${basePathURL}/js/jquery/jquery-3.2.1.js"></script>
    <script src="${basePathURL}/js/defjs/externalJs.js"></script>
    <script>
        function bb() {
            console.log(xy + 1023);
        }
        bb();
        function cc() {
            yue();
        }
    </script>
    <script>

        eval("var xy=1999; function yue() {console.log(920)}");
//        console.log(xy);
        //        yue();
        function addTab() {
            $.get("<%=request.getContextPath()%>/AjaxgetServlet/ajaxget.do", function (data) {
                var p1 = document.getElementById("p1");
                var div1 = document.createElement("div");
                div1.innerHTML = data;
                p1.appendChild(div1);
                eval($('#p1 script').html());
                ffy();
                var stop = 1;
            })
        }
        fnnt("call befor script")
        function fun1() {
            console.log("fun1" + 1);
            var fff = 1;
        }
    </script>

    <script>
        fun1();
        fnnt();
        function fnnt(arg) {
            var xi = 23;
            console.log("you" + arg)
        }

    </script>
</head>
<body>
<jsp:useBean id="customer" class="com.apeny.servletjsp.domain.Customer">
</jsp:useBean>
<jsp:setProperty property="id" name="customer" value="121"/>
<jsp:setProperty property="name" name="customer" value="huzi"/>
<jsp:setProperty property="city" name="customer" value="new york"/>
<p>customer: <%=customer%>
</p>
<p id="p1">want add a tab</p>
<p>customer Name:
    <jsp:getProperty property="name" name="customer"/>
    <br/>Page Context: pageContext.getRequest() == request <%=pageContext.getRequest() == request%>
<p>i don't know, i get request();ActionLabel Attribute: <%=request.getAttribute("ActionLabel")%></p>
</p>
<jsp:include page="../jspfs/IncludeAction.jsp">
    <jsp:param value="<%=request.getServletPath()%>" name="reference"/>
</jsp:include>
<!--redirect到WEB-INF下会被禁止-->
<form name="testform" action="${pageContext.request.contextPath}/WEB-INF/view/testdir/MainPage.jsp">
    <input type="submit" value="test">
</form>
<%--<jsp:forward page="../jspfs/ForwardAction.jsp">
    <jsp:param value="<%=request.getRequestURI() %>" name="reference"/>
</jsp:forward>--%>
<%
    System.out.println("test can execute....");
%>

<p id="p2" onclick="ffy()">clict here.....</p>
<br/>
<br/>
<br/>
<br/>
<p id="p3" onclick="yue()">yue......</p>
</body>
</html>