//package com.ecom.controller;
//
//import com.ecom.pojo.User;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.util.Date;
//import java.util.Map;
//
//@Controller
//public class BearOrderController {
//
//    //    提交订单
//    @RequestMapping("/submitOrder")
//    public void submitOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        HttpSession session = request.getSession();
////        判断用户是否登陆，若果未登录下面代码不执行
//        User user = (User) session.getAttribute("user");
//        if (user == null) {
////            没有登陆
//            response.sendRedirect(request.getContextPath() + "/login");
//            return;//跳转之后后面的代码不再执行。
//        }
//
//
////        封装好一个Order对象，传递给service层
//
//        Order order = new Order();
//
////        1. private String oid;//该订单的订单号
//
//        String oid = CommonUtils.getUUID();
//        order.setOid(oid);
////        2. private Date ordertime;//下单时间
//        order.setOrdertime(new Date());
////        3. private double total;//该订单的总金额
//        Cart cart = (Cart) session.getAttribute("cart");
//        if (cart != null) {
//            double total = cart.getTotal();
//            order.setTotal(total);
//
////        4. private int state;//订单的支付状态1代表已付款，0代表未付款
//            order.setState(0);
////        5. private String addr;//收货地址
//            order.setAddr(null);
////        6. private String name;//收货人
//            order.setName(null);
//
////        7. private String telephone;//收货人电话
//            order.setTelephone(null);
//
////        8. private User user;//该订单属于哪个用户
//            order.setUser(user);
////
////
////        // 该订单中有多少订单项
////        9.List<OrderItem> orderItems = new ArrayList<OrderItem>();
//
////            获得购物车中购物项的集合map
//            Map<String, CartItem> cartItems = cart.getCartItems();
//            for (Map.Entry<String, CartItem> entry : cartItems.entrySet()) {
//
////                取出每一个购物项
//                CartItem cartItem = entry.getValue();
//                OrderItem orderItem = new OrderItem();
//                orderItem.setItemid(CommonUtils.getUUID());
//                orderItem.setCount(cartItem.getBuyNum());
//                orderItem.setSubtotal(cartItem.getSubtotal());
//                orderItem.setProduct(cartItem.getProduct());
//                orderItem.setOrder(order);
//
////                将该订单项添加到订单的订单集合中
//                order.getOrderItems().add(orderItem);
//            }
//
//
////            Order对象封装完毕
//
//
////            转递数据到service层
//            OrderService service = new OrderService();
//            service.submitOrder(order);
//
//            session.setAttribute("order", order);
////            页面跳转
//            response.sendRedirect(request.getContextPath() + "/order_info.jsp");
//        }
//    }
//
//}
