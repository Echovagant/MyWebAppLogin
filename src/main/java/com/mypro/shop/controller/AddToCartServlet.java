package com.mypro.shop.controller;

import com.mypro.shop.data.ProductRepository;
import com.mypro.shop.model.Product;
import com.mypro.shop.model.ShoppingCart;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serial;
import java.net.URLEncoder; // 引入 URL 编码工具
import java.nio.charset.StandardCharsets;

/**
 * 添加商品到购物车的Servlet (映射到 /shop/addToCart)
 */
public class AddToCartServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 获取请求参数：商品ID
        String productId = request.getParameter("id");
        if (productId == null || productId.isEmpty()) {
            // 如果没有ID，重定向回商品列表
            response.sendRedirect(request.getContextPath() + "/shop/products");
            return;
        }

        // 2. 查找商品
        Product product = ProductRepository.getProductById(productId);
        if (product == null) {
            response.sendRedirect(request.getContextPath() + "/shop/products?error=product_not_found");
            return;
        }

        // 3. 获取或创建Session
        HttpSession session = request.getSession(true);

        // 4. 获取购物车对象 (Session的使用)
        ShoppingCart cart = (ShoppingCart) session.getAttribute("shoppingCart");

        if (cart == null) {
            // Session中没有购物车，创建一个新的并存入Session
            cart = new ShoppingCart();
            session.setAttribute("shoppingCart", cart);
        }

        // 5. 添加商品到购物车
        cart.addItem(product);

        // 6. 重定向到购物车详情，并传递成功消息 (已编码)
        // 修正逻辑：直接跳转到购物车详情页面，并对商品名进行URL编码
        String encodedName = URLEncoder.encode(product.getName(), StandardCharsets.UTF_8);
        response.sendRedirect(request.getContextPath() + "/shop/viewCart?message=" + encodedName + "_added");
    }
}
