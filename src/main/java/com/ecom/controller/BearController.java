package com.ecom.controller;

import com.ecom.pojo.Category;
import com.ecom.pojo.User;
import com.ecom.service.ProductService;
import com.ecom.service.UserService;
import com.ecom.utils.CommonUtils;
import com.ecom.utils.JedisPoolUtils;
import com.ecom.utils.MailUtils;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class BearController {

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



//   ----------------------------------user用户------------------------------------------

    //    检查手机号是否重复的功能
    @RequestMapping("/checkPhone")
    public void checkPhone(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // 获得电话号码
        String phone = request.getParameter("phone");
        UserService service = new UserService();
        boolean isExist = service.checkPhone(phone);
        String json = "{\"isExist\":" + isExist + "}";
        response.getWriter().write(json);
    }

    //    检查邮箱是否重复的功能
    @RequestMapping("/checkEmail")
    public void checkEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获得邮箱
        String email = request.getParameter("email");
        UserService service = new UserService();
        boolean isExist = service.checkEmail(email);
        String json = "{\"isExist\":" + isExist + "}";
        response.getWriter().write(json);
    }


    //    用户注册功能
    @RequestMapping("/registerUser")
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setCharacterEncoding("UTF-8");
//        获得表单数据
        Map<String, String[]> properties = request.getParameterMap();
        User user = new User();
        try {
//            自己指定一个类型转换器（将String转成Date）
            ConvertUtils.register(new Converter() {
                @Override
                public Object convert(Class aClass, Object o) {
//                    将String转成Date
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date parse = null;
                    try {
                        parse = format.parse(o.toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return parse;
                }
            }, Date.class);
//            映射封装
            BeanUtils.populate(user, properties);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        user.setUid(CommonUtils.getUUID());
        user.setSid("0");
        String activeCode = CommonUtils.getUUID();
        user.setUcode(activeCode);
        user.setState(0);

//        将user传递给service层
        UserService service = new UserService();
        boolean isRegisterSuccess = false;
        try {
            isRegisterSuccess = service.register(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        判断是否注册成功
        if (isRegisterSuccess) {
//            发送激活邮件
            String emailMsg = "恭喜您注册成功，请点击下面的连接进行激活账户" +
                    "<a href='http://localhost:8080/ecom/active?activeCode=" + activeCode
                    + "'>http://localhost:8080/ecom/active?activeCode=" + activeCode + "</a>";
            try {
                MailUtils.sendMail(user.getEmail(), emailMsg);
            } catch (MessagingException e) {
                e.printStackTrace();
            }

//            跳转到注册成功的页面
            response.sendRedirect(request.getContextPath() + "/registerSuccess");
        } else {
//            跳转到注册失败的页面
            response.sendRedirect(request.getContextPath() + "/registerFail");

        }
    }


    //    激活注册用户的功能
    @RequestMapping("/active")
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        获得激活码
        String activeCode = request.getParameter("activeCode");
        UserService service = new UserService();
        service.active(activeCode);
        response.getWriter().write("alert('恭喜您，激活成功，请重新登陆！')");
//        跳转到登陆页面
        response.sendRedirect(request.getContextPath() + "/login");
    }

}
