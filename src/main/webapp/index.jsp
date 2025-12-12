<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>欢迎页面 - JavaWeb 实验项目</title>
    <style>
        body { font-family: 'Arial', sans-serif; background-color: #f4f4f4; text-align: center; padding-top: 50px; }
        .container { background: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); display: inline-block; }
        h1 { color: #333; }
        .links a {
            display: inline-block;
            margin: 10px;
            padding: 10px 20px;
            text-decoration: none;
            color: white;
            background-color: #007bff;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        .links a:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>JavaWeb 实验项目</h1>
    <p>请选择你要进行的功能模块：</p>
    <div class="links">
        <!-- 现有登录功能入口 (实验一) -->
        <a href="auth/login.html">用户登录系统</a>

        <!-- 新增购物车功能入口 (实验二: Session & Cookie) -->
        <a href="shop/products">进入购物中心</a>

        <!-- 新增学生信息登录验证 (实验三) -->
        <a href="exp3/login.jsp">学生登录验证</a>

        <!-- 新增竞赛模块 (实验四) -->
        <a href="competition/login.jsp">实验四：竞赛模块系统</a>
    </div>

    <hr style="margin-top: 20px;">

    <p>当前时间: <%= new java.util.Date() %></p>
</div>
</body>
</html>
