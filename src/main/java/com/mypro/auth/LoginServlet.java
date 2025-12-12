package com.mypro.auth; // 必须添加这一行
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;
import java.io.Serializable; // 重新导入 Serializable 确保内部类可序列化
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet implements Serializable { // 重新实现 Serializable
    @Serial
    private static final long serialVersionUID = 1L;

    // 使用线程安全的集合存储所有用户的登录记录
    private static final List<LoginRecord> loginRecords = new CopyOnWriteArrayList<>();

    // --------------------------------------------------------------------------------
    // 1. 处理 POST 请求 (登录操作)
    // --------------------------------------------------------------------------------
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置请求和响应编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // 获取表单参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String userType = request.getParameter("userType");

        // 参数验证
        if (username == null || password == null || userType == null ||
                username.trim().isEmpty() || password.trim().isEmpty()) {
            // 参数无效时返回错误页面
            showErrorPage(response);
            return;
        }

        // 业务逻辑：获取用户类型中文名
        String userTypeCN = getChineseUserType(userType);

        // 业务逻辑：创建登录记录并添加到集合中
        String sessionId = UUID.randomUUID().toString().substring(0, 8);
        String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        LoginRecord record = new LoginRecord(sessionId, username, userType, userTypeCN, loginTime);
        loginRecords.add(record);

        // Controller: 准备数据并存储到 Session
        HttpSession session = request.getSession();
        session.setAttribute("loginRecord", record);
        session.setAttribute("lastLoginTime", loginTime);

        // 准备数据并存储到 Request (用于本次转发)
        List<LoginRecord> sortedRecords = new ArrayList<>(loginRecords);
        Collections.reverse(sortedRecords);
        request.setAttribute("sortedRecords", sortedRecords);

        // -----------------------------------------------------------------
        // MVC 核心改变: 从直接输出 HTML 改为 请求转发给 JSP (View)
        // -----------------------------------------------------------------
        request.getRequestDispatcher("/login_success.jsp").forward(request, response);
    }

    // --------------------------------------------------------------------------------
    // 2. 辅助方法：获取用户类型的中文名称
    // --------------------------------------------------------------------------------
    private String getChineseUserType(String userType) {
        return switch (userType.toLowerCase()) {
            case "student" -> "学生";
            case "teacher" -> "教师";
            case "staff" -> "工作人员";
            default -> "未知";
        };
    }

    // --------------------------------------------------------------------------------
    // 3. 辅助方法：显示错误页面 (这部分仍然保留在 Servlet 中)
    // --------------------------------------------------------------------------------
    private void showErrorPage(HttpServletResponse response) throws IOException {
        // 错误页面因为代码量小且不涉及复杂数据，保留在 Servlet 中是可接受的
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang='zh-CN'>");
        out.println("<head>");
        out.println("    <meta charset='UTF-8'>");
        out.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("    <title>登录错误</title>");
        out.println("    <style>");
        out.println("        body { font-family: 'Segoe UI', sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; background: #f5f5f5; margin: 0; }");
        out.println("        .error-container { text-align: center; background: white; padding: 40px; border-radius: 10px; box-shadow: 0 5px 15px rgba(0,0,0,0.1); max-width: 400px; }");
        out.println("        h2 { color: #e74c3c; margin-bottom: 20px; }");
        out.println("        p { color: #555; margin-bottom: 30px; }");
        out.println("        .btn { background: #3498db; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; text-decoration: none; font-weight: bold; }");
        out.println("        .btn:hover { background: #2980b9; }");
        out.println("    </style>");
        out.println("</head>");
        out.println("<body>");
        out.println("    <div class='error-container'>");
        out.println("        <h2>登录失败</h2>");
        out.println("        <p>" + "登录信息不完整，请重新登录" + "</p>");
        out.println("        <a href='login.html' class='btn'>返回登录</a>");
        out.println("    </div>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    // --------------------------------------------------------------------------------
        // 4. 登录记录内部类 (为了让 JSP 可以访问，必须是 public)
        // --------------------------------------------------------------------------------
        public record LoginRecord(String sessionId, String username, String userType, String userTypeCN,
                                  String loginTime) implements Serializable {
            @Serial
            private static final long serialVersionUID = 1L;
    }
}