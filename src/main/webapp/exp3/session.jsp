<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // 设置页面不缓存，防止回退问题
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setHeader("Expires", "0"); // Proxies

    // 1. 获取表单提交的数据
    String name = request.getParameter("name");
    String studentId = request.getParameter("studentId");
    String classId = request.getParameter("classId");

    // 2. 定义正确的验证信息 (已更新为新的信息)
    final String CORRECT_NAME = "刘宇飞"; // 新的姓名
    final String CORRECT_STUDENT_ID = "2331051544"; // 新学号
    final String CORRECT_CLASS_ID = "软件工程2301"; // 新的班级

    // 3. 验证用户信息
    boolean isAuthenticated = CORRECT_NAME.equals(name) &&
            CORRECT_STUDENT_ID.equals(studentId) &&
            CORRECT_CLASS_ID.equals(classId);

    if (isAuthenticated) {
        // 4. 验证通过：将相关信息存入Session
        session.setAttribute("userName", name);
        session.setAttribute("userStudentId", studentId);
        session.setAttribute("userClassId", classId);
        session.setAttribute("isAuthenticated", true);

        // 获取并更新访客计数器（Application内置对象）
        Integer visitorCount = (Integer) application.getAttribute("visitorCount");
        if (visitorCount == null) {
            visitorCount = 1;
        } else {
            visitorCount = visitorCount + 1;
        }
        application.setAttribute("visitorCount", visitorCount);

        // 5. 跳转到登录成功的 welcome.jsp 页面
        response.sendRedirect("welcome.jsp");
    } else {
        // 6. 验证失败：弹出JavaScript对话框并跳转回登录页面 (提示信息已更新)
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>验证失败</title>
</head>
<body>
<script>
    alert("用户信息错误！请检查：\n姓名：<%= CORRECT_NAME %>\n学号：<%= CORRECT_STUDENT_ID %>\n班级：<%= CORRECT_CLASS_ID %>");
    // 跳转回登录页面
    window.location.href = "login.jsp";
</script>
</body>
</html>
<%
    }
%>