package com.ecom.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RememberMeUtils {
    /**
     * 记住我功能的实现
     * @param request
     * @param response
     * @param username  用户名
     * @param password  密码
     * @param remember  记住我checkbox
     */
            public static void remember(HttpServletRequest request, HttpServletResponse response,String username,String password,String remember) {

//        String codeName="";  
//        try {  
//            codeName = URLEncoder.encode(name, "UTF-8");      //对输入的中文进行编码，防止乱码出现  
//        } catch (UnsupportedEncodingException e) {  
//            e.printStackTrace();  
//        }  
        Cookie nameCookie = new Cookie("username", username);
        Cookie passwordCookie = new Cookie("password", password);   
        nameCookie.setPath(request.getContextPath() + "/");      //设置Cookie的有效路径  
        passwordCookie.setPath(request.getContextPath() + "/");//设置Cookie的有效路径  
        if (remember != null && "yes".equals(remember)){            //有记住我，就设置cookie的保存时间  
            nameCookie.setMaxAge(7 * 24 * 60 * 60);  
            passwordCookie.setMaxAge(7 * 24 * 60 * 60);  
        }else{                                                                                 //没有记住我，设置cookie的时间为0  
            nameCookie.setMaxAge(0);  
            passwordCookie.setMaxAge(0);  
        }  
        response.addCookie(nameCookie);  //nameCookie
        response.addCookie(passwordCookie);  //passwordCookie
    }  
}
