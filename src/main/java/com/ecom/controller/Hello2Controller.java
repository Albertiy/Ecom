package com.ecom.controller;

import com.ecom.dao.VarietyDao;
import com.ecom.pojo.Order;
import com.ecom.pojo.OrderPageBean;
import com.ecom.service.OrderService;
import com.google.gson.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class Hello2Controller {

    //获取Gson的Bean
    static ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext1.xml");
    private Gson gson = (Gson)context.getBean("gson");

    @Resource(name="orderService")
    private OrderService orderService;

    @RequestMapping("/testorder")
    public String testOrder(){
        System.out.println("【/testorder】");
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

    /*
    * 这个是测试用例
    * */
    @RequestMapping("/getjson")
    public String getjson(){return "getjson";}

    /*
    * 这个是真的
    * SpringMVC 的 @ResponseBody 标注了该函数返回的内容直接作为Response，不再加载页面
    * */
    @RequestMapping("/getOrderList")
    @ResponseBody
    public String getOrderList(
            @RequestParam(value = "search",defaultValue = "") String search,
            @RequestParam(value = "sort",defaultValue = "") String sort,
            @RequestParam(value = "order",defaultValue = "asc") String order,
            @RequestParam(value = "offset",defaultValue = "0") int offset,
            @RequestParam(value = "limit",defaultValue = "20") int limit,
            @RequestParam(value = "other",defaultValue = "") String other   //other暂时用不到
    ){
        OrderPageBean<Order> orderPageBean = new OrderPageBean<Order>(search,sort,order,offset,limit);
        orderPageBean = orderService.findUnFilledOrdersBySid(1,orderPageBean);
        String orderJson = gson.toJson(orderPageBean.getList());
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("total",new JsonPrimitive(orderPageBean.getTotal()));
        jsonObject.add("rows",new JsonParser().parse(orderJson).getAsJsonArray());
        System.out.println(jsonObject.toString());
        return jsonObject.toString();
    }
}
