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
    private String sql1=null;
    private String sql2=null;
    public static ResultSet rs;

    /*
    * 未发货订单检索，包含搜索、排序以及分页后的返回值。
    * */
    public OrderPageBean<Order> findUnfilledOrders(String sid, OrderPageBean<Order> orderPageBean) throws SQLException {
        //搜索参数
        String search = (orderPageBean.getSearch().trim().equals("")?"":orderPageBean.getSearch());
        //排序参数
        String sort = orderPageBean.getSort();
        String order = orderPageBean.getOrder();
        ArrayList<Order> list = new ArrayList<Order>();
        try {
            conn = JdbcUtils.getConnection();
            //为了第二次计数total查询，先不写入查询返回内容
            sql = " from _order,users where _order.sid = ? and _order.state = 0 or 1";
            if(!search.equals("")){//搜索功能
                sql += " and (";
                sql += " _order.create_time LIKE binary '%"+search+"%'";
                sql += " or _order.pay_time LIKE binary '%"+search+"%'";
                sql += " or _order.delivery_time LIKE binary '%"+search+"%'";
                sql += " or _order.finish_time LIKE binary '%"+search+"%'";
                sql += " or _order.consignee LIKE binary '%"+search+"%'";
                sql += " or _order.address LIKE binary '%"+search+"%'";
                sql += " or _order.ps LIKE binary '%"+search+"%'";
                try {//数字不能用LIKE方法，只能转换为MYSQL的char然后，按照string的LIKE的方法搜索
                    int i = Integer.parseInt(search);
                    sql += " or cast(_order.oid as char) LIKE '%"+i+"%'";
                    sql += " or cast(_order.sid as char) LIKE '%"+i+"%'";
                    sql += " or cast(_order.uid as char) LIKE '%"+i+"%'";
                    sql += " or cast(_order.phone as char) LIKE '%"+i+"%'";
                    sql += " or cast(_order.total as char) LIKE '%"+i+"%'";
                }catch(Exception e){
                    System.out.println("search 不是 数字");
                }
                sql += ")";
            }
            sql+=" and _order.uid = users.uid";
            if(!sort.equals("")){//排序功能
                sql += " order by _order." + sort + " " +order;
            }
            sql1 = "select _order.*,users.nickname"+sql+" limit ?,?";
            System.out.println("sql1语句："+sql1);
            pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1, sid);
            pstmt.setInt(2, orderPageBean.getOffset());
            pstmt.setInt(3, orderPageBean.getLimit());
            rs = pstmt.executeQuery();
            System.out.println("【数据库】通过【sid】查询【未发货】订单【成功】！");
            while (rs.next()) {
                Order tempOrder = new Order();
                tempOrder.setOid(rs.getString("oid"));
                System.out.println("oid: " + tempOrder.getOid());
                tempOrder.setSid(rs.getString("sid"));
                tempOrder.setUid(rs.getString("uid"));
                tempOrder.setCreateTime(rs.getString("create_time"));
                tempOrder.setPayTime(rs.getString("pay_time"));
                //既然是未发货订单，就不写入发货与完成时间了吧
                tempOrder.setTotal(rs.getInt("total"));
                tempOrder.setConsignee(rs.getString("consignee"));
                tempOrder.setPhone(rs.getString("phone"));
                tempOrder.setAddress(rs.getString("address"));
                tempOrder.setState(rs.getString("state"));
                tempOrder.setPs(rs.getString("ps"));
                tempOrder.setOdata(rs.getString("odata"));
                tempOrder.setNickName(rs.getString("nickname"));
                list.add(tempOrder);
            }
            sql2="select count(*) as total"+sql;
            System.out.println("sql2语句："+sql2);
            pstmt = conn.prepareStatement(sql2);
            pstmt.setString(1, sid);
            rs=pstmt.executeQuery();
            System.out.println("【数据库】通过【sid】查询【未发货】订单【数目】【成功】！");
            int total = list.size();
            if(rs.next()){
                total = rs.getInt("total");
            }
            orderPageBean.setTotal(total);
            System.out.println("total："+total);
            orderPageBean.setList(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderPageBean;
    }
}
