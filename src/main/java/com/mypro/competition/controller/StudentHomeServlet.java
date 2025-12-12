package com.mypro.competition.controller;

import com.mypro.competition.data.CompetitionRepository;
import com.mypro.competition.model.Competition;
import com.mypro.competition.model.Registration;
import com.mypro.competition.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.List;

public class StudentHomeServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(StudentHomeServlet.class);
    private final CompetitionRepository competitionRepository = new CompetitionRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("开始处理学生主页GET请求");

        // 设置字符编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 检查用户是否登录
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            logger.info("用户未登录，重定向到登录页");
            response.sendRedirect(request.getContextPath() + "/competition/login");
            return;
        }

        User user = (User) session.getAttribute("currentUser");
        logger.info("当前用户: ID={}, 名称={}, 角色={}", user.getId(), user.getName(), user.getRole());

        // 获取所有可报名的竞赛列表
        logger.info("开始获取竞赛列表...");
        List<Competition> availableCompetitions = competitionRepository.findAllCompetitions();
        logger.info("获取到 {} 个竞赛", availableCompetitions.size());

        // 获取用户的报名记录
        List<Registration> myRegistrations = competitionRepository.findRegistrationsByUserId(user.getId());
        logger.info("用户有 {} 条报名记录", myRegistrations.size());
        
        // 将数据存储在请求属性中
        request.setAttribute("competitions", availableCompetitions);
        request.setAttribute("userName", user.getName());
        request.setAttribute("myRegistrations", myRegistrations);

        // 转发到学生主页视图
        logger.info("转发到学生主页视图");
        request.getRequestDispatcher("/competition/home.jsp").forward(request, response);

        logger.info("请求处理完成");
    }
}