<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>添加竞赛 - 竞赛系统</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <style type="text/tailwindcss">
        @layer utilities {
            .content-auto {
                content-visibility: auto;
            }
            .bg-gradient {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            }
        }
    </style>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-gradient min-h-screen">
    <div class="container mx-auto px-4 py-12">
        <div class="max-w-2xl mx-auto bg-white rounded-xl shadow-2xl overflow-hidden">
            <!-- Header -->
            <div class="bg-gradient-to-r from-purple-600 to-indigo-600 text-white px-6 py-4">
                <div class="flex items-center">
                    <a href="admin?action=viewCompetitions" class="text-white hover:text-purple-100 mr-4">
                        <i class="fas fa-arrow-left text-xl"></i>
                    </a>
                    <h1 class="text-2xl font-bold">添加竞赛</h1>
                </div>
            </div>
            
            <!-- Main Content -->
            <div class="p-6">
                <!-- Error Message -->
                <c:if test="${not empty error}">
                    <div class="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg mb-6 flex items-center">
                        <i class="fas fa-exclamation-circle mr-2"></i>
                        <span>${error}</span>
                    </div>
                </c:if>
                
                <!-- Competition Form -->
                <form action="admin" method="post">
                    <input type="hidden" name="action" value="saveCompetition">
                    
                    <div class="mb-5">
                        <label for="name" class="block text-sm font-medium text-gray-700 mb-1">竞赛名称 *</label>
                        <input type="text" id="name" name="name" required 
                               class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500 transition duration-300">
                    </div>
                    
                    <div class="grid grid-cols-1 md:grid-cols-2 gap-5">
                        <div class="mb-5">
                            <label for="type" class="block text-sm font-medium text-gray-700 mb-1">竞赛类别 *</label>
                            <select id="type" name="type" required 
                                    class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500 transition duration-300">
                                <option value="">请选择竞赛类别</option>
                                <option value="编程类">编程类</option>
                                <option value="设计类">设计类</option>
                                <option value="数学类">数学类</option>
                                <option value="英语类">英语类</option>
                                <option value="机器人">机器人</option>
                                <option value="其他">其他</option>
                            </select>
                        </div>
                        
                        <div class="mb-5">
                            <label for="level" class="block text-sm font-medium text-gray-700 mb-1">级别 *</label>
                            <select id="level" name="level" required 
                                    class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500 transition duration-300">
                                <option value="">请选择级别</option>
                                <option value="校级">校级</option>
                                <option value="市级">市级</option>
                                <option value="省级">省级</option>
                                <option value="国家级">国家级</option>
                                <option value="国际级">国际级</option>
                            </select>
                        </div>
                    </div>
                    
                    <div class="mb-5">
                        <label for="formType" class="block text-sm font-medium text-gray-700 mb-1">参赛形式 *</label>
                        <select id="formType" name="formType" required 
                                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500 transition duration-300">
                            <option value="">请选择参赛形式</option>
                            <option value="个人">个人</option>
                            <option value="团体">团体</option>
                            <option value="个人/团体均可">个人/团体均可</option>
                        </select>
                    </div>
                    
                    <div class="grid grid-cols-1 md:grid-cols-2 gap-5">
                        <div class="mb-5">
                            <label for="startDate" class="block text-sm font-medium text-gray-700 mb-1">开始日期 *</label>
                            <input type="date" id="startDate" name="startDate" required 
                                   class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500 transition duration-300">
                        </div>
                        
                        <div class="mb-5">
                            <label for="endDate" class="block text-sm font-medium text-gray-700 mb-1">结束日期 *</label>
                            <input type="date" id="endDate" name="endDate" required 
                                   class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500 transition duration-300">
                        </div>
                    </div>
                    
                    <!-- Form Actions -->
                    <div class="flex justify-end space-x-3 pt-4">
                        <button type="button" onclick="window.location.href='admin?action=viewCompetitions'" 
                                class="bg-gray-200 hover:bg-gray-300 text-gray-800 font-medium py-2 px-6 rounded-lg transition duration-300">
                            取消
                        </button>
                        <button type="submit" 
                                class="bg-gradient-to-r from-purple-600 to-indigo-600 hover:from-purple-700 hover:to-indigo-700 text-white font-medium py-2 px-6 rounded-lg transition duration-300 flex items-center">
                            <i class="fas fa-save mr-2"></i> 保存
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>