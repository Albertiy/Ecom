package com.ecom.auth;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecom.pojo.User;
import com.ecom.service.UserService;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.sql.SQLException;

/*
* 添加自己的拦截器实现AuthInterceptor继承于HandlerInterceptorAdapter
* @AuthUser 优先级 高于 @AuthSeller，
* 若没有@AuthUser，则@AuthSeller无效；
* 若没有@AuthSeller，只有@AuthUser，则验证失败重定向到login页面；
* 若两者都有，且登陆验证完毕，则重定向到开店页面。
* */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
            AuthUser authUser = ((HandlerMethod) handler).getMethodAnnotation(AuthUser.class);
            AuthSeller authSeller = ((HandlerMethod) handler).getMethodAnnotation(AuthSeller.class);
            /*System.out.println("AuthUser: "+authUser);
            System.out.println("AuthSeller: "+authSeller);*/
            //如果没有声明需要权限,或者声明不验证权限
            if(authUser == null || authUser.validate() == false) {
                return super.preHandle(request, response, handler);
            } else{   //在这里实现自己的权限验证逻辑
                System.out.print("【验证登陆】：");
                if(!checkUser(request,response)){
                    System.out.println("未登录！");
                    response.sendRedirect(request.getContextPath()+"/login");
                    return false;
                }
                System.out.println("已登录！");
            }

            if(authSeller == null || authSeller.validate() == false)
                return super.preHandle(request, response, handler);
            else{//首先检查用户是否登陆，以防没有使用 @AuthUser 标签
                System.out.print("【验证商铺】：");
                if(!checkUser(request,response)){
                    response.sendRedirect(request.getContextPath()+"/login");
                    return false;
                }
                if(checkSeller(request,response)) {//如果验证成功返回true（这里直接写false来模拟验证失败的处理）
                    System.out.println("已开通店铺！");
                    return super.preHandle(request, response, handler);
                } else{ //如果验证失败
                        //返回到商户注册界面
                    System.out.println("未开通店铺！");
                    response.sendRedirect(request.getContextPath()+"/openStore");
                    return false;
                }
            }
        }
        else
            return super.preHandle(request, response, handler);
    }

    /**
     * checkUser函数
    * */
    public boolean checkUser(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            String cookie_username = null;
            String cookie_password = null;

            //获取携带用户名和密码cookie
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    //获得想要的cookie
                    if ("cookie_username".equals(cookie.getName())) {
                        cookie_username = cookie.getValue();
                    }
                    if ("cookie_password".equals(cookie.getName())) {
                        cookie_password = cookie.getValue();
                    }
                }
            }

            if (cookie_username != null && cookie_password != null) {
                //去数据库校验该用户名和密码是否正确
                UserService service = new UserService();
                try {
                    user = service.login(cookie_username, cookie_password);
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("验证失败！");
                    return false;
                }
                //完成自动登录
                request.getSession().setAttribute("user", user);
            } else
                return false;
        }
        return true;
    }

    /**
     * checkSeller函数
     * */
    public boolean checkSeller (HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null)
            return false;
        System.out.println("店铺Sid："+user.getSid());
        if(user.getSid() == null || user.getSid().equals("0")){
            return false;
        }
        return true;
    }
}