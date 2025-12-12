<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.mypro.competition.model.Competition" %>
<%@ page import="com.mypro.competition.model.Registration" %>
<%@ page import="java.util.List" %>
<%
    // 获取从StudentHomeServlet传递的数据
    List<Competition> competitions = (List<Competition>) request.getAttribute("competitions");
    String userName = (String) request.getAttribute("userName");
    List<Registration> myRegistrations = (List<Registration>) request.getAttribute("myRegistrations");
%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>学生竞赛报名系统</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <style>body { font-family: 'Inter', sans-serif; }</style>
</head>
<body class="bg-gray-50">
<div class="container mx-auto p-8">
    <header class="flex justify-between items-center py-4 border-b border-blue-300 mb-8">
        <h1 class="text-3xl font-extrabold text-blue-700">竞赛报名系统</h1>
        <nav class="flex items-center space-x-4">
            <span class="text-gray-600">欢迎, <span class="font-bold text-blue-600"><%= userName %></span></span>
            <a href="<%= request.getContextPath() %>/competition/logout" class="bg-red-500 hover:bg-red-600 text-white text-sm py-2 px-4 rounded-full transition duration-150 shadow-md">
                退出登录
            </a>
        </nav>
    </header>

    <main>
        <h2 class="text-2xl font-semibold text-gray-800 mb-6">可报名竞赛列表</h2>

        <div class="bg-white p-6 rounded-xl shadow-lg mb-10">
            <table class="min-w-full divide-y divide-gray-200">
                <thead class="bg-gray-100">
                <tr>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">竞赛名称</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">竞赛级别</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">报名形式</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">报名截止日期</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">操作</th>
                </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                <%
                    if (competitions != null && !competitions.isEmpty()) {
                        for (Competition comp : competitions) {
                %>
                <tr>
                    <td class="px-6 py-4 whitespace-nowrap"><%= comp.getName() %></td>
                    <td class="px-6 py-4 whitespace-nowrap"><%= comp.getLevel() %></td>
                    <td class="px-6 py-4 whitespace-nowrap"><%= comp.getFormType() %></td>
                    <td class="px-6 py-4 whitespace-nowrap"><%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(comp.getEndDate()) %></td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                        <a href="registration_form.jsp?compId=<%= comp.getId() %>&formType=<%= comp.getFormType() %>"
                           class="text-blue-600 hover:text-blue-900">立即报名</a>
                    </td>
                </tr>
                <%      }
                } else { %>
                <tr><td colspan="5" class="px-6 py-4 text-center text-gray-500">暂无可用竞赛。</td></tr>
                <% } %>
                </tbody>
            </table>
        </div>

        <h2 class="text-2xl font-semibold text-gray-700 mt-10 mb-6 border-t pt-6">我的报名记录</h2>
        <div class="bg-white p-6 rounded-xl shadow-lg overflow-x-auto">
            <table class="min-w-full divide-y divide-gray-200">
                <thead class="bg-gray-100">
                <tr>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">竞赛名称</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">报名形式</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">队伍名称</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">报名时间</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">状态</th>
                </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                <%
                    if (myRegistrations != null && !myRegistrations.isEmpty()) {
                        for (Registration reg : myRegistrations) {
                            // 检查Competition对象是否存在
                            Competition competition = reg.getCompetition();
                %>
                <tr>
                    <td class="px-6 py-4 whitespace-nowrap"><%= competition != null ? competition.getName() : "未知竞赛" %></td>
                    <td class="px-6 py-4 whitespace-nowrap"><%= competition != null ? competition.getFormType() : "未知形式" %></td>
                    <td class="px-6 py-4 whitespace-nowrap"><%= reg.getTeamName() != null ? reg.getTeamName() : "个人赛" %></td>
                    <td class="px-6 py-4 whitespace-nowrap"><%= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(reg.getRegistrationDate()) %></td>
                    <td class="px-6 py-4 whitespace-nowrap">
                        <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800">
                            已报名
                        </span>
                    </td>
                </tr>
                <%      }
                } else { %>
                <tr><td colspan="5" class="px-6 py-4 text-center text-gray-500">您还没有报名任何竞赛。</td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </main>
</div>
</body>
</html>