package com.mypro.shop.controller;

import com.mypro.shop.data.ProductRepository;
import com.mypro.shop.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.util.List;

/**
 * 显示商品列表的Servlet (映射到 /shop/products)
 */
public class ShowProductsServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 获取商品列表数据
        List<Product> products = ProductRepository.getAllProducts();

        // 2. 将商品列表设置到request属性中，供JSP显示
        request.setAttribute("productList", products);

        // 3. 示例：使用Cookie来追踪用户访问（满足实验要求）
        // 存储访问时间戳到名为 'last_shop_visit' 的 Cookie 中
        Cookie lastVisitCookie = new Cookie("last_shop_visit", String.valueOf(System.currentTimeMillis()));
        lastVisitCookie.setMaxAge(60 * 60 * 24 * 30); // 30天有效期
        // 将Cookie路径设置为 /shop，使其作用域限制在购物车模块
        lastVisitCookie.setPath(request.getContextPath() + "/shop");
        response.addCookie(lastVisitCookie);

        // 4. 转发到商品显示JSP页面
        request.getRequestDispatcher("/shop/products.jsp").forward(request, response);
    }
}
