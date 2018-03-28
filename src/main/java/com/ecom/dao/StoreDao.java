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
}
