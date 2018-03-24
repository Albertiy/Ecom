package com.ecom.dao;

import com.ecom.pojo.Express;

import com.ecom.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpressDao {
    private	static Connection conn = null;
    private	static PreparedStatement pstmt = null;
    String sql=null;
    public static ResultSet rs;

    public Express findExpress(String sid, String oid) throws SQLException {
        Express express = new Express();
        try {
            conn = JdbcUtils.getConnection();
            sql = "select * from Express where sid = ? and oid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,sid);
            pstmt.setString(2,oid);
            rs = pstmt.executeQuery();
            System.out.println("通过sid,oid查询Express成功");
            while(rs.next()){
                express.setEid(rs.getString("eid"));
                express.setSid(rs.getString("sid"));
                express.setOid(rs.getString("oid"));
                express.seteTime(rs.getString("eTime"));
                express.seteAddress(rs.getString("eAddress"));
                express.setsAddress(rs.getString("sAddress"));
                express.seteCompany(rs.getString("eCompany"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return express;
    }

}
