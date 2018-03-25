package com.ecom.dao;

import com.ecom.pojo.Express;

import com.ecom.utils.JdbcUtils;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ExpressDao {
    private static Connection conn = null;
    private static PreparedStatement pstmt = null;
    String sql = null;
    public static ResultSet rs;

    public Express findExpress(String sid, String oid) throws SQLException {
        Express express = new Express();
        try {
            conn = JdbcUtils.getConnection();
            sql = "select * from Express where sid = ? and oid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sid);
            pstmt.setString(2, oid);
            rs = pstmt.executeQuery();
            System.out.println("通过sid,oid查询Express成功");
            while (rs.next()) {
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

    /**
     * 添加物流信息
     */
    public void addExpress(Express express) throws SQLException {
        System.out.println("【ExpressDao】:addExpress");
        conn = JdbcUtils.getConnection();
        String sAddress = "";   //如果没有传过来店铺地址，则需要从数据库获取
        if (express.getsAddress().equals(null) || "".equals(express.getsAddress().trim())) {
            sql = "select saddress from omdb.store where sid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, express.getSid());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                sAddress = rs.getString("saddress");
            }
        }
        //例：insert into omdb.express values('express-1','order-1','1',now(),'江苏省南通市海安县科技软件园8523','江苏宿迁','申通');
        sql = "insert into omdb.express values(?,?,?,now(),?,?,?)";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, express.getEid());
        pstmt.setString(2, express.getOid());
        pstmt.setString(3, express.getSid());
        pstmt.setString(4, express.geteAddress());
        pstmt.setString(5, sAddress);
        pstmt.setString(6, express.geteCompany());
        pstmt.executeUpdate();
        System.out.println("新增Express成功");
    }

}
