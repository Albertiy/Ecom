package com.ecom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GustPigController {
    @RequestMapping("/test_GustPig")
    public String myStore(){
        return "test_GustPig";
    }
}
