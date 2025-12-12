package com.mypro.competition.controller;

import com.mypro.competition.data.UserRepository;
import com.mypro.competition.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserRegistrationServlet extends HttpServlet {

    private final UserRepository userRepository = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 显示注册页面
        request.getRequestDispatcher("/competition/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置请求编码
        request.setCharacterEncoding("UTF-8");

        // 获取注册信息
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");

        // 创建用户对象
        User user = new User(0, username, password, name, "student", null, null);

        // 保存用户信息
        boolean success = userRepository.save(user);

        if (success) {
            // 注册成功，跳转到登录页面
            request.setAttribute("successMessage", "注册成功，请登录");
            request.getRequestDispatcher("/competition/login.jsp").forward(request, response);
        } else {
            // 注册失败，显示错误信息
            request.setAttribute("errorMessage", "注册失败，用户名可能已存在");
            request.getRequestDispatcher("/competition/register.jsp").forward(request, response);
        }
    }
}