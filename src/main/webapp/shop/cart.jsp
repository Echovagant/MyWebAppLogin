<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mypro.shop.model.ShoppingCart, com.mypro.shop.model.CartItem, java.util.Collection, java.math.BigDecimal" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>购物车详情</title>
    <style>
        body { font-family: 'Arial', sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }
        .header { background-color: #FF5722; color: white; padding: 15px; text-align: center; border-radius: 5px 5px 0 0; }
        .header a { color: white; text-decoration: none; margin: 0 10px; font-weight: bold; }
        .header a:hover { text-decoration: underline; }
        .container { max-width: 900px; margin: 20px auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }
        h2 { color: #333; border-bottom: 2px solid #ccc; padding-bottom: 10px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #f2f2f2; color: #333; }
        .total-row td { font-weight: bold; border-top: 2px solid #FF5722; font-size: 1.2em; color: #FF5722; }
        .action-form { display: flex; align-items: center; }
        .action-form input[type="number"] { width: 60px; padding: 5px; border: 1px solid #ccc; border-radius: 3px; margin-right: 10px; }
        .update-btn, .remove-btn {
            padding: 6px 10px; border: none; border-radius: 4px; cursor: pointer; transition: background-color 0.3s;
            color: white; font-weight: bold;
        }
        .update-btn { background-color: #2196F3; margin-right: 5px; }
        .update-btn:hover { background-color: #1976D2; }
        .remove-btn { background-color: #F44336; }
        .remove-btn:hover { background-color: #D32F2F; }
    </style>
</head>
<body>
<div class="header">
    <h1>🛒 您的购物车 🛍️</h1>
    <a href="<%= request.getContextPath() %>/shop/products">继续购物</a>
    <a href="<%= request.getContextPath() %>/index.jsp">返回主页</a>
</div>

<div class="container">
    <%
        // 1. 获取购物车对象 (Session的使用)
        ShoppingCart cart = (ShoppingCart) request.getAttribute("shoppingCart");

        if (cart == null || cart.getItemCount() == 0) {
    %>
    <p style="text-align: center; padding: 50px;">您的购物车是空的！快去 <a href="<%= request.getContextPath() %>/shop/products">添加商品</a> 吧！</p>
    <%
    } else {
        Collection<CartItem> items = cart.getItems();
        BigDecimal totalAmount = cart.getTotalPrice();
    %>
    <h2>购物车列表 (<%= cart.getTotalQuantity() %> 件商品)</h2>
    <table>
        <thead>
        <tr>
            <th>商品ID</th>
            <th>商品名称</th>
            <th>单价 (¥)</th>
            <th>数量</th>
            <th>小计 (¥)</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (CartItem item : items) {
                String productId = item.getProduct().getId();
        %>
        <tr>
            <td><%= productId %></td>
            <td><%= item.getProduct().getName() %></td>
            <td><%= item.getProduct().getPrice() %></td>
            <td>
                <!-- 修改数量表单 -->
                <form action="<%= request.getContextPath() %>/shop/updateCart" method="post" class="action-form">
                    <input type="hidden" name="productId" value="<%= productId %>">
                    <input type="hidden" name="action" value="update">
                    <!-- 数量输入框 -->
                    <label>
                        <input type="number" name="quantity" value="<%= item.getQuantity() %>" min="1" required>
                    </label>
                    <input type="submit" value="更新" class="update-btn">
                </form>
            </td>
            <td><%= item.getTotalPrice() %></td>
            <td>
                <!-- 移除商品表单 -->
                <form action="<%= request.getContextPath() %>/shop/updateCart" method="post" style="display: inline;">
                    <input type="hidden" name="productId" value="<%= productId %>">
                    <input type="hidden" name="action" value="remove">
                    <input type="submit" value="移除" class="remove-btn">
                </form>
            </td>
        </tr>
        <%
            }
        %>
        <tr class="total-row">
            <td colspan="4" style="text-align: right;">总计金额:</td>
            <td>¥ <%= totalAmount %></td>
            <td></td>
        </tr>
        </tbody>
    </table>
    <%
        }
    %>
</div>
</body>
</html>
