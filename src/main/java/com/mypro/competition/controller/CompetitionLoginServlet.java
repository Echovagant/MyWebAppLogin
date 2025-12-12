package com.mypro.competition.controller;

import com.mypro.competition.data.UserRepository;
import com.mypro.competition.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class CompetitionLoginServlet extends HttpServlet {

    private final UserRepository userRepository = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 显示登录页面
        request.getRequestDispatcher("/competition/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置请求编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // 获取登录信息
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 验证用户
        User user = userRepository.findByUsernameAndPassword(username, password);

        if (user != null) {
            // 登录成功，创建会话
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", user);
            session.setAttribute("userName", user.getName());

            // 根据用户角色重定向到不同页面
            if ("admin".equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/competition/admin");
            } else {
                response.sendRedirect(request.getContextPath() + "/competition/home");
            }
        } else {
            // 登录失败，显示错误信息
            request.setAttribute("errorMessage", "用户名或密码错误");
            request.getRequestDispatcher("/competition/login.jsp").forward(request, response);
        }
    }
}