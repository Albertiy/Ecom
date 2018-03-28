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
        String pid = request.getParameter("pid");
        int buyNum = Integer.parseInt(request.getParameter("buyNum"));
//        获得product对象
        Product product = service.findProductByPid(pid);

        //获得sid
        String sid = product.getSid();
//        计算小计
        float subtotal = product.getPrice() * buyNum;
//        封装productItem
        ProductItem item = new ProductItem();
        item.setProduct(product);
        item.setBuyNum(buyNum);
        item.setSubtotal(subtotal);

        //        获得购物车---判断是否在Session中已经存在购物车
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }
        Map<String, CartItem> cartItems = cart.getCartItems();

        //首先需要判断这个商品是否属于某个店铺，
        if (cartItems.containsKey(sid)) {
            //如果属于这个店铺,判断这个店铺里面是否已经有了这个商品
            CartItem cartItem = cartItems.get(sid);
            float newcartitem_total = 0.0f;

            Map<String, ProductItem> productItems = cartItem.getProductItems();
            float newsubtotal = 0.0f;

            if (productItems.containsKey(pid)) {

                //如果已经有了这个商品，就修改对应的数量和总价
                ProductItem productItem = productItems.get(pid);
                //修改商品数量
                int oldBuyNum = productItem.getBuyNum();
                oldBuyNum += buyNum;
                productItem.setBuyNum(oldBuyNum);
                //cartItem.setProductItems(productItems);

                //修改小计
                float oldsubtotal1 = productItem.getSubtotal();
                newsubtotal = buyNum * product.getPrice();
                productItem.setSubtotal(newsubtotal + oldsubtotal1);

            } else {
                //如果没有这个商品，就将这个商品添加到其中
                productItems.put(product.getPid(), item);
                newsubtotal = buyNum * product.getPrice();


            }
            //修改店铺的总价
            float oldcartitem_total = cartItem.getCartitem_total();
            newcartitem_total = subtotal;
            cartItem.setCartitem_total(oldcartitem_total + newcartitem_total);
        } else {
            //如果不属于这个店铺，就创建一个新的CartItem，并且修改购物车的总价
            Map<String, ProductItem> productItems = new HashMap<>();
            productItems.put(product.getPid(), item);

            CartItem cartItem = new CartItem();
            cartItem.setProductItems(productItems);
            cartItem.setCartitem_total(subtotal);

            cartItems.put(product.getSid(), cartItem);
        }

        //计算总计
        float total = cart.getTotal() + buyNum * product.getPrice();
        cart.setTotal(total);
        //将车再次放到session中
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

        //获得要删除的item的pid
        String pid = request.getParameter("pid");
        //        获得要删除的item的pid
        String sid = request.getParameter("sid");
        //删除session中的购物车的购物项集合中的item
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            Map<String, CartItem> cartItems = cart.getCartItems();
            CartItem cartItem = cartItems.get(sid);
            Map<String, ProductItem> productItems = cartItem.getProductItems();

            ProductItem productItem = productItems.get(pid);
            float subtotal = productItem.getSubtotal();

            cartItem.setCartitem_total(cartItem.getCartitem_total()-subtotal);
            cart.setTotal(cart.getTotal()-subtotal);

            productItems.remove(pid);
            cartItem.setProductItems(productItems);
            if(productItems.isEmpty()){
                cartItems.remove(sid);
            }
            cart.setCartItems(cartItems);

        }

        session.setAttribute("cart", cart);

//        跳转回购物车页
        response.sendRedirect(request.getContextPath() + "/cart");

    }


}
