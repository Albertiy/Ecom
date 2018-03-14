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
}
