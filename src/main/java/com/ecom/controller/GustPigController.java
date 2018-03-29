package com.ecom.controller;

import com.ecom.pojo.*;
import com.ecom.service.ExpressService;
import com.ecom.service.OrderService;
import com.ecom.service.StoreService;
import com.google.gson.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class GustPigController {

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
    private Gson gson = (Gson)context.getBean("gson");

    @Resource(name="orderService")
    private OrderService orderService;

    @RequestMapping("/test_GustPig")
    public String test_GustPig(){
        return "test_GustPig";
    }

    @RequestMapping("/store_info")
    public String store_info(){
        return "store_info";
    }

    @RequestMapping("/store_info_change")
    public String store_info_change(){
        return "store_info_change";
    }

    @RequestMapping("/store_income")
    public String store_income(){
        return "store_income";
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

    //加载物流信息
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
        System.out.println("Controller执行成功");
        request.setAttribute("express", express);
        request.getRequestDispatcher("/expressmodal").forward(request, response);
        System.out.println("传参成功");
    }

    //加载店铺信息
    @RequestMapping("/findStore")
    public void findStore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        System.out.print("进入findStore Controller");
        String uid = request.getParameter("uid");
        System.out.println(" uid="+uid);
        Store store = new Store();
        StoreService service = new StoreService();
        // 调用service查询店铺信息
        store = service.findStoreInfo(uid);
        System.out.println("Controller执行成功");
        request.setAttribute("store",store);
        request.getRequestDispatcher("/store_info").forward(request,response);
    }

    //加载修改店铺信息
    @RequestMapping("/findStoreChange")
    public void findStoreChange(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        System.out.print("进入findStoreChange Controller");
        String uid = request.getParameter("uid");
        System.out.println(" uid="+uid);
        Store store = new Store();
        StoreService service = new StoreService();
        // 调用service查询店铺信息
        store = service.findStoreInfo(uid);
        System.out.println("Controller执行成功");
        request.setAttribute("store",store);
        request.getRequestDispatcher("/store_info_change").forward(request,response);
    }

    //修改店铺信息
    @RequestMapping("/StoreChange")
    public void StoreChange(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        System.out.println("进入StoreChange Controller");
        String st = request.getParameter("st");
        Store store = new Store();
        store.setUid(request.getParameter("uid"));
        if(st.equals("1")) {   //st=1,修改基本信息；st=2,修改店铺状态
            System.out.println("st=1?");
            store.setSname(request.getParameter("sname"));
            store.setSaddress(request.getParameter("saddress"));
            store.setIntroduce(request.getParameter("introduce"));
        }
        if(st.equals("2")){
            System.out.println("st=2?");
            store.setState(request.getParameter("state"));
        }
        StoreService service = new StoreService();
        // 调用service查询店铺信息
        // i==200,成功；i==1,失败
        int i = service.StoreChange(store,st);
        if(i==200) {
            System.out.println("Controller执行成功");
            //response.getWriter().print("<script>alert('修改成功');</script>");
        }else{
            System.out.println("Controller执行失败");
        }
        System.out.println("findStore?uid="+store.getUid());
        request.getRequestDispatcher("/findStore").forward(request,response);
    }

    // SpringMVC 的 @ResponseBody 标注了该函数返回的内容直接作为Response，不再加载页面
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
        orderPageBean = orderService.findUnFilledOrdersBySid2(user.getSid(),orderPageBean);
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
        orderPageBean = orderService.findUnFilledOrdersBySid3(user.getSid(),orderPageBean);
        String orderJson = gson.toJson(orderPageBean.getList());
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("total",new JsonPrimitive(orderPageBean.getTotal()));
        jsonObject.add("rows",new JsonParser().parse(orderJson).getAsJsonArray());
        System.out.println(jsonObject.toString());
        return jsonObject.toString();
    }
}