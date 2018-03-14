package com.ecom.pojo;

public class Product {
    /*create table IF NOT EXISTS product(
            pid varchar(50) primary key,
    sid int not null,	-- 默认店铺id
    pname varchar(20) not null,	-- 商品名称
    cid varchar(50) not null,	-- 种类
    shop_price float not null,	-- 价格
    pdesc varchar(255) not null, 	-- 商品简介
    pstorage int not null,	-- 库存 付款后库存减少， 退货后增加
    psold int not null default 0 , -- 已出售数量 付款后已卖出数量增加，退货后减少
    incompleteness_pturnover float not null default 0.0, -- 未到账的营业额， 付款后增加，订单完成后减少
    pturnover float not null default 0.0, -- 营业额，考虑价格变动，所以通过订单完成时进行计算写入. 退款后减少
    pimage varchar(255), -- 服务器中的图片路径
    pflag int not null default 0 -- 1-是否上架，0-默认未上架
    -- constraint fksid foreign key(sid) references store(sid) -- 商品依附于店铺
);*/
    private String pid;
    private int sid;
    private String pname;
    private String cid;
    private float shop_price;
    private String pdesc;
    private int pstorage;
    private int psold;
    private float incompleteness_pturnover;
    private float pturnover;
    private String pimage;
    private int pflag;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public float getShop_price() {
        return shop_price;
    }

    public void setShop_price(float shop_price) {
        this.shop_price = shop_price;
    }

    public String getPdesc() {
        return pdesc;
    }

    public void setPdesc(String pdesc) {
        this.pdesc = pdesc;
    }

    public int getPstorage() {
        return pstorage;
    }

    public void setPstorage(int pstorage) {
        this.pstorage = pstorage;
    }

    public int getPsold() {
        return psold;
    }

    public void setPsold(int psold) {
        this.psold = psold;
    }

    public float getIncompleteness_pturnover() {
        return incompleteness_pturnover;
    }

    public void setIncompleteness_pturnover(float incompleteness_pturnover) {
        this.incompleteness_pturnover = incompleteness_pturnover;
    }

    public float getPturnover() {
        return pturnover;
    }

    public void setPturnover(float pturnover) {
        this.pturnover = pturnover;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }

    public int getPflag() {
        return pflag;
    }

    public void setPflag(int pflag) {
        this.pflag = pflag;
    }
}
