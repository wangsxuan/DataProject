<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Login</title>
<%--
        <link rel="stylesheet" href="${pageContext.request.contextPath}/boot/css/bootstrap.css">
    --%>

</head>

<body style="text-align:center">
<span>${select}</span>
<form action="${pageContext.request.contextPath}/user/login" method="post">

    <input type="text" class="form-control"  name= "username" placeholder="请输入用户名" /> <br/>
    <input  type="password" class="form-control"  name = "password" placeholder="请输入密码"/><br/>
    <%--<input  type="email" class="form-control"  name="email" placeholder="请输入邮箱"  /><br/>--%>
    <input type="submit" class="form-control" value="提交"/><br/>
</form>

</body>
</html>