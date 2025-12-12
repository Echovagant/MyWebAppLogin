<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.mypro.competition.model.Competition" %>
<%@ page import="com.mypro.competition.data.CompetitionRepository" %>
<%
    // 获取竞赛ID和形式
    String compId = request.getParameter("compId");
    String formType = request.getParameter("formType");
    CompetitionRepository compRepo = new CompetitionRepository();
    Competition competition = null;

    if (compId != null && !compId.isEmpty()) {
        competition = compRepo.findById(Integer.parseInt(compId));
    }
%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>竞赛报名 - <%= competition != null ? competition.getName() : "" %></title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <style>body { font-family: 'Inter', sans-serif; }</style>
</head>
<body class="bg-gray-50">
<div class="container mx-auto p-8">
    <header class="flex justify-between items-center py-4 border-b border-blue-300 mb-8">
        <h1 class="text-3xl font-extrabold text-blue-700">竞赛报名</h1>
        <nav class="flex items-center space-x-4">
            <a href="home" class="text-blue-600 hover:text-blue-800 font-medium">返回主页</a>
            <a href="logout" class="bg-red-500 hover:bg-red-600 text-white text-sm py-2 px-4 rounded-full transition duration-150 shadow-md">
                退出登录
            </a>
        </nav>
    </header>

    <main>
        <% if (competition != null) { %>
        <div class="bg-white p-8 rounded-xl shadow-lg mb-8">
            <h2 class="text-2xl font-bold text-gray-800 mb-4">报名竞赛: <%= competition.getName() %></h2>
            <div class="text-gray-600 mb-6">
                <p><strong>竞赛级别:</strong> <%= competition.getLevel() %></p>
                <p><strong>报名形式:</strong> <span class="<%= formType.contains("团体") ? "text-red-500" : "text-green-500" %> font-semibold"><%= formType %></span></p>
                <p><strong>报名截止日期:</strong> <%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(competition.getEndDate()) %></p>
            </div>

            <form action="<%= request.getContextPath() %>/competition/register" method="post">
                <input type="hidden" name="compId" value="<%= competition.getId() %>">
                <input type="hidden" name="formType" value="<%= formType %>">

                <% if (formType.contains("团体")) { %>
                <!-- 团体赛报名表单 -->
                <div class="mb-4">
                    <label for="teamName" class="block text-gray-700 text-sm font-bold mb-2">队伍名称:</label>
                    <input type="text" id="teamName" name="teamName" required
                           class="shadow appearance-none border rounded w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500">
                </div>

                <div class="mb-4">
                    <label for="teamMembers" class="block text-gray-700 text-sm font-bold mb-2">团队成员 (用逗号分隔):</label>
                    <input type="text" id="teamMembers" name="teamMembers" required
                           class="shadow appearance-none border rounded w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500"
                           placeholder="例如: 张三,李四,王五">
                </div>
                <% } %>

                <div class="mb-6">
                    <label for="contactEmail" class="block text-gray-700 text-sm font-bold mb-2">联系邮箱:</label>
                    <input type="email" id="contactEmail" name="contactEmail" required
                           class="shadow appearance-none border rounded w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500">
                </div>

                <div class="flex justify-between">
                    <button type="submit" class="bg-blue-600 hover:bg-blue-700 text-white font-bold py-3 px-6 rounded-lg transition duration-150">
                        提交报名
                    </button>
                    <a href="home" class="bg-gray-500 hover:bg-gray-600 text-white font-bold py-3 px-6 rounded-lg transition duration-150">
                        取消
                    </a>
                </div>
            </form>
        </div>
        <% } else { %>
        <div class="bg-white p-8 rounded-xl shadow-lg text-center">
            <h2 class="text-2xl font-bold text-red-600 mb-4">竞赛信息错误</h2>
            <p class="text-gray-600 mb-6">无法找到指定的竞赛信息，请返回主页重新选择竞赛。</p>
            <a href="home" class="bg-blue-600 hover:bg-blue-700 text-white font-bold py-3 px-6 rounded-lg transition duration-150 inline-block">
                返回主页
            </a>
        </div>
        <% } %>
    </main>
</div>
</body>
</html>