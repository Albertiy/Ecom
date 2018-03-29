package com.ecom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BearViewController {
    @RequestMapping("/login")
    public String getLogin() {
        return "login";
    }

    @RequestMapping("/index")
    public String getIndex() {
        return "index";
    }

    @RequestMapping("/header")
    public String getHeader() {
        return "header";
    }

    @RequestMapping("/register")
    public String getRegister() {
        return "register";
    }

    @RequestMapping("/footer")
    public String getFooter() {
        return "footer";
    }

    @RequestMapping("/registerSuccess")
    public String registerSuccess() {
        return "registerSuccess";
    }

    @RequestMapping("/registerFail")
    public String registerFail() {
        return "registerFail";
    }

    @RequestMapping("/product_list")
    public String product_list() {
        return "product_list";
    }

    @RequestMapping("/product_info")
    public String product_info() {
        return "product_info";
    }

    @RequestMapping("/cart")
    public String cart() {
        return "cart";
    }

    @RequestMapping("/user_info")
    public String user_info() {
        return "user_info";
    }


    @RequestMapping("/user_info_change")
    public String user_info_change() {
        return "user_info_change";
    }

    @RequestMapping("/user_pwd_change")
    public String user_pwd_change() {
        return "user_pwd_change";
    }

    @RequestMapping("/order_info")
    public String order_info() {
        return "order_info";
    }
}
