package com.ecom.controller;

import com.ecom.pojo.*;
import com.ecom.service.ProductService;
import com.ecom.service.UserService;
import com.ecom.utils.CommonUtils;
import com.ecom.utils.JedisPoolUtils;
import com.ecom.utils.MailUtils;
import com.ecom.utils.RememberMeUtils;
import com.google.gson.Gson;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class BearProductController {

//   ----------------------------------product商品--------------------------------------

    //    显示商品的类别功能
    @RequestMapping("/categoryList")
    public void categoryList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ProductService service = new ProductService();
//        先从缓存中查询categoryList，如果有直接使用，没有再从数据库中查询，存到缓存中
//        1.获得jedis对象，链接redis数据库
        Jedis jedis = null;
        String categoryListJson = null;
        boolean jedisflag = true;
        try {
            jedis = JedisPoolUtils.getJedis();
            categoryListJson = jedis.get("categoryListJson");
        } catch (JedisConnectionException e) {
            jedisflag = false;
            System.out.println("Jedis数据库打不开");
        }
//        2.判断categoryListJson是否为空，
        if (categoryListJson == null || !jedisflag) {
            System.out.println("缓存没有数据，查询数据库！");
//            准备分类数据
            List<Category> categoryList = service.findAllCategroy();
            Gson gson = new Gson();
            categoryListJson = gson.toJson(categoryList);
            if (jedisflag) {
                jedis.set("categoryListJson", categoryListJson);
            }

        }


        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(categoryListJson);
    }

    //    显示首页的功能
    @RequestMapping("/getIndex")
    public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService service = new ProductService();


//        准备热门商品---List<Product>
        List<Product> hotProductList = service.findHotProductList();


//        准备最新商品---List<Product>
        List<Product> newProductList = service.findNewProductList();

//        准备分类数据
//        List<Category> categoryList = service.findAllCategroy();

        request.setAttribute("hotProductList", hotProductList);
        request.setAttribute("newProductList", newProductList);
//        request.setAttribute("categoryList", categoryList);

        request.getRequestDispatcher("/index").forward(request, response);

    }

    //    根据商品的类别获得商品的目录
    @RequestMapping("/productList")
    public void productList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        获得cid
        String cid = request.getParameter("cid");
        String currentPageStr = request.getParameter("currentPage");
        if (currentPageStr == null) {
            currentPageStr = "1";
        }
        int currentPage = Integer.parseInt(currentPageStr);
        int currentCount = 12;

        ProductService service = new ProductService();
        PageBean pageBean = service.findProductListByCid(cid, currentPage, currentCount);

        request.setAttribute("pageBean", pageBean);
        request.setAttribute("cid", cid);


//        定义一个记录历史商品信息的集合
        List<Product> historyProductList = new ArrayList<>();
//        获得客户端携带名字叫pids的cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("pids".equals(cookie.getName())) {
                    String pids = cookie.getValue();
                    String[] split = pids.split("-");
                    for (String pid : split) {
                        Product pro = service.findProductByPid(pid);
                        historyProductList.add(pro);
                    }
                }
            }
        }

//        将历史记录的集合放到域中
        request.setAttribute("historyProductList", historyProductList);

        request.getRequestDispatcher("/product_list").forward(request, response);
    }



    //    显示商品的详细信息
    @RequestMapping("/productInfo")
    public void productInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        获得当前页
        String currentPage = request.getParameter("currentPage");
//        获得商品类别
        String cid = request.getParameter("cid");

//        获得要查询的商品的pid
        String pid = request.getParameter("pid");

        ProductService service = new ProductService();
        Product product = service.findProductByPid(pid);

        request.setAttribute("product", product);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("cid", cid);

