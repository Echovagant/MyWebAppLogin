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

    // 如果需要管理员操作，如审核/拒绝报名，可以使用 POST 或其他 Servlet
}