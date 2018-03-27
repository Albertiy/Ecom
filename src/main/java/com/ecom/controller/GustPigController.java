package com.ecom.controller;

import com.ecom.pojo.Express;
import com.ecom.pojo.Order;
import com.ecom.pojo.OrderData;
import com.ecom.pojo.OrderPageBean;
import com.ecom.service.ExpressService;
import com.ecom.service.OrderService;
import com.google.gson.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class GustPigController {
    //获取Gson的Bean
    public static ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext1.xml");
    private Gson gson = (Gson)context.getBean("gson");

    @Resource(name="orderService")
    private OrderService orderService;

    @RequestMapping("/test_GustPig")
    public String test_GustPig(){
        return "test_GustPig";
    }

    @RequestMapping("/delivered_orders")
    public String delivered_orders(){
        return "delivered_orders";
    }

    @RequestMapping("/orders_done")
    public String orders_done(){
            return "orders_done";
        }

    @RequestMapping("/expressmodal")
    public String expressmodal(){
        return  "expressmodal";
    }

    @RequestMapping("/findExpress")
    public void fineExpress(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入Controller");
        String sid = request.getParameter("sid");
        String oid = request.getParameter("oid");
        System.out.println(oid+"   "+sid);
        Express express = new Express();
        ExpressService service = new ExpressService();
        //调用service查询物流信息
        express = service.findExpressInfo(sid,oid);
        System.out.println("Controller执行成功，准备向expressmodal.jsp传参");
        request.setAttribute("express", express);
        request.getRequestDispatcher("/expressmodal").forward(request, response);
        System.out.println("传参成功");
    }

    /*
     * 这个是真的
     * SpringMVC 的 @ResponseBody 标注了该函数返回的内容直接作为Response，不再加载页面
     * */
    @RequestMapping("/getOrderList2")
    @ResponseBody
    public String getOrderList2(
            @RequestParam(value = "search",defaultValue = "") String search,
            @RequestParam(value = "sort",defaultValue = "") String sort,
            @RequestParam(value = "order",defaultValue = "asc") String order,
            @RequestParam(value = "offset",defaultValue = "0") int offset,
            @RequestParam(value = "limit",defaultValue = "20") int limit,
            @RequestParam(value = "other",defaultValue = "") String other   //other暂时用不到
    ){
        OrderPageBean<Order> orderPageBean = new OrderPageBean<Order>(search,sort,order,offset,limit);
        orderPageBean = orderService.findUnFilledOrdersBySid("1",orderPageBean);
        String orderJson = gson.toJson(orderPageBean.getList());
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("total",new JsonPrimitive(orderPageBean.getTotal()));
        jsonObject.add("rows",new JsonParser().parse(orderJson).getAsJsonArray());
        System.out.println(jsonObject.toString());
        return jsonObject.toString();
    }

    @RequestMapping("/getOrderList3")
    @ResponseBody
    public String getOrderList3(
            @RequestParam(value = "search",defaultValue = "") String search,
            @RequestParam(value = "sort",defaultValue = "") String sort,
            @RequestParam(value = "order",defaultValue = "asc") String order,
            @RequestParam(value = "offset",defaultValue = "0") int offset,
            @RequestParam(value = "limit",defaultValue = "20") int limit,
            @RequestParam(value = "other",defaultValue = "") String other   //other暂时用不到
    ){
        OrderPageBean<Order> orderPageBean = new OrderPageBean<Order>(search,sort,order,offset,limit);
        orderPageBean = orderService.findUnFilledOrdersBySid("1",orderPageBean);
        String orderJson = gson.toJson(orderPageBean.getList());
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("total",new JsonPrimitive(orderPageBean.getTotal()));
        jsonObject.add("rows",new JsonParser().parse(orderJson).getAsJsonArray());
        System.out.println(jsonObject.toString());
        return jsonObject.toString();
    }
}