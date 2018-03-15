package com.ecom.pojo;

import com.google.gson.annotations.SerializedName;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    public Express() {
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("新的快递单|创建时间："+df.format(day));
        eTime = df.format(day);
        eCompany = "自己联系";
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String geteTime() {
        return eTime;
    }

    public void seteTime(String eTime) {
        this.eTime = eTime;
    }

    public String geteAddress() {
        return eAddress;
    }

    public void seteAddress(String eAddress) {
        this.eAddress = eAddress;
    }

    public String getsAddress() {
        return sAddress;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress;
    }

    public String geteCompany() {
        return eCompany;
    }

    public void seteCompany(String eCompany) {
        this.eCompany = eCompany;
    }
}
