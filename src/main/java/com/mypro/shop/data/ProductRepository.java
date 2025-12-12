package com.mypro.shop.data;

import com.mypro.shop.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模拟数据库类：存储商品信息
 */
public class ProductRepository {

    private static final Map<String, Product> PRODUCTS = new HashMap<>();
    private static final List<Product> PRODUCT_LIST = new ArrayList<>();

    static {
        // 初始化商品数据
        // 男装 (Men's Clothing)
        addProduct(new Product("M001", "商务休闲衬衫", "男装", new BigDecimal("199.00")));
        addProduct(new Product("M002", "修身牛仔裤", "男装", new BigDecimal("299.50")));

        // 女装 (Women's Clothing)
        addProduct(new Product("W001", "法式复古连衣裙", "女装", new BigDecimal("388.00")));
        addProduct(new Product("W002", "高腰阔腿裤", "女装", new BigDecimal("255.00")));

        // 童装 (Children's Clothing)
        addProduct(new Product("K001", "卡通印花T恤", "童装", new BigDecimal("89.90")));
        addProduct(new Product("K002", "防晒外套", "童装", new BigDecimal("129.00")));
    }

    private static void addProduct(Product product) {
        PRODUCTS.put(product.getId(), product);
        PRODUCT_LIST.add(product);
    }

    /**
     * 获取所有商品列表
     * @return 所有商品的List
     */
    public static List<Product> getAllProducts() {
        return new ArrayList<>(PRODUCT_LIST);
    }

    /**
     * 根据ID查找商品
     * @param id 商品ID
     * @return 找到的商品，如果未找到则为null
     */
    public static Product getProductById(String id) {
        return PRODUCTS.get(id);
    }
}
