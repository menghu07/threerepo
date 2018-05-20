<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Upload One File</title>
    <script src="${pageContext.request.contextPath}/js/jquery/jquery-3.2.1.js"></script>
</head>
<body>

<script>
    /**
     * onclick 返回true还是false不要影响事件的默认行为，改变事件默认行为的方法是event.preventDefault();
     * @param e
     * @param x
     * @param y
     * @returns {boolean}
     */
    function onclick2(e, x, y) {
        console.log("event: " + e);
        console.log("event: " + x);
        console.log("event: " + y);
        return false;
    }
</script>
<form action="${pageContext.request.contextPath}/chapter11/multifilesupload.do" method="post" enctype="multipart/form-data">
    Author: <input type="text" name="author"/><br/>
    Please Choose Multi Files To Upload: <input type="file" name="fileName" multiple="multiple"/><br/>
    <input id="btn1" type="submit" value="Upload" onclick="onclick2(event)"/>
</form>
</body>
</html>
