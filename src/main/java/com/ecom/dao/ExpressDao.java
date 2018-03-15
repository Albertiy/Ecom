package com.ecom.dao;

import com.ecom.pojo.Express;

import com.ecom.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExpressDao {
    private	static Connection conn = null;
    private	static PreparedStatement pstmt = null;
    String sql=null;
    public static ResultSet rs;

    public List<Express> findExpress(String sid, String oid) throws SQLException {
        ArrayList<Express> list = new ArrayList<Express>();
        try {
            conn = JdbcUtils.getConnection();
            sql = "select * from Express where sid = ? and oid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,sid);
            pstmt.setString(2,oid);
            rs = pstmt.executeQuery();
            System.out.println("通过sid,oid查询Express成功");
            while(rs.next()){
                Express express = new Express();
                express.setEid(rs.getInt("eid"));
                express.setSid(rs.getInt("sid"));
                express.setOid(rs.getInt("oid"));
                express.seteTime(rs.getString("eTime"));
                express.seteAddress(rs.getString("eAddress"));
                express.setsAddress(rs.getString("sAddress"));
                express.seteCompany(rs.getString("eCompany"));
                list.add(express);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
