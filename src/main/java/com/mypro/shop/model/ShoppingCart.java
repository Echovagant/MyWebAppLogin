package com.mypro.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Collection;

/**
 * 购物车逻辑类：使用LinkedHashMap存储购物车项，存入Session
 */
public class ShoppingCart implements Serializable {
    private static final long serialVersionUID = 1L;
    // 使用LinkedHashMap保持插入顺序
    private Map<String, CartItem> items = new LinkedHashMap<>();

    /**
     * 添加商品到购物车
     * @param product 要添加的商品
     */
    public void addItem(Product product) {
        // 默认添加数量为1
        addItem(product, 1);
    }

    /**
     * 添加商品到购物车，并指定数量
     * @param product 要添加的商品
     * @param quantity 添加的数量
     */
    public void addItem(Product product, int quantity) {
        if (product == null || quantity <= 0) {
            return;
        }

        String productId = product.getId();
        if (items.containsKey(productId)) {
            // 如果商品已存在，则更新数量
            CartItem existingItem = items.get(productId);
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            // 否则，添加新的购物车项
            CartItem newItem = new CartItem(product, quantity);
            items.put(productId, newItem);
        }
    }

    /**
     * 更新购物车中指定商品的数量
     * @param productId 商品ID
     * @param newQuantity 新的数量
     */
    public void updateQuantity(String productId, int newQuantity) {
        if (items.containsKey(productId)) {
            if (newQuantity <= 0) {
                // 如果数量小于等于0，则移除该项
                removeItem(productId);
            } else {
                items.get(productId).setQuantity(newQuantity);
            }
        }
    }

    /**
     * 从购物车中移除商品
     * @param productId 要移除的商品ID
     */
    public void removeItem(String productId) {
        items.remove(productId);
    }

    /**
     * 获取购物车中的所有商品项
     * @return 购物车项集合
     */
    public Collection<CartItem> getItems() {
        return items.values();
    }

    /**
     * 获取购物车中的总商品项数
     * @return 总项数
     */
    public int getItemCount() {
        return items.size();
    }

    /**
     * 获取购物车中的所有商品的数量总和
     * @return 总数量
     */
    public int getTotalQuantity() {
        return items.values().stream().mapToInt(CartItem::getQuantity).sum();
    }

    /**
     * 计算购物车中所有商品的总价
     * @return 总价 (BigDecimal)
     */
    public BigDecimal getTotalPrice() {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : items.values()) {
            total = total.add(item.getTotalPrice());
        }
        return total;
    }

    /**
     * 清空购物车
     */
    public void clear() {
        items.clear();
    }
}
