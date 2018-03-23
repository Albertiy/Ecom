package com.ecom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BearViewController {
    @RequestMapping("/login")
    public String getLogin() { return "login"; }

    @RequestMapping("/header")
    public String getHeader(){
        return "header";
    }

    @RequestMapping("/register")
    public String getRegister(){
        return "register";
    }

    @RequestMapping("/footer")
    public String getFooter(){
        return "footer";
    }

    @RequestMapping("/registerSuccess")
    public String registerSuccess(){
        return "registerSuccess";
    }

    @RequestMapping("/registerFail")
    public String registerFail(){
        return "registerFail";
    }

}
