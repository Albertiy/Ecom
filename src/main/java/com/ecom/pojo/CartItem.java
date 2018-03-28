package com.ecom.pojo;

import java.util.HashMap;
import java.util.Map;

public class CartItem {
    //    该购物车中一个店铺的所有商品
    private Map<String, ProductItem> productItems = new HashMap<>();


    //    一个店铺的商品总计
    private float cartitem_total;

    public Map<String, ProductItem> getProductItems() {
        return productItems;
    }

    public void setProductItems(Map<String, ProductItem> productItems) {
        this.productItems = productItems;
    }

    public float getCartitem_total() {
        return cartitem_total;
    }

    public void setCartitem_total(float cartitem_total) {
        this.cartitem_total = cartitem_total;
    }
}
