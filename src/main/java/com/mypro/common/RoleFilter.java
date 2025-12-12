package com.mypro.common;

import com.mypro.competition.model.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * RoleFilter 过滤器：实现基于角色的访问控制
 * 此过滤器负责拦截所有映射到它的路径（例如 /admin/*），确保只有管理员角色（"admin"）才能访问。
 * 假定 web.xml 已经正确配置了映射 URL。
 */
public class RoleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // 获取会话和用户对象，我们只需要检查用户是否拥有 "admin" 角色。
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;

        // 检查用户是否已登录且角色是管理员
        if (user != null && "admin".equals(user.getRole())) {
            // 是管理员，放行到目标 Servlet 或下一个 Filter
            chain.doFilter(request, response);
        } else {
            // 非管理员或未登录，禁止访问

            // 记录日志或设置错误信息
            System.err.println("Access denied for user: " + (user != null ? user.getUsername() : "Unauthenticated") + ". Attempted URI: " + req.getRequestURI());

            // 1. 设置 HTTP 403 Forbidden 状态码
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);

            // 2. 将错误信息存入请求属性
            req.setAttribute("errorMessage", "权限不足，请使用管理员账号登录。");

            // 3. 重定向到登录页
            // 使用重定向，确保用户浏览器地址栏更新，并且强制跳转到登录页。
            res.sendRedirect(req.getContextPath() + "/competition/login.jsp");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("RoleFilter initialized.");
    }

    @Override
    public void destroy() {
        // 销毁时执行清理工作
        System.out.println("RoleFilter destroyed.");
    }
}