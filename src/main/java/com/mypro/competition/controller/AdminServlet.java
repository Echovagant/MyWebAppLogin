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
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
                case "viewCompetitions":
                    viewCompetitions(request, response);
                    return;
                case "addCompetition":
                    showAddCompetitionForm(request, response);
                    return;
                case "editCompetition":
                    showEditCompetitionForm(request, response);
                    return;
                case "deleteCompetition":
                    handleDeleteCompetition(request, response);
                    return;
                default:
                    // 默认处理，加载仪表板
            }
        }
        
        loadDashboard(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action != null) {
            switch (action) {
                case "saveCompetition":
                    handleSaveCompetition(request, response);
                    return;
                case "updateCompetition":
                    handleUpdateCompetition(request, response);
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
    
    /**
     * 查看所有竞赛信息
     */
    private void viewCompetitions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Competition> competitions = competitionRepository.findAllCompetitions();
        request.setAttribute("competitions", competitions);
        request.getRequestDispatcher("/competition/admin_competitions.jsp").forward(request, response);
    }
    
    /**
     * 显示添加竞赛表单
     */
    private void showAddCompetitionForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/competition/admin_add_competition.jsp").forward(request, response);
    }
    
    /**
     * 显示编辑竞赛表单
     */
    private void showEditCompetitionForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int competitionId = Integer.parseInt(request.getParameter("id"));
            Competition competition = competitionRepository.findCompetitionById(competitionId);
            
            if (competition != null) {
                request.setAttribute("competition", competition);
                request.getRequestDispatcher("/competition/admin_edit_competition.jsp").forward(request, response);
            } else {
                // 竞赛不存在，重定向到竞赛列表
                response.sendRedirect(request.getContextPath() + "/competition/admin?action=viewCompetitions");
            }
        } catch (NumberFormatException e) {
            // ID 格式错误，重定向到竞赛列表
            response.sendRedirect(request.getContextPath() + "/competition/admin?action=viewCompetitions");
        }
    }
    
    /**
     * 处理保存新竞赛
     */
    private void handleSaveCompetition(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 设置请求编码
        request.setCharacterEncoding("UTF-8");
        
        // 获取表单参数
        String name = request.getParameter("name");
        String level = request.getParameter("level");
        String formType = request.getParameter("formType");
        String type = request.getParameter("type");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        
        // 解析日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        
        try {
            startDate = dateFormat.parse(startDateStr);
            endDate = dateFormat.parse(endDateStr);
        } catch (ParseException e) {
            request.setAttribute("error", "日期格式错误，请使用YYYY-MM-DD格式");
            request.getRequestDispatcher("/competition/admin_add_competition.jsp").forward(request, response);
            return;
        }
        
        // 创建竞赛对象
        Competition competition = new Competition();
        competition.setName(name);
        competition.setLevel(level);
        competition.setFormType(formType);
        competition.setType(type);
        competition.setStartDate(startDate);
        competition.setEndDate(endDate);
        
        // 保存竞赛
        boolean success = competitionRepository.saveCompetition(competition);
        
        if (success) {
            response.sendRedirect(request.getContextPath() + "/competition/admin?action=viewCompetitions");
        } else {
            request.setAttribute("error", "保存竞赛失败，请重试");
            request.getRequestDispatcher("/competition/admin_add_competition.jsp").forward(request, response);
        }
    }
    
    /**
     * 处理更新竞赛信息
     */
    private void handleUpdateCompetition(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 设置请求编码
        request.setCharacterEncoding("UTF-8");
        
        // 获取表单参数
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String level = request.getParameter("level");
        String formType = request.getParameter("formType");
        String type = request.getParameter("type");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        
        // 解析日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        
        try {
            startDate = dateFormat.parse(startDateStr);
            endDate = dateFormat.parse(endDateStr);
        } catch (ParseException e) {
            request.setAttribute("error", "日期格式错误，请使用YYYY-MM-DD格式");
            Competition competition = competitionRepository.findCompetitionById(id);
            request.setAttribute("competition", competition);
            request.getRequestDispatcher("/competition/admin_edit_competition.jsp").forward(request, response);
            return;
        }
        
        // 创建竞赛对象
        Competition competition = new Competition();
        competition.setId(id);
        competition.setName(name);
        competition.setLevel(level);
        competition.setFormType(formType);
        competition.setType(type);
        competition.setStartDate(startDate);
        competition.setEndDate(endDate);
        
        // 更新竞赛
        boolean success = competitionRepository.updateCompetition(competition);
        
        if (success) {
            response.sendRedirect(request.getContextPath() + "/competition/admin?action=viewCompetitions");
        } else {
            request.setAttribute("error", "更新竞赛失败，请重试");
            request.setAttribute("competition", competition);
            request.getRequestDispatcher("/competition/admin_edit_competition.jsp").forward(request, response);
        }
    }
    
    /**
     * 处理删除竞赛
     */
    private void handleDeleteCompetition(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int competitionId = Integer.parseInt(request.getParameter("id"));
            boolean success = competitionRepository.deleteCompetition(competitionId);
            
            // 删除后重定向到竞赛列表
            response.sendRedirect(request.getContextPath() + "/competition/admin?action=viewCompetitions");
        } catch (NumberFormatException e) {
            // ID 格式错误，重定向到竞赛列表
            response.sendRedirect(request.getContextPath() + "/competition/admin?action=viewCompetitions");
        }
    }
}