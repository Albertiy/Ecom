package com.ecom.dao;

import com.ecom.pojo.Store;
import com.ecom.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StoreDao {
    private static Connection conn = null;
    private static PreparedStatement pstmt = null;
    String sql = null;
    public static ResultSet rs;

    //加载店铺信息
    public Store findStoreDao(String uid) throws SQLException {
        Store store = new Store();
        try {
            System.out.println("进入StoreDao，uid="+uid);
            conn = JdbcUtils.getConnection();
            sql = "select * from store where uid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, uid);
            rs = pstmt.executeQuery();
            System.out.println("通过uid查询Store成功");
            while (rs.next()) {
                store.setSid(rs.getString("sid"));
                store.setUid(rs.getString("uid"));
                store.setSname(rs.getString("sname"));
                store.setSaddress(rs.getString("saddress"));
                store.setIntroduce(rs.getString("introduce"));
                store.setState(rs.getString("state"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return store;
    }

    //修改店铺信息
    public void StoreChange(Store store,String i){
        conn = JdbcUtils.getConnection();
        System.out.println("进入StoreChange Dao");
        System.out.println("标签i="+i);
        if(i.equals("1")) {
            System.out.println("进入Dao 修改基本信息");
            sql = "UPDATE omdb.store  set sname=?,saddress=?,introduce=? where uid=? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, store.getSname());
                pstmt.setString(2, store.getSaddress());
                pstmt.setString(3, store.getIntroduce());
                pstmt.setString(4, store.getUid());
                pstmt.executeUpdate();
                System.out.println("修改Store成功");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("修改Store失败");
            }
        }if(i.equals("2")){//修改店铺状态
            System.out.println("进入Dao 修改店铺状态");
            sql = "UPDATE omdb.store  set state=? where uid=? ";
            try {
                pstmt = conn.prepareStatement(sql);
                if(store.getState().equals("1")) {
                    pstmt.setString(1, "0");
                }
                if(store.getState().equals("0")){
                    pstmt.setString(1, "1");
                }
                pstmt.setString(2, store.getUid());
                pstmt.executeUpdate();
                System.out.println("修改Store成功");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("修改Store失败");
            }
        }
    }
}
