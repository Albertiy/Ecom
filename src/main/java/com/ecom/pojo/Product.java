package com.ecom.pojo;

public class Product {
    /*create table IF NOT EXISTS product(
    pid varchar(50) not null primary key,
    sid varchar(50) not null,	-- 默认店铺id
    pname varchar(20) not null,	-- 商品名称
    cid varchar(50) not null,	-- 种类
    price float not null,	-- 价格
    pdesc varchar(255) not null, 	-- 商品简介
    pdate datetime default null, -- 添加日期
    pstorage int not null,	-- 库存 付款后库存减少， 退货后增加
    psold int not null default 0 , -- 已出售数量 付款后已卖出数量增加，退货后减少
    unpturnover float not null default 0.0, -- 未到账的营业额， 付款后增加，订单完成后减少
    pturnover float not null default 0.0, -- 营业额，考虑价格变动，所以通过订单完成时进行计算写入. 退款后减少
    pimage varchar(255), -- 服务器中的图片路径
    state varchar(10) not null default 0, -- 是否上架，默认未上架
    is_hot int(11) default 1, -- 1-热门
    constraint pfksid foreign key(sid) references store(sid), -- 商品依附于店铺
    constraint pfkcid foreign key(cid) references category(cid)
            );*/

    private String pid;
    private String sid;
    private String pname;
    private String cid;
    private float price;
    private String pdesc;
    private String pdate;
    private int pstorage;
    private int psold;
    private float unpturnover;
    private float pturnover;
    private String pimage;
    private String state;
    private int is_hot;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPdesc() {
        return pdesc;
    }

    public void setPdesc(String pdesc) {
        this.pdesc = pdesc;
    }

    public String getPdate() {
        return pdate;
    }

    public void setPdate(String pdate) {
        this.pdate = pdate;
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

    public float getUnpturnover() {
        return unpturnover;
    }

    public void setUnpturnover(float unpturnover) {
        this.unpturnover = unpturnover;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(int is_hot) {
        this.is_hot = is_hot;
    }
}
