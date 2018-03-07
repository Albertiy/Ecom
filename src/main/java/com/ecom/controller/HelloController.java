package com.ecom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

    private String message="O_J_B_K";

    @RequestMapping("/hello")
    public ModelAndView showMesssage(@RequestParam(value = "name",required = false,defaultValue = "Spring")String name){
        ModelAndView mv=new ModelAndView("helloworld");//指定视图
        //ModelAndView mv2=new ModelAndView("helloworld","message","Hello world!");//指定视图
        //向视图中添加所要展示或使用的内容，将在页面中使用
        mv.addObject("message",message);
        mv.addObject("name",name);
        return mv;
    }
}
