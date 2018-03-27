package com.ecom.dao;

import com.ecom.pojo.User;
import com.ecom.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;

public class UserDao {


    public Long checkPhone(String phone) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) from users where phone=?";
        Long query = (Long) runner.query(sql, new ScalarHandler(), phone);
        return query;
    }

    public Long checkEmail(String email) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) from users where email=?";
        Long query = (Long) runner.query(sql, new ScalarHandler(), email);
        return query;
    }

    public int register(User user) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into users values(?,?,?,?,?,?,?,?,?,?,?,?)";
        int update = runner.update(sql,
                user.getUid(), user.getSid(), user.getUcode(), user.getState(), user.getPhone(),
                user.getL_pwd(), user.getP_pwd(), user.getNickname(), user.getUname(), user.getGender(),
                user.getBirthday(), user.getEmail());
        return update;
    }

    public void active(String activeCode) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update users set state = ? where ucode = ?";
        runner.update(sql,1,activeCode);
    }

    public User login(String username, String password) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from users where email=? or phone=? and l_pwd=?";
        return runner.query(sql, new BeanHandler<User>(User.class), username,username,password);
    }

    public void changeUserInfo(User user) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update users set nickname = ? ,uname = ?, gender=?, birthday=? where uid = ?";
        runner.update(sql,user.getNickname(),user.getUname(),user.getGender(),user.getBirthday(),user.getUid());
    }

    public void changeUserL_pwd(User user) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update users set l_pwd = ?  where uid = ?";
        runner.update(sql,user.getL_pwd(),user.getUid());

    }

    public User getUserByUid(String uid) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from users where uid=?";
        return runner.query(sql, new BeanHandler<User>(User.class), uid);
    }

    public Long checkL_Pwd(String old_pwd, String uid) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) from users where uid=? and l_pwd=? ";
        Long query = (Long) runner.query(sql, new ScalarHandler(), uid,old_pwd);
        return query;
    }
}
