package com.ecom.pojo;

public class User {

    private String uid; //用户id
    private String sid; //店铺id  是否开通店铺，默认是0
    private String ucode; //用户激活码
    private int state; //0表示用户未激活，默认是0
    private String phone; //手机号  unique
    private String l_pwd; //登陆密码
    private String p_pwd; //支付密码
    private String nickname; //昵称
    private String uname; //真实姓名
    private String gender; //性别
    private String birthday; //出生日期
    private String email; //邮箱登陆  unique

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getUcode() {
        return ucode;
    }

    public void setUcode(String ucode) {
        this.ucode = ucode;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getL_pwd() {
        return l_pwd;
    }

    public void setL_pwd(String l_pwd) {
        this.l_pwd = l_pwd;
    }

    public String getP_pwd() {
        return p_pwd;
    }

    public void setP_pwd(String p_pwd) {
        this.p_pwd = p_pwd;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
