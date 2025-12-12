<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mypro.auth.LoginServlet.LoginRecord" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>登录成功 - 欢迎</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f4f7f6; color: #333; margin: 0; padding: 20px; }
        .container { max-width: 900px; margin: 50px auto; background: white; padding: 30px; border-radius: 12px; box-shadow: 0 4px 20px rgba(0,0,0,0.1); }
        h1 { color: #2c3e50; border-bottom: 2px solid #3498db; padding-bottom: 10px; margin-bottom: 20px; }
        h2 { color: #34495e; margin-top: 30px; border-left: 5px solid #e74c3c; padding-left: 10px; }
        .welcome-card { background: #e8f5e9; padding: 20px; border-radius: 8px; margin-bottom: 30px; border: 1px solid #c8e6c9; }
        .welcome-card p { margin: 5px 0; line-height: 1.8; }
        .info-label { font-weight: bold; color: #27ae60; display: inline-block; width: 120px; }
        table { width: 100%; border-collapse: collapse; margin-top: 15px; }
        th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }
        th { background-color: #3498db; color: white; }
        tr:nth-child(even) { background-color: #f2f2f2; }
    </style>
</head>
<body>
<%-- 1. 从 Session 中获取当前登录记录 --%>
<% LoginRecord currentRecord = (LoginRecord) session.getAttribute("loginRecord"); %>
<% String lastLoginTime = (String) session.getAttribute("lastLoginTime"); %>

<%-- 2. 从 Request 中获取所有登录记录列表 --%>
<%
    // 强制转换为 LoginServlet.LoginRecord 类型，需要上面导入的包
    List<LoginRecord> records = (List<LoginRecord>) request.getAttribute("sortedRecords");
%>

<div class="container">
    <% if (currentRecord != null) { %>
    <h1>✨ 欢迎回来，<%= currentRecord.username() %>!</h1>

    <div class="welcome-card">
        <p><span class="info-label">用户角色:</span> <%= currentRecord.userTypeCN() %></p>
        <p><span class="info-label">登录时间:</span> <%= currentRecord.loginTime() %></p>
        <p><span class="info-label">会话ID:</span> <%= currentRecord.sessionId() %></p>
        <% if (lastLoginTime != null && !lastLoginTime.equals(currentRecord.loginTime())) { %>
        <p><span class="info-label">上次登录:</span> <%= lastLoginTime %></p>
        <% } %>
    </div>
    <% } else { %>
    <h1>会话信息丢失</h1>
    <p>无法获取您的登录记录，请尝试重新登录。</p>
    <% } %>

    <%-- 3. 显示历史登录记录 --%>
    <h2>🚪 全部历史登录记录 (最近的在最前面)</h2>

    <% if (records != null && !records.isEmpty()) { %>
    <table>
        <thead>
        <tr>
            <th>会话ID (前4位)</th>
            <th>用户名</th>
            <th>用户类型</th>
            <th>中文类型</th>
            <th>登录时间</th>
        </tr>
        </thead>
        <tbody>
        <%
            // 遍历所有记录并显示
            for (LoginRecord record : records) {
        %>
        <tr>
            <td><%= record.sessionId().substring(0, 4) + "..." %></td>
            <td><%= record.username() %></td>
            <td><%= record.userType() %></td>
            <td><%= record.userTypeCN() %></td>
            <td><%= record.loginTime() %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <% } else { %>
    <p>暂无历史登录记录。</p>
    <% } %>
</div>
</body>
</html>