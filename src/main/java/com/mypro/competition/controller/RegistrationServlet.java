package com.mypro.competition.controller;

import com.mypro.competition.data.CompetitionRepository;
import com.mypro.competition.data.UserRepository;
import com.mypro.competition.model.Registration;
import com.mypro.competition.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Date;

/**
 * RegistrationServlet 处理两种注册功能：
 * 1. 用户注册（学生账户注册）
 * 2. 竞赛报名（学生报名参加竞赛）
 */
public class RegistrationServlet extends HttpServlet {

    private final UserRepository userRepository = new UserRepository();
    private final CompetitionRepository competitionRepository = new CompetitionRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 处理用户注册页面请求
        request.getRequestDispatcher("/competition/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 根据请求参数判断是用户注册还是竞赛报名
        String compId = request.getParameter("compId");

        if (compId != null) {
            // 竞赛报名处理
            handleCompetitionRegistration(request, response);
        } else {
            // 用户注册处理
            handleUserRegistration(request, response);
        }
    }

    /**
     * 处理用户注册请求
     */
    private void handleUserRegistration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");

        // 简单验证参数
        if (username == null || password == null || name == null ||
                username.isEmpty() || password.isEmpty() || name.isEmpty()) {
            request.setAttribute("errorMessage", "所有字段都必须填写");
            request.getRequestDispatcher("/competition/register.jsp").forward(request, response);
            return;
        }

        // 创建用户对象
        User user = new User(0, username, password, name, "student", null, null);

        // 保存用户
        if (userRepository.save(user)) {
            request.setAttribute("successMessage", "注册成功，请登录");
            request.getRequestDispatcher("/competition/login.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "注册失败，请重试");
            request.getRequestDispatcher("/competition/register.jsp").forward(request, response);
        }
    }

    /**
     * 处理竞赛报名请求
     */
    private void handleCompetitionRegistration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取当前登录用户
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/competition/login");
            return;
        }

        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/competition/login");
            return;
        }

        // 获取请求参数
        int competitionId = Integer.parseInt(request.getParameter("compId"));
        String formType = request.getParameter("formType");
        String teamName = request.getParameter("teamName");
        String contactEmail = request.getParameter("contactEmail");

        // 创建报名记录
        Registration registration = new Registration(
                0,
                currentUser.getId(),
                competitionId,
                new Date(),
                "Pending", // 默认待审核状态
                formType.contains("团体") ? teamName : currentUser.getName()
        );

        // 保存报名记录
        if (competitionRepository.saveRegistration(registration)) {
            session.setAttribute("message", "竞赛报名成功！");
            response.sendRedirect(request.getContextPath() + "/competition/home");
        } else {
            request.setAttribute("errorMessage", "竞赛报名失败，请重试");
            request.getRequestDispatcher("/competition/registration_form.jsp").forward(request, response);
        }
    }
}