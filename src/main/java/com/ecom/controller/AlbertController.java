package com.ecom.controller;

import com.ecom.auth.AuthSeller;
import com.ecom.pojo.Order;
import com.ecom.pojo.OrderData;
import com.ecom.pojo.OrderPageBean;
import com.ecom.service.OrderService;
import com.google.gson.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@Controller
public class AlbertController {

    //获取Gson的Bean
    public static ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext1.xml");
    private Gson gson = (Gson)context.getBean("gson");

    @Resource(name="orderService")
    private OrderService orderService;

    @RequestMapping("/unfilledOrder")
    @AuthSeller
    public String testOrder(){
        System.out.println("【/unfilledOrder】");
        return "unfilled_order";
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
        orderPageBean = orderService.findUnFilledOrdersBySid("1",orderPageBean);
        String orderJson = gson.toJson(orderPageBean.getList());
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("total",new JsonPrimitive(orderPageBean.getTotal()));
        jsonObject.add("rows",new JsonParser().parse(orderJson).getAsJsonArray());
        System.out.println(jsonObject.toString());
        return jsonObject.toString();
    }

    @RequestMapping("/getOrderDetails")
    //@ResponseBody
    public String getOrderDetails(@RequestParam(value = "odata",defaultValue = "") String odata, Map<String,Object> map){
        System.out.println("【getOrderDetails】");
        List<OrderData> orderDetails = new ArrayList<OrderData>();
        try {
            //先转JsonObject
            JsonObject jsonObject = new JsonParser().parse(odata).getAsJsonObject();
            //再转JsonArray 加上数据头
            JsonArray jsonArray = jsonObject.getAsJsonArray("odata");
            orderDetails = gson.fromJson(jsonArray.toString(), orderDetails.getClass());
        }catch(Exception e){
            System.out.println("orderData 格式转换失败！");
            System.out.println(e);
        }
        map.put("orderDetails",orderDetails);
        return "order_table_details";
    }
}
