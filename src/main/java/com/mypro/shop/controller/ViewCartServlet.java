package com.mypro.shop.controller;

import com.mypro.shop.model.ShoppingCart;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 查看购物车详情的Servlet (映射到 /shop/viewCart)
 */
public class ViewCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 获取Session
        HttpSession session = request.getSession(false); // 不创建新Session

        ShoppingCart cart = null;
        if (session != null) {
            // 2. 从Session中获取购物车 (Session的使用)
            cart = (ShoppingCart) session.getAttribute("shoppingCart");
        }

        // 3. 将购物车对象设置到request属性中，供JSP显示
        request.setAttribute("shoppingCart", cart);

        // 4. 转发到购物车详情JSP页面
        request.getRequestDispatcher("/shop/cart.jsp").forward(request, response);
    }
}
