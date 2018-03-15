package com.ecom.utils;

import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class JdbcUtils {
    private static String driver;
    private static String url;
    private static String user;
    private static String password;
    static Connection conn;

    static{
        try {
            //读取配置文件
//            Properties prop  = new Properties();
//            //使用class读取文件地址
//            InputStream is = JdbcUtils.class.getResourceAsStream( "classpath:db.properties");//就放在本文件夹下
//            prop.load(is);
//            is.close();
//            driver = prop.getProperty("driver");
//            url = prop.getProperty("url");
//            user = prop.getProperty("user");
//            password = prop.getProperty("password");
            driver="com.mysql.jdbc.Driver";
            url="jdbc:mysql://localhost:3306/omdb";
            user="root";
            password="password";
            //1 注册驱动
            Class.forName(driver).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //1 获得连接
    public static Connection getConnection(){
        try {
            //2 获得连接
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create a connection!");
        }
        return conn;
    }

    //2 释放资源
    //1> 参数可能为空
    //2> 调用close方法要抛出异常,确保即使出现异常也能继续关闭
    //3>关闭顺序,需要从小到大
    public static void close(Connection conn , Statement st , ResultSet rs){
        try {
            if(rs!=null){
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                if(st!=null){
                    st.close();
                }
            } catch (SQLException e){
                e.printStackTrace();
            }finally{
                try {
                    if(conn!=null){
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
