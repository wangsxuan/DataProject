<%@page pageEncoding="UTF-8" contentType="text/html; UTF-8" isELIgnored="false" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Login</title>
    <link rel="stylesheet" href="../boot/css/bootstrap.css">

</head>
<body style="text-align:center">
    <form action="${pageContext.request.contextPath}/user/register" method="post">
        用户名：<input type="text" class="form-control" name="username"/><br/>
        密  码：<input type="password" class="form-control" name="passowrd"/><br/>
        邮  箱：<input type="text" class="form-control" name="email"/><br/>
        <input type="submit" class="form-control" value="注册"/><br/>
    </form>
</body>
</html>