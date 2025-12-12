<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ page import="com.mypro.competition.model.User" %>
<%@ page import="com.mypro.competition.model.Registration" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    // 角色检查 (RoleFilter 已经处理了大部分，这里作为页面逻辑的初始化)
    User user = (User) session.getAttribute("currentUser");
    if (user == null || !"admin".equals(user.getRole())) {
        response.sendRedirect(request.getContextPath() + "/competition/login.jsp");
        return;
    }

    List<Registration> registrations = (List<Registration>) request.getAttribute("registrations");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>管理员仪表板 - 报名查询</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <style>body { font-family: 'Inter', sans-serif; }</style>
</head>
<body class="bg-gray-50">
<div class="container mx-auto p-8">
    <header class="flex justify-between items-center py-4 border-b border-red-300 mb-8">
        <h1 class="text-3xl font-extrabold text-red-700">管理员控制台</h1>
        <nav class="flex items-center space-x-4">
            <span class="text-gray-600">欢迎, <span class="font-bold text-red-600"><%= user.getName() %></span></span>
            <a href="<%= request.getContextPath() %>/competition/logout" class="bg-red-500 hover:bg-red-600 text-white text-sm py-2 px-4 rounded-full transition duration-150 shadow-md">
                安全退出
            </a>
        </nav>
    </header>

    <main>
        <h2 class="text-2xl font-semibold text-gray-800 mb-6">所有竞赛报名查询结果</h2>

        <div class="bg-white rounded-xl shadow-lg overflow-x-auto">
            <table class="min-w-full divide-y divide-gray-200">
                <thead class="bg-gray-100">
                <tr>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">学生姓名</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">竞赛类别</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">队伍名称</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">报名时间</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">操作</th>
                </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                <%
                    if (registrations != null && !registrations.isEmpty()) {
                        for (Registration reg : registrations) {
                %>
                <tr>
                    <td class="px-6 py-4 whitespace-nowrap"><%= reg.getId() %></td>
                    <td class="px-6 py-4 whitespace-nowrap"><%= reg.getStudentUser().getName() %></td>
                    <td class="px-6 py-4 whitespace-nowrap"><%= reg.getCompetition().getName() %></td>
                    <td class="px-6 py-4 whitespace-nowrap"><%= reg.getTeamName() != null ? reg.getTeamName() : "N/A (个人赛)" %></td>
                    <td class="px-6 py-4 whitespace-nowrap"><%= sdf.format(reg.getRegistrationDate()) %></td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                        <a href="#" class="text-blue-600 hover:text-blue-900">详情/删除</a>
                    </td>
                </tr>
                <%      }
                } else { %>
                <tr><td colspan="6" class="px-6 py-4 text-center text-gray-500">暂无报名记录。</td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </main>
</div>
</body>
</html>