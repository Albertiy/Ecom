package com.ecom.controller;

import com.ecom.dao.CategoryDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class HelloController {

    private String message="O_J_B_K";


    //@Autowired
    private CategoryDao categoryDao=new CategoryDao();

    @RequestMapping(value = "/variety",method = RequestMethod.GET)
    public String varietyInput(){
        return "varietyinput";
    }

    @RequestMapping("/varietylist")
    public String varietyList(Map<String,Object> map){
        map.put("varieties",categoryDao.getCategoryies());
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


    @RequestMapping("/product")
    public String product(@RequestParam(value="method",required = false,defaultValue = "index")String method){
        if(method.equals("index"))
            return "index";
        return "index";
    }


}
