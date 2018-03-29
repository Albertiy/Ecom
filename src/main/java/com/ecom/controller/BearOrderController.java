package com.ecom.controller;

import com.ecom.pojo.*;
import com.ecom.service.OrderService;
import com.ecom.service.ProductService;
import com.ecom.utils.CommonUtils;
import com.ecom.utils.PaymentUtil;
import com.google.gson.Gson;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Controller
public class BearOrderController {

    //    提交订单
    @RequestMapping("/submitOrder")
    public void submitOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        //判断用户是否登陆，若果未登录下面代码不执行
        User user = (User) session.getAttribute("user");
        if (user == null) {
            //没有登陆
            response.sendRedirect(request.getContextPath() + "/login");
            return;//跳转之后后面的代码不再执行。
        }
        //一个cart对应一个oriderList
        //一个orderList对应很多个Order
        //一个order对应一个odata
        //一个odata对应一个OrderDataList
        //一个orderDataList对应很多个orderData
        Cart cart = (Cart) session.getAttribute("cart");
        List<Order> orderList=new ArrayList<Order>();

        ProductService service = new ProductService();
        //购物车的总价格
        float carttotal = cart.getTotal();


        //1.订单oid
        String oid = CommonUtils.getUUID();
        //2.uid
        String uid = user.getUid();



        //获得购物车中购物项的集合map
        Map<String, CartItem> cartItems = cart.getCartItems();
        for (Map.Entry<String, CartItem> cartItemEntry : cartItems.entrySet()) {

            //每个店铺创建一个order
            Order order = new Order();
            //取出每一个购物项店铺的sid
            String sid = cartItemEntry.getKey();
            //取出每一个购物项
            CartItem cartItem = cartItemEntry.getValue();
            //每个店铺的总价
            float total = cartItem.getCartitem_total();


            List<OrderData> orderDataList=new ArrayList<OrderData>();
            //获得购物项里面的商品集合map
            Map<String, ProductItem> productItems = cartItem.getProductItems();
            for (Map.Entry<String, ProductItem> productItemEntry : productItems.entrySet()) {
                OrderData orderData = new OrderData();
                String pid = productItemEntry.getKey();
                ProductItem productItem = productItemEntry.getValue();
                float subtotal = productItem.getSubtotal();
                int buyNum = productItem.getBuyNum();

                //删除库存
                service.delNumByPid(pid,buyNum);

                String pname = productItem.getProduct().getPname();
                float price = productItem.getProduct().getPrice();
                //封装orderData
                orderData.setJudged(false);
                orderData.setReturned(false);
                orderData.setPcount(buyNum);
                orderData.setPid(pid);
                orderData.setPname(pname);
                orderData.setShopPrice(price);
                orderData.setSubTotal(subtotal);
                orderDataList.add(orderData);
            }
            Gson gson = new Gson();
            String orderDataListJson = gson.toJson(orderDataList);
            String odata = "{\"odata\":"+orderDataListJson+"}";
            System.out.println(odata);

            //封装order数据
            order.setOid(oid);
            order.setSid(sid);
            order.setUid(uid);
            order.setPayTime("");
            order.setDeliveryTime("");
            order.setFinishTime("");
            order.setTotal(total);
            order.setConsignee("");
            order.setPhone("");
            order.setAddress("");
            order.setState("0");
            order.setPs("");
            order.setNickName(user.getNickname());
            order.setOdata(odata);



            orderList.add(order);

        }

        //orderList对象封装完毕
        OrderService orderService = new OrderService();
        orderService.submitOrder(orderList);
        session.setAttribute("orderList",orderList);
        session.setAttribute("cart",cart);
        response.sendRedirect(request.getContextPath() + "/order_info");
    }

    ////    更新收货人信息+在线支付
    //@RequestMapping("/submitOrder")
    //public void confirmOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //
    //    Map<String, String[]> properties = request.getParameterMap();
    //
    //    Order order = new Order();
    //    try {
    //        BeanUtils.populate(order, properties);
    //    } catch (IllegalAccessException e) {
    //        e.printStackTrace();
    //    } catch (InvocationTargetException e) {
    //        e.printStackTrace();
    //    }
    //
    //    OrderService service = new OrderService();
    //    service.updateOrderAdrr(order);
    //
    //
    //    //    2.选择银行
    //    //只接入一个接口，这个接口已经集成所有的银行接口了  ，这个接口是第三方支付平台提供的
    //    //接入的是易宝支付
    //    // 获得 支付必须基本数据
    //    String orderid = request.getParameter("oid");
    //    //String money = order.getTotal()+"";//支付金额
    //    String money = "0.01";//支付金额
    //    // 银行
    //    String pd_FrpId = request.getParameter("pd_FrpId");
    //
    //    // 发给支付公司需要哪些数据
    //    String p0_Cmd = "Buy";
    //    String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
    //    String p2_Order = orderid;
    //    String p3_Amt = money;
    //    String p4_Cur = "CNY";
    //    String p5_Pid = "";
    //    String p6_Pcat = "";
    //    String p7_Pdesc = "";
    //    // 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
    //    // 第三方支付可以访问网址
    //    String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("callback");
    //    String p9_SAF = "";
    //    String pa_MP = "";
    //    String pr_NeedResponse = "1";
    //    // 加密hmac 需要密钥
    //    String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
    //            "keyValue");
    //    String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
    //            p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
    //            pd_FrpId, pr_NeedResponse, keyValue);
    //
    //
    //    String url = "https://www.yeepay.com/app-merchant-proxy/node?pd_FrpId=" + pd_FrpId +
    //            "&p0_Cmd=" + p0_Cmd +
    //            "&p1_MerId=" + p1_MerId +
    //            "&p2_Order=" + p2_Order +
    //            "&p3_Amt=" + p3_Amt +
    //            "&p4_Cur=" + p4_Cur +
    //            "&p5_Pid=" + p5_Pid +
    //            "&p6_Pcat=" + p6_Pcat +
    //            "&p7_Pdesc=" + p7_Pdesc +
    //            "&p8_Url=" + p8_Url +
    //            "&p9_SAF=" + p9_SAF +
    //            "&pa_MP=" + pa_MP +
    //            "&pr_NeedResponse=" + pr_NeedResponse +
    //            "&hmac=" + hmac;
    //
    //    //重定向到第三方支付平台
    //    response.sendRedirect(url);
    //}
}
