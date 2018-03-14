package com.ecom.pojo;

import com.google.gson.annotations.SerializedName;
import org.springframework.stereotype.Component;

@Component
public class Express {
    private int eid;            //运单id
    private int oid;            //订单id
    private int sid;            //店铺id
    @SerializedName("etime")
    private String eTime;       //发货时间
    @SerializedName("eaddress")
    private String eAddress;    //发货地址
    @SerializedName("saddress")
    private String sAddress;    //收货地址
    @SerializedName("ecompany")
    private String eCompany;    //快递公司
}
