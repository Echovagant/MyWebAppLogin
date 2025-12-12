<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户注册 - 竞赛系统</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <style>
        body { font-family: 'Inter', sans-serif; }
        .register-container { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); }
    </style>
</head>
<body class="min-h-screen flex items-center justify-center">
    <div class="register-container w-full max-w-md p-8 rounded-xl shadow-lg">
        <div class="bg-white rounded-xl shadow-inner p-8">
            <h2 class="text-3xl font-bold mb-6 text-center text-gray-800">用户注册</h2>
            
            <%-- 显示错误信息 --%>
            <% if (request.getAttribute("errorMessage") != null) { %>
            <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4" role="alert">
                <%= request.getAttribute("errorMessage") %>
            </div>
            <% } %>
            
            <%-- 显示成功信息 --%>
            <% if (request.getAttribute("successMessage") != null) { %>
            <div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded mb-4" role="alert">
                <%= request.getAttribute("successMessage") %>
            </div>
            <% } %>
            
            <form action="<%= request.getContextPath() %>/competition/register" method="post">
                <div class="mb-4">
                    <label for="username" class="block text-gray-700 text-sm font-bold mb-2">用户名（学号）:</label>
                    <input type="text" id="username" name="username" required
                           class="shadow appearance-none border rounded w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500">
                </div>
                
                <div class="mb-4">
                    <label for="password" class="block text-gray-700 text-sm font-bold mb-2">密码:</label>
                    <input type="password" id="password" name="password" required
                           class="shadow appearance-none border rounded w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500">
                </div>
                
                <div class="mb-6">
                    <label for="name" class="block text-gray-700 text-sm font-bold mb-2">真实姓名:</label>
                    <input type="text" id="name" name="name" required
                           class="shadow appearance-none border rounded w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500">
                </div>
                
                <div class="flex items-center justify-between">
                    <button type="submit" class="bg-blue-600 hover:bg-blue-700 text-white font-bold py-3 px-6 rounded-lg transition duration-150">
                        注册
                    </button>
                    <a href="<%= request.getContextPath() %>/competition/login" class="text-sm text-blue-500 hover:text-blue-700 font-bold">
                        已有账号？登录
                    </a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>