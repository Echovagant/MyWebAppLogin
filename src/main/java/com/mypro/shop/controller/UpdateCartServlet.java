package com.mypro.shop.controller;

import com.mypro.shop.model.ShoppingCart;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 更新或移除购物车商品的Servlet (映射到 /shop/updateCart)
 */
public class UpdateCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 设置编码以正确处理中文
        request.setCharacterEncoding("UTF-8");

        // 2. 获取Session
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/shop/viewCart");
            return;
        }

        // 3. 获取购物车对象
        ShoppingCart cart = (ShoppingCart) session.getAttribute("shoppingCart");
        if (cart == null) {
            response.sendRedirect(request.getContextPath() + "/shop/viewCart");
            return;
        }

        // 4. 获取操作参数
        String productId = request.getParameter("productId");
        String action = request.getParameter("action");
        String quantityStr = request.getParameter("quantity");

        if (productId == null || productId.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/shop/viewCart?error=no_product_id");
            return;
        }

        try {
            if ("update".equals(action) && quantityStr != null) {
                // 更新数量
                int newQuantity = Integer.parseInt(quantityStr);
                if (newQuantity < 0) newQuantity = 0; // 防止负数
                cart.updateQuantity(productId, newQuantity);
            } else if ("remove".equals(action)) {
                // 移除商品
                cart.removeItem(productId);
            }
        } catch (NumberFormatException e) {
            System.err.println("Quantity update error: " + e.getMessage());
        }

        // 5. 重定向回购物车页面
        response.sendRedirect(request.getContextPath() + "/shop/viewCart");
    }
}
