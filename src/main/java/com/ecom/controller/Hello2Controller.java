package com.ecom.controller;

import com.ecom.dao.VarietyDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class Hello2Controller {

    @RequestMapping("/testorder")
    public String testOrder(){
        System.out.println("test order");
        return "test_order";
    }

    @RequestMapping("/testbootstrap")
    public String testBootstrap(){
        System.out.println("test bootstrap");
        return "test_bootstrap";
    }

    @RequestMapping("/order_header")
    public String orderHeader(){
        return "order_header";
    }

    @RequestMapping("/getjson")
    public String getjson(){return "getjson";}
}
