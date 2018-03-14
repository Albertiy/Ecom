package com.ecom.pojo;

import com.google.gson.annotations.SerializedName;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 订单对象：_order表
 * 注意：由于JS与JSON默认对象名为下划线格式，而Java习惯驼峰命名法，因此
 * 使用gson的@SerializedName标注属性名
 */
@Component
public class Order {
    private int oid;            //订单id：11位
    private int sid;            //店铺id：11位
    private int uid;            //买家id：11位
    @SerializedName("create_time")
    private String createTime;  //下单时间
    @SerializedName("pay_time")
    private String payTime;     //支付时间
    @SerializedName("delivery_time")
    private String deliveryTime;//发货时间
    @SerializedName("finish_time")
    private String finishTime;  //完成时间
    private float amount;       //实付金额
    private String consignee;   //收件人姓名：16位
    private String phone;       //收件人手机号：11位
    private String address;     //收件人地址：255位
    private String state;       //订单状态：1位，默认"0"
    private String ps;          //备注：20位
    private String odata;       //订单内容：JSON格式

    public Order() {
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("新的订单|创建时间："+df.format(day));

        createTime = df.format(day);
        amount = 0;
        state = "0";
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

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    public String getOdata() {
        return odata;
    }

    public void setOdata(String odata) {
        this.odata = odata;
    }
}
