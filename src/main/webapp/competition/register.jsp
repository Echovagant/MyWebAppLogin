<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户注册</title>
    <meta charset="UTF-8">
</head>
<body>
<h1>用户注册</h1>

<%-- 显示错误信息 --%>
<% if (request.getAttribute("errorMessage") != null) { %>
<div style="color: red;">
    <%= request.getAttribute("errorMessage") %>
</div>
<% } %>

<%-- 显示成功信息 --%>
<% if (request.getAttribute("successMessage") != null) { %>
<div style="color: green;">
    <%= request.getAttribute("successMessage") %>
</div>
<% } %>

<form action="<%= request.getContextPath() %>/competition/register" method="post">
    <div>
        <label for="username">用户名（学号）:</label>
        <input type="text" id="username" name="username" required>
    </div>

    <div>
        <label for="password">密码:</label>
        <input type="password" id="password" name="password" required>
    </div>

    <div>
        <label for="name">真实姓名:</label>
        <input type="text" id="name" name="name" required>
    </div>

    <div>
        <input type="submit" value="注册">
    </div>
</form>

<p>已有账号？<a href="<%= request.getContextPath() %>/competition/login">登录</a></p>
</body>
</html>