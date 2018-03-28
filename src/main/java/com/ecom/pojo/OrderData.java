package com.ecom.pojo;

import com.google.gson.annotations.SerializedName;
import org.springframework.stereotype.Component;

/**
 * OrderData类 对应 omdb._order 的 odata 列
 */

@Component
public class OrderData {
    private String pid;
    private String pname;//为了使读取时更方便的显示商品名称，并不在表中
    private int pcount;
    @SerializedName("shop_price")
    private float shopPrice;
    @SerializedName("sub_total")
    private float subTotal;
    private boolean returned;
    private boolean judged;

    public OrderData() {
        pname = "";
        pcount = 1;
        shopPrice = 1.00f;
        subTotal = 1.00f;
        returned = false;
        judged = false;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public float getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(float shopPrice) {
        this.shopPrice = shopPrice;
    }

    public float getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(float subTotal) {
        this.subTotal = subTotal;
    }

    public int getPcount() {
        return pcount;
    }

    public void setPcount(int pcount) {
        this.pcount = pcount;
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

    @Override
    public String toString() {
        return "OrderData{" +
                "pid='" + pid + '\'' +
                ", pname='" + pname + '\'' +
                ", pcount=" + pcount +
                ", shopPrice=" + shopPrice +
                ", subTotal=" + subTotal +
                ", returned=" + returned +
                ", judged=" + judged +
                '}';
    }
}
