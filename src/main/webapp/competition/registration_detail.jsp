<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>报名详情</title>
    <!-- Tailwind CSS -->
    <script src="https://cdn.tailwindcss.com"></script>
    <!-- Font Awesome -->
    <link href="https://cdn.jsdelivr.net/npm/font-awesome@4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <!-- 自定义样式 -->
    <style type="text/tailwindcss">
        @layer utilities {
            .content-auto {
                content-visibility: auto;
            }
            .bg-gray-50 {
                background-color: #f9fafb;
            }
            .shadow-sm {
                box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.05);
            }
        }
    </style>
</head>
<body class="bg-gray-50">
    <!-- 导航栏 -->
    <nav class="bg-white shadow-sm">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div class="flex justify-between h-16">
                <div class="flex items-center">
                    <h1 class="text-xl font-semibold text-gray-800">竞赛报名管理系统</h1>
                </div>
                <div class="flex items-center">
                    <span class="mr-4 text-gray-600">欢迎，管理员</span>
                    <a href="${pageContext.request.contextPath}/competition/admin" class="mr-4 text-gray-600 hover:text-gray-900">
                        <i class="fa fa-arrow-left"></i> 返回仪表板
                    </a>
                    <a href="${pageContext.request.contextPath}/competition/logout" class="text-gray-600 hover:text-gray-900">
                        <i class="fa fa-sign-out"></i> 退出登录
                    </a>
                </div>
            </div>
        </div>
    </nav>

    <!-- 主要内容 -->
    <main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div class="bg-white rounded-lg shadow-sm p-6">
            <h2 class="text-2xl font-semibold text-gray-800 mb-6">报名记录详情</h2>
            
            <%-- 报名记录详情 --%>
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div class="border-b border-gray-200 pb-4">
                    <h3 class="text-lg font-medium text-gray-700 mb-3">基本信息</h3>
                    <div class="space-y-3">
                        <div>
                            <span class="text-sm font-medium text-gray-500">报名ID:</span>
                            <span class="ml-2 text-sm text-gray-700"><%= ((com.mypro.competition.model.Registration)request.getAttribute("registration")).getId() %></span>
                        </div>
                        <div>
                            <span class="text-sm font-medium text-gray-500">报名日期:</span>
                            <span class="ml-2 text-sm text-gray-700">
                                <%= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(((com.mypro.competition.model.Registration)request.getAttribute("registration")).getRegistrationDate()) %>
                            </span>
                        </div>
                        <div>
                            <span class="text-sm font-medium text-gray-500">报名状态:</span>
                            <span class="ml-2 text-sm text-gray-700"><%= ((com.mypro.competition.model.Registration)request.getAttribute("registration")).getStatus() %></span>
                        </div>
                    </div>
                </div>
                
                <div class="border-b border-gray-200 pb-4">
                    <h3 class="text-lg font-medium text-gray-700 mb-3">团队信息</h3>
                    <div class="space-y-3">
                        <div>
                            <span class="text-sm font-medium text-gray-500">团队名称:</span>
                            <span class="ml-2 text-sm text-gray-700">
                                <%= ((com.mypro.competition.model.Registration)request.getAttribute("registration")).getTeamName() != null ? 
                                    ((com.mypro.competition.model.Registration)request.getAttribute("registration")).getTeamName() : 
                                    "N/A (个人赛)" %>
                            </span>
                        </div>
                    </div>
                </div>
                
                <div class="border-b border-gray-200 pb-4">
                    <h3 class="text-lg font-medium text-gray-700 mb-3">学生信息</h3>
                    <div class="space-y-3">
                        <div>
                            <span class="text-sm font-medium text-gray-500">学生姓名:</span>
                            <span class="ml-2 text-sm text-gray-700"><%= ((com.mypro.competition.model.Registration)request.getAttribute("registration")).getStudentUser().getName() %></span>
                        </div>
                        <div>
                            <span class="text-sm font-medium text-gray-500">学号:</span>
                            <span class="ml-2 text-sm text-gray-700"><%= ((com.mypro.competition.model.Registration)request.getAttribute("registration")).getStudentUser().getUsername() %></span>
                        </div>
                        <div>
                            <span class="text-sm font-medium text-gray-500">学号:</span>
                            <span class="ml-2 text-sm text-gray-700"><%= ((com.mypro.competition.model.Registration)request.getAttribute("registration")).getStudentUser().getStudentId() %></span>
                        </div>
                        <div>
                            <span class="text-sm font-medium text-gray-500">专业:</span>
                            <span class="ml-2 text-sm text-gray-700"><%= ((com.mypro.competition.model.Registration)request.getAttribute("registration")).getStudentUser().getMajor() %></span>
                        </div>
                    </div>
                </div>
                
                <div class="border-b border-gray-200 pb-4">
                    <h3 class="text-lg font-medium text-gray-700 mb-3">竞赛信息</h3>
                    <div class="space-y-3">
                        <div>
                            <span class="text-sm font-medium text-gray-500">竞赛名称:</span>
                            <span class="ml-2 text-sm text-gray-700"><%= ((com.mypro.competition.model.Registration)request.getAttribute("registration")).getCompetition().getName() %></span>
                        </div>
                        <div>
                            <span class="text-sm font-medium text-gray-500">竞赛级别:</span>
                            <span class="ml-2 text-sm text-gray-700"><%= ((com.mypro.competition.model.Registration)request.getAttribute("registration")).getCompetition().getLevel() %></span>
                        </div>
                        <div>
                            <span class="text-sm font-medium text-gray-500">竞赛形式:</span>
                            <span class="ml-2 text-sm text-gray-700"><%= ((com.mypro.competition.model.Registration)request.getAttribute("registration")).getCompetition().getFormType() %></span>
                        </div>
                        <div>
                            <span class="text-sm font-medium text-gray-500">报名开始时间:</span>
                            <span class="ml-2 text-sm text-gray-700">
                                <%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(((com.mypro.competition.model.Registration)request.getAttribute("registration")).getCompetition().getStartDate()) %>
                            </span>
                        </div>
                        <div>
                            <span class="text-sm font-medium text-gray-500">报名截止时间:</span>
                            <span class="ml-2 text-sm text-gray-700">
                                <%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(((com.mypro.competition.model.Registration)request.getAttribute("registration")).getCompetition().getEndDate()) %>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- 返回按钮 -->
            <div class="mt-8 flex justify-start">
                <a href="${pageContext.request.contextPath}/competition/admin" class="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md shadow-sm text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500">
                    <i class="fa fa-arrow-left mr-2"></i> 返回仪表板
                </a>
            </div>
        </div>
    </main>

    <!-- 页脚 -->
    <footer class="bg-white border-t border-gray-200 py-4 mt-8">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <p class="text-center text-sm text-gray-500">© 2024 竞赛报名管理系统</p>
        </div>
    </footer>
</body>
</html>