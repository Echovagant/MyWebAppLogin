<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // 检查是否已登录
    if (session.getAttribute("isAuthenticated") == null || !(Boolean)session.getAttribute("isAuthenticated")) {
        // 未登录，跳转到登录页
        response.sendRedirect("login.jsp");
        return;
    }
    // 成功登录，立即跳转到欢迎页
    response.sendRedirect("welcome.jsp");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>登录成功</title>
</head>
<body>
<p>登录成功，正在跳转到欢迎页面...</p>
</body>
</html>