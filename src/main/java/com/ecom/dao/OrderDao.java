package com.ecom.dao;

import com.ecom.pojo.Order;
import com.ecom.pojo.OrderPageBean;
import com.ecom.utils.JdbcUtils;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class OrderDao {
    private	static Connection conn = null;
    private	static PreparedStatement pstmt = null;
    private String sql=null;
    public static ResultSet rs;

    /*
    * 未发货订单检索，包含搜索、排序以及分页后的返回值。
    * */
    public OrderPageBean<Order> findUnfilledOrders(int sid, OrderPageBean<Order> orderPageBean) throws SQLException {
        //搜索参数
        String search = (orderPageBean.getSearch().trim().equals("")?"":orderPageBean.getSearch());
        //排序参数
        String sort = orderPageBean.getSort();
        String order = orderPageBean.getOrder();
        ArrayList<Order> list = new ArrayList<Order>();
        try {
            conn = JdbcUtils.getConnection();
            sql = "select * from _order where sid = ? and state = 0 or 1";
            if(!search.equals("")){//搜索功能
                sql += " and (";
                sql += " create_time LIKE binary '%"+search+"%'";
                sql += " or pay_time LIKE binary '%"+search+"%'";
                sql += " or delivery_time LIKE binary '%"+search+"%'";
                sql += " or finish_time LIKE binary '%"+search+"%'";
                sql += " or consignee LIKE binary '%"+search+"%'";
                sql += " or address LIKE binary '%"+search+"%'";
                sql += " or ps LIKE binary '%"+search+"%'";
                try {//数字不能用LIKE方法，只能转换为MYSQL的char然后，按照string的LIKE的方法搜索
                    int i = Integer.parseInt(search);
                    sql += " or cast(oid as char) LIKE '%"+i+"%'";
                    sql += " or cast(sid as char) LIKE '%"+i+"%'";
                    sql += " or cast(uid as char) LIKE '%"+i+"%'";
                    sql += " or cast(phone as char) LIKE '%"+i+"%'";
                    sql += " or cast(amount as char) LIKE '%"+i+"%'";
                }catch(Exception e){
                    System.out.println("search 不是 数字");
                }
                sql += ")";
            }
            if(!sort.equals("")){//排序功能
                sql += " order by " + sort + " " +order;
            }
            sql +=" limit ?,?";
            System.out.println("sql语句："+sql);
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, sid);
            pstmt.setInt(2, orderPageBean.getOffset());
            pstmt.setInt(3, orderPageBean.getLimit());
            rs = pstmt.executeQuery();
            System.out.println("【数据库】通过【sid】查询【未发货】订单【成功】！");
            while (rs.next()) {
                Order tempOrder = new Order();
                tempOrder.setOid(rs.getInt("oid"));
                System.out.println("oid: " + tempOrder.getOid());
                tempOrder.setSid(rs.getInt("sid"));
                tempOrder.setUid(rs.getInt("uid"));
                tempOrder.setCreateTime(rs.getString("create_time"));
                tempOrder.setPayTime(rs.getString("pay_time"));
                //既然是未发货订单，就不写入发货与完成时间了吧
                tempOrder.setAmount(rs.getInt("amount"));
                tempOrder.setConsignee(rs.getString("consignee"));
                tempOrder.setPhone(rs.getString("phone"));
                tempOrder.setAddress(rs.getString("address"));
                tempOrder.setState(rs.getString("state"));
                tempOrder.setPs(rs.getString("ps"));
                tempOrder.setOdata(rs.getString("odata"));
                list.add(tempOrder);
            }
            orderPageBean.setTotal(list.size());
            System.out.println("total："+list.size());
            orderPageBean.setList(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderPageBean;
    }
}
