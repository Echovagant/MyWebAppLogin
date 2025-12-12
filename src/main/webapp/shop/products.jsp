<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.mypro.shop.model.Product, jakarta.servlet.http.Cookie" %>
<%@ page import="com.mypro.shop.model.ShoppingCart" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>商品列表</title>
    <style>
        body { font-family: 'Arial', sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }
        .header { background-color: #4CAF50; color: white; padding: 15px; text-align: center; border-radius: 5px 5px 0 0; }
        .header a { color: white; text-decoration: none; margin: 0 10px; font-weight: bold; }
        .header a:hover { text-decoration: underline; }
        .product-grid { display: flex; flex-wrap: wrap; gap: 20px; justify-content: center; padding: 20px 0; }
        .product-card { background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); width: 300px; }
        .product-card h3 { color: #333; margin-top: 0; }
        .product-card p { color: #666; font-size: 0.9em; }
        .price { color: #E94057; font-size: 1.2em; font-weight: bold; margin: 10px 0; }
        .category { background-color: #ddd; padding: 3px 8px; border-radius: 3px; display: inline-block; font-size: 0.8em; margin-bottom: 10px; }
        .add-btn {
            background-color: #007bff; color: white; padding: 8px 15px; border: none; border-radius: 4px;
            cursor: pointer; text-decoration: none; display: inline-block; transition: background-color 0.3s;
        }
        .add-btn:hover { background-color: #0056b3; }
        .message { background-color: #d4edda; color: #155724; padding: 10px; border-radius: 5px; margin-bottom: 15px; text-align: center; }
        .cookie-info { margin-top: 20px; padding: 10px; border: 1px solid #ccc; background-color: #fff3cd; color: #856404; border-radius: 5px; text-align: center; }
    </style>
</head>
<body>
<div class="header">
    <h1>👕 购物中心 🛍️</h1>
    <!-- 显示购物车中的商品总数量，使用了Session -->
    <a href="<%= request.getContextPath() %>/shop/viewCart">查看购物车 (<%= (session.getAttribute("shoppingCart") != null) ? ((ShoppingCart)session.getAttribute("shoppingCart")).getTotalQuantity() : 0 %>)</a>
    <a href="<%= request.getContextPath() %>/index.jsp">返回主页</a>
</div>

<%
    // 消息提示处理
    String message = request.getParameter("message");
    if (message != null && !message.isEmpty()) {
        if (message.endsWith("_added")) {
            String productName = message.substring(0, message.length() - 6).replaceAll("_", " ");
%>
<div class="message">商品 **<%= productName %>** 已成功添加到购物车！</div>
<%
        }
    }

    // Cookie使用示例 (读取Cookie)
    String lastVisit = "从未访问";
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("last_shop_visit".equals(cookie.getName())) {
                try {
                    long lastTime = Long.parseLong(cookie.getValue());
                    lastVisit = new java.util.Date(lastTime).toString();
                } catch (NumberFormatException e) {
                    // 忽略错误
                }
                break;
            }
        }
    }
%>
<!-- 显示 Cookie 信息 -->
<div class="cookie-info">
    您上次访问购物中心的时间是 <%= lastVisit %>
</div>

<div class="product-grid">
    <%
        List<Product> products = (List<Product>) request.getAttribute("productList");
        if (products != null) {
            for (Product product : products) {
    %>
    <div class="product-card">
        <span class="category"><%= product.getCategory() %></span>
        <h3><%= product.getName() %></h3>
        <p>商品ID: <%= product.getId() %></p>
        <div class="price">¥ <%= product.getPrice() %></div>
        <a href="<%= request.getContextPath() %>/shop/addToCart?id=<%= product.getId() %>" class="add-btn">
            加入购物车
        </a>
    </div>
    <%
        }
    } else {
    %>
    <p style="text-align: center; width: 100%;">没有找到任何商品。</p>
    <%
        }
    %>
</div>
</body>
</html>
