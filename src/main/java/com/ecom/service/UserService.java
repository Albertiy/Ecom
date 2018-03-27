package com.ecom.service;

import com.ecom.dao.UserDao;
import com.ecom.pojo.User;

import java.sql.SQLException;

public class UserService {

    //校验手机号是否存在
    public boolean checkPhone(String phone) {
        UserDao dao = new UserDao();
        Long isExists = 0L;
        try {
            isExists = dao.checkPhone(phone);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isExists > 0 ? true : false;
    }

    //校验邮箱是否存在
    public boolean checkEmail(String email) {
        UserDao dao = new UserDao();
        Long isExists = 0L;
        try {
            isExists = dao.checkEmail(email);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isExists > 0 ? true : false;
    }

    public boolean register(User user) throws SQLException {
        UserDao dao = new UserDao();
        int row = dao.register(user);
        return row > 0 ? true : false;
    }

    //激活
    public void active(String activeCode) {
        UserDao dao = new UserDao();
        try {
            dao.active(activeCode);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //用户登录的方法
    public User login(String username, String password) throws SQLException {
        UserDao dao = new UserDao();
        return dao.login(username, password);
    }


    //修改用户信息
    public void changeUserInfo(User user) {
        UserDao dao = new UserDao();
        try {
            dao.changeUserInfo(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //修改用户登录密码
    public void changeUserL_pwd(User user) {
        UserDao dao = new UserDao();
        try {
            dao.changeUserL_pwd(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //通过Uid获取用户的方法
    public User getUserByUid(String uid) throws SQLException {
        UserDao dao = new UserDao();
        return dao.getUserByUid(uid);
    }

    public boolean checkL_Pwd(String old_pwd, String uid) {
        UserDao dao = new UserDao();
        Long isExists = 0L;
        try {
            isExists = dao.checkL_Pwd(old_pwd,uid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isExists > 0 ? true : false;
    }
}
