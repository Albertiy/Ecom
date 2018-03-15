package com.ecom.pojo;

import com.google.gson.annotations.SerializedName;
import org.springframework.stereotype.Component;

@Component
public class OrderData {
    private int pid;
    private int pcount;
    @SerializedName("shop_price")
    private int shopPrice;
    @SerializedName("sub_total")
    private int subTotal;
    private boolean returned;
    private boolean judged;

    public OrderData() {
        pcount = 1;
        shopPrice = 1;
        subTotal = 1;
        returned = false;
        judged = false;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getPcount() {
        return pcount;
    }

    public void setPcount(int pcount) {
        this.pcount = pcount;
    }

    public int getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(int shopPrice) {
        this.shopPrice = shopPrice;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public boolean isJudged() {
        return judged;
    }

    public void setJudged(boolean judged) {
        this.judged = judged;
    }
}
