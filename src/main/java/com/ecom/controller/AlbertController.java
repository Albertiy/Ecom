package com.ecom.controller;

import com.ecom.auth.AuthSeller;
import com.ecom.auth.AuthUser;
import com.ecom.pojo.*;
import com.ecom.service.ExpressService;
import com.ecom.service.OrderService;
import com.google.gson.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class AlbertController {

    User user = null;
    @ModelAttribute//这个注解的函数会在每个方法之前执行
    public void getUserSession(HttpServletRequest request, HttpServletResponse response){
        System.out.println("【getUserSession】");
        user= (User) request.getSession().getAttribute("user");
        if(user==null){
            try {
                response.sendRedirect(request.getContextPath()+"/login");
            } catch (IOException e) {
                System.out.println("重定向失败！");
                e.printStackTrace();
            }
        }
    }

    //获取Gson的Bean
    public static ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext1.xml");
    private Gson gson = (Gson) context.getBean("gson");

    @Resource(name = "orderService")
    private OrderService orderService;

    @Resource(name = "expressService")
    private ExpressService expressService;

    @RequestMapping("/unfilledOrder")
    @AuthUser
    @AuthSeller
    public String testOrder() {
        System.out.println("【/unfilledOrder】");
        return "unfilled_order";
    }

    @RequestMapping("/testbootstrap")
    public String testBootstrap() {
        System.out.println("test bootstrap");
        return "test_bootstrap";
    }

    @RequestMapping("/order_header")
    public String orderHeader() {
        return "order_header";
    }

    /*
     * 这个是测试用例
     * */
    @RequestMapping("/getjson")
    public String getjson() {
        return "getjson";
    }

    @RequestMapping("/openStore")
    public String openStore(){
        return "open_store"; 
    }
    /*
     * 这个是真的
     * SpringMVC 的 @ResponseBody 标注了该函数返回的内容直接作为Response，不再加载页面
     * */
    @RequestMapping("/getOrderList")
    @ResponseBody
    public String getOrderList(
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "order", defaultValue = "asc") String order,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "20") int limit,
            @RequestParam(value = "other", defaultValue = "") String other   //other暂时用不到
    ) {
        OrderPageBean<Order> orderPageBean = new OrderPageBean<Order>(search, sort, order, offset, limit);
        //TODO 从Session中获取sid
        orderPageBean = orderService.findUnFilledOrdersBySid(user.getSid(), orderPageBean);
        String orderJson = gson.toJson(orderPageBean.getList());
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("total", new JsonPrimitive(orderPageBean.getTotal()));
        jsonObject.add("rows", new JsonParser().parse(orderJson).getAsJsonArray());
        System.out.println(jsonObject.toString());
        return jsonObject.toString();
    }

    @RequestMapping("/getOrderDetails")
    public String getOrderDetails(@RequestParam(value = "odata", defaultValue = "") String odata, Map<String, Object> map) {
        System.out.println("【getOrderDetails】");
        List<OrderData> orderDetails = new ArrayList<OrderData>();
        try {
            //先转JsonObject
            JsonObject jsonObject = new JsonParser().parse(odata).getAsJsonObject();
            //再转JsonArray 加上数据头
            JsonArray jsonArray = jsonObject.getAsJsonArray("odata");
            //System.out.println("jsonArray: " + jsonArray.size());
            //新的方法：然而到JSP页面后无法读取了
            for(int i = 0;i<jsonArray.size();i++){
                //记住Json里的0和1不能自动转换为boolean类型啊！！！直接存false不带双引号啊！
                OrderData temp=  gson.fromJson(jsonArray.get(i).toString(),OrderData.class);
                String pname = orderService.findPnameByPid(temp.getPid());
                System.out.println(pname);
                temp.setPname(pname);
                orderDetails.add(temp);
            }
            //之前的方法：
            //orderDetails = gson.fromJson(jsonArray.toString(), orderDetails.getClass());
        } catch (Exception e) {
            System.out.println("orderData 格式转换失败！");
            System.out.println(e);
        }
        map.put("orderDetails", orderDetails);
        return "order_table_details";
    }

    /**
     * eid varchar(50) not null primary key,
     * oid varchar(50) not null,	-- 订单id
     * sid varchar(50) not null,	-- 店铺id
     * etime datetime not null,	-- 发货时间
     * eaddress varchar(255) not null,	-- 发货地址=店铺地址
     * saddress varchar(255) not null,	-- 收货地址
     * ecompany varchar(255) not null	-- 快递公司
     */
    @RequestMapping(value = "/addExpress/{eeid}")
    @ResponseBody
    public int addExpress(@PathVariable String eeid,
                          @RequestParam(value = "eid", defaultValue = "") String eid,
                          @RequestParam(value = "oid", defaultValue = "") String oid,
                          @RequestParam(value = "eaddress", defaultValue = "") String eAddress,//收货地址
                          @RequestParam(value = "ecompany", defaultValue = "申通") String eCompany) {

        System.out.println("【addExpress】");
        System.out.println("eid: " + eid);


        String sid = user.getSid();
        String saddress = "";   //发货地址（从store表获取）
        Express express = new Express();
        if(!eeid.equals(eid)){
            System.out.println("eid不相等！");
            return 0;
        }
        express.setEid(eid);
        express.setOid(oid);
        express.setSid(sid);
        express.seteAddress(eAddress);
        express.seteCompany(eCompany);
        express.setsAddress(saddress);
        System.out.println(express.toString());
        int i = expressService.addExpress(express);
        //数据库有trigger，插入物流自动修改 _order.state = 2
        return i;
    }
}
