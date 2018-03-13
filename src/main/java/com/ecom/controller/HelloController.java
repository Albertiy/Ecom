package com.ecom.controller;

import com.ecom.dao.VarietyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import java.util.Map;

@Controller
public class HelloController {

    private String message="O_J_B_K";


    //@Autowired
    private VarietyDao varietyDao=new VarietyDao();

    @RequestMapping(value = "/variety",method = RequestMethod.GET)
    public String varietyInput(){
        return "varietyinput";
    }

    @RequestMapping("/varietylist")
    public String varietyList(Map<String,Object> map){
        map.put("varieties",varietyDao.getVarieties());
        return "varietylist";
    }

    @RequestMapping("/hello")
    public ModelAndView showMesssage(@RequestParam(value = "name",required = false,defaultValue = "Spring")String name){
        ModelAndView mv=new ModelAndView("helloworld");//指定视图
        //ModelAndView mv2=new ModelAndView("helloworld","message","Hello world!");//指定视图
        //向视图中添加所要展示或使用的内容，将在页面中使用
        mv.addObject("message",message);
        mv.addObject("name",name);
        return mv;
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/product")
    public String product(@RequestParam(value="method",required = false,defaultValue = "index")String method){
        if(method.equals("index"))
            return "index";
        return "index";
    }

    @RequestMapping("/header")
    public String getHeader(){
        return "header";
    }

    @RequestMapping("/footer")
    public String getFooter(){
        return "footer";
    }
}
