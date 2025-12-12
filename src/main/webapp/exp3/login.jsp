<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>实验三：用户登录</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f0f4f8; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .login-container { background: white; padding: 40px; border-radius: 12px; box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1); width: 350px; text-align: center; }
        h2 { color: #333; margin-bottom: 25px; border-bottom: 2px solid #007bff; padding-bottom: 10px; }
        .form-group { margin-bottom: 20px; text-align: left; }
        label { display: block; margin-bottom: 8px; font-weight: bold; color: #555; }
        input[type="text"], select { width: 100%; padding: 12px; border: 1px solid #ccc; border-radius: 6px; box-sizing: border-box; font-size: 16px; transition: border-color 0.3s; }
        input[type="text"]:focus, select:focus { border-color: #007bff; outline: none; box-shadow: 0 0 5px rgba(0, 123, 255, 0.2); }
        .submit-btn { background-color: #007bff; color: white; padding: 12px 20px; border: none; border-radius: 6px; cursor: pointer; width: 100%; font-size: 18px; transition: background-color 0.3s, transform 0.1s; }
        .submit-btn:hover { background-color: #0056b3; }
        .submit-btn:active { transform: scale(0.98); }
    </style>
</head>
<body>
<div class="login-container">
    <h2>用户身份验证</h2>
    <!-- 提交到 session.jsp 进行验证 -->
    <form action="session.jsp" method="post">

        <div class="form-group">
            <label for="name">姓 名:</label>
            <input type="text" id="name" name="name" required placeholder="请输入您的姓名">
        </div>

        <div class="form-group">
            <label for="studentId">学 号:</label>
            <input type="text" id="studentId" name="studentId" required placeholder="请输入您的学号">
        </div>

        <div class="form-group">
            <label for="classId">班 级:</label>
            <select id="classId" name="classId" required>
                <option value="">-- 请选择您的班级 --</option>
                <option value="学生专业1234">学生专业1234</option>
                <option value="信息技术1234">信息技术1234</option>
                <option value="软件工程2301">软件工程2301</option>
            </select>
        </div>

        <button type="submit" class="submit-btn">登 录</button>
    </form>
</div>
</body>
</html>