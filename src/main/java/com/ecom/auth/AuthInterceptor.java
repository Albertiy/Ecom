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
            if(authUser == null || authSeller.validate() == false)
                return true;
            else{
                User user = (User) request.getSession().getAttribute("user");
                if(user==null){
                    String cookie_username = null;
                    String cookie_password = null;

                    //获取携带用户名和密码cookie
                    Cookie[] cookies = request.getCookies();
                    if(cookies!=null){
                        for(Cookie cookie:cookies){
                            //获得想要的cookie
                            if("cookie_username".equals(cookie.getName())){
                                cookie_username = cookie.getValue();
                            }
                            if("cookie_password".equals(cookie.getName())){
                                cookie_password = cookie.getValue();
                            }
                        }
                    }

                    if(cookie_username!=null&&cookie_password!=null){
                        //去数据库校验该用户名和密码是否正确
                        UserService service = new UserService();
                        try {
                            user = service.login(cookie_username,cookie_password);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        //完成自动登录
                        request.getSession().setAttribute("user", user);

                    }
                }
            }

            //没有声明需要权限,或者声明不验证权限
            if(authSeller == null || authSeller.validate() == false)
                return true;
            else{
                //在这里实现自己的权限验证逻辑

                if(true)//如果验证成功返回true（这里直接写false来模拟验证失败的处理）
                    return true;
                else//如果验证失败
                {
                    //返回到登录界面
                    response.sendRedirect("/Ecom/login");
                    return false;
                }
            }
        }
        else
            return true;
    }
}