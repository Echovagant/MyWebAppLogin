<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>欢迎光临</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #e6f7ff; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .welcome-card { background: white; padding: 50px; border-radius: 15px; box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2); width: 450px; text-align: center; border-left: 5px solid #007bff; }
        h1 { color: #007bff; font-size: 2.5em; margin-bottom: 20px; }
        p { color: #555; font-size: 1.2em; line-height: 1.6; }
        .highlight { color: #e65100; font-weight: bold; font-size: 1.5em; }
        .counter { margin-top: 30px; padding: 15px; background-color: #f0f0f0; border-radius: 8px; border: 1px dashed #aaa; }
    </style>
</head>
<body>
<%
    // 检查 Session 是否有效
    String userName = (String) session.getAttribute("userName");
    if (userName == null) {
        // Session 无效或未登录，跳转回登录页
        response.sendRedirect("login.jsp");
        return;
    }

    // 获取 Application 范围内的访客计数
    Integer visitorCount = (Integer) application.getAttribute("visitorCount");
    if (visitorCount == null) {
        visitorCount = 0; // 理论上 session.jsp 应该已经初始化，这里作为安全检查
    }
%>

<div class="welcome-card">
    <h1>🎉 欢迎光临! 🎉</h1>
    <p>尊敬的用户 <span class="highlight"><%= userName %></span>,</p>
    <p>您的学号是：<%= session.getAttribute("userStudentId") %></p>
    <p>您所属的班级是：<%= session.getAttribute("userClassId") %></p>

    <div class="counter">
        <p>您是第 <span class="highlight"><%= visitorCount %></span> 位访问本站的用户。</p>
    </div>

    <p style="margin-top: 40px; font-size: 0.9em;">
        <a href="login.jsp" style="color: #007bff; text-decoration: none;">返回登录页</a> |
        <a href="<%= request.getContextPath() %>/index.jsp" style="color: #007bff; text-decoration: none;">返回主应用首页</a>
    </p>
</div>
</body>
</html>