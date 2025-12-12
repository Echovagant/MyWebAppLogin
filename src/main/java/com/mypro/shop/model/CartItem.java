package com.mypro.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 购物车项实体类：包含商品和购买数量
 */
public class CartItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    /**
     * 计算该购物项的总价
     * @return 总价 (BigDecimal)
     */
    public BigDecimal getTotalPrice() {
        if (product == null || product.getPrice() == null) {
            return BigDecimal.ZERO;
        }
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