//        获得客户端携带的cookie--获得名字是pids的cookie
        String pids = pid;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("pids".equals(cookie.getName())) {
                    pids = cookie.getValue();
                    // 1-3-2 本次访问商品是8------->8-1-3-2
                    // 1-3-2 本次访问商品是3------->3-1-2
                    // 1-3-2 本次访问商品是2------->2-1-3
                    // 将pids拆成一个数组
                    String[] split = pids.split("-");//{3，1，2}
                    List<String> asList = Arrays.asList(split);//[3,1,2]
                    LinkedList<String> list = new LinkedList<>(asList);//[3,1,2]
                    // 判断集合中是否存在当前pid
                    if (list.contains(pid)) {
//                        包含当前查看的商品的pid
                        list.remove(pid);
                    }
                    list.addFirst(pid);
//                    将[3,1,2]转成3-1-2字符串
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < list.size() && i < 7; i++) {
                        sb.append(list.get(i));
                        sb.append("-");//3-1-2-
                    }
//                    去掉多余的-
                    pids = sb.substring(0, sb.length() - 1);


                }
            }
        }
        Cookie cookie_pids = new Cookie("pids", pids);
        response.addCookie(cookie_pids);

        request.getRequestDispatcher("/product_info").forward(request, response);
    }


    //    将商品添加到购物车
    @RequestMapping("/addProduct2Cart")
    public void addProduct2Cart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        HttpSession session = request.getSession();
        ProductService service = new ProductService();

//        要放到购物车的商品的pid
        String pid = request.getParameter("pid");
//        获得商品的购买数量
        int buyNum = Integer.parseInt(request.getParameter("buyNum"));

//        获得product对象
        Product product = service.findProductByPid(pid);
//        计算小计
        double subtotal = product.getPrice() * buyNum;
//        封装CartItem
        CartItem item = new CartItem();
        item.setProduct(product);
        item.setBuyNum(buyNum);
        item.setSubtotal(subtotal);

//        获得购物车---判断是否在Session中已经存在购物车
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }

//        将此次的购物项放到车中---key是pid
//        先判断购物车是否已经包含此购物项--------判断key，是否已经存在
//        如果购物车中已经存在该商品----将现在买的数量与原有的数量进行相加操作

        Map<String, CartItem> cartItems = cart.getCartItems();
        double newsubtotal = 0.0d;
        if (cartItems.containsKey(pid)) {

//            取出原有的商品的数量
            CartItem cartItem = cartItems.get(pid);
//            修改商品数量
            int oldBuyNum = cartItem.getBuyNum();
            oldBuyNum += buyNum;
            cartItem.setBuyNum(oldBuyNum);
            cart.setCartItems(cartItems);

//            修改小计
            double oldsubtotal = cartItem.getSubtotal();
            newsubtotal = buyNum * product.getPrice();
            cartItem.setSubtotal(newsubtotal + oldBuyNum);


        } else {
//        若果车中没有该商品
            cart.getCartItems().put(product.getPid(), item);
            newsubtotal = buyNum * product.getPrice();

        }


//            计算总计
        double totoal = cart.getTotal() + newsubtotal;
        cart.setTotal(totoal);
//        将车再次放到session中
        session.setAttribute("cart", cart);

//        直接跳转到购物车页面
//        这边如果我们转发的话，到我们的购物车页面，如果我们刷新页面的话，会继续继续servelt方法，再执行一次，这样是不合理的。
//        request.getRequestDispatcher("/cart.jsp").forward(request,response);

//        因此我们使用重定向
        response.sendRedirect(request.getContextPath() + "/cart");
    }


    //    清空购物车
    @RequestMapping("/clearCart")
    public void clearCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("cart");
        response.sendRedirect(request.getContextPath() + "/cart");



    }


    //    删除单一商品
    @RequestMapping("/delProFromCart")
    public void delProFromCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        获得要删除的item的pid
        String pid = request.getParameter("pid");
//        删除session中的购物车的购物项集合中的item
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            Map<String, CartItem> cartItems = cart.getCartItems();
//            需要修改总价
            cart.setTotal(cart.getTotal() - cartItems.get(pid).getSubtotal());
            cartItems.remove(pid);
            cart.setCartItems(cartItems);

        }

        session.setAttribute("cart", cart);

//        跳转回购物车页
        response.sendRedirect(request.getContextPath() + "/cart");

    }


}
