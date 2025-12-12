package com.mypro.competition.controller;

import com.mypro.competition.data.CompetitionRepository;
import com.mypro.competition.model.Registration;
import com.mypro.competition.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * 管理员查询控制 (AdminServlet)
 * 负责加载管理员仪表板所需的数据，主要是所有竞赛报名记录。
 * 只能由 'admin' 角色访问 (依赖 RoleFilter)。
 */
public class AdminServlet extends HttpServlet {

    private final CompetitionRepository competitionRepository = new CompetitionRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action != null) {
            switch (action) {
                case "view":
                    handleViewRegistration(request, response);
                    return;
                case "delete":
                    handleDeleteRegistration(request, response);
                    return;
                default:
                    // 默认处理，加载仪表板
            }
        }
        
        loadDashboard(request, response);
    }
    
    /**
     * 加载管理员仪表板
     */
    private void loadDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;

        if (user == null || !"admin".equals(user.getRole())) {
            // 理论上 RoleFilter 会处理，但这里进行安全检查
            response.sendRedirect(request.getContextPath() + "/competition/login");
            return;
        }

        // 1. 查询所有报名详情 (使用修改后的方法)
        List<Registration> registrations = competitionRepository.findAllRegistrations();

        // 2. 将数据存储在请求属性中 (使用与JSP期望匹配的属性名)
        request.setAttribute("registrations", registrations);
        request.setAttribute("adminName", user.getName());

        // 3. 转发到管理员仪表板视图
        request.getRequestDispatcher("/competition/admin_dashboard.jsp").forward(request, response);
    }

    /**
     * 处理查看报名记录详情
     */
    private void handleViewRegistration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int registrationId = Integer.parseInt(request.getParameter("id"));
            Registration registration = competitionRepository.findRegistrationById(registrationId);
            
            if (registration != null) {
                request.setAttribute("registration", registration);
                request.getRequestDispatcher("/competition/registration_detail.jsp").forward(request, response);
            } else {
                // 记录不存在，重定向到仪表板
                response.sendRedirect(request.getContextPath() + "/competition/admin");
            }
        } catch (NumberFormatException e) {
            // ID 格式错误，重定向到仪表板
            response.sendRedirect(request.getContextPath() + "/competition/admin");
        }
    }

    /**
     * 处理删除报名记录
     */
    private void handleDeleteRegistration(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int registrationId = Integer.parseInt(request.getParameter("id"));
            boolean success = competitionRepository.deleteRegistration(registrationId);
            
            // 删除后重定向到仪表板
            response.sendRedirect(request.getContextPath() + "/competition/admin");
        } catch (NumberFormatException e) {
            // ID 格式错误，重定向到仪表板
            response.sendRedirect(request.getContextPath() + "/competition/admin");
        }
    }
}