package com.ecom.pojo;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    //    该购物车中存储的所有店铺
    private Map<String, CartItem> cartItems = new HashMap<>();


    //    商品总计
    private float total;
    public Cart(){
        total = 0.0f;
    }

    public Map<String, CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Map<String, CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
