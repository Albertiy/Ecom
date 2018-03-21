package com.ecom.dao;


import com.ecom.pojo.Product;
import com.ecom.utils.JdbcUtils;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProductDao {
    private	static Connection conn = null;
    private	static PreparedStatement pstmt = null;
    String sql=null;
    public static ResultSet rs;

    public List<Product> findProductByPage(String sid, int index, int currentCount) throws SQLException {
        ArrayList<Product> list = new ArrayList<Product>();
        try {
            conn = JdbcUtils.getConnection();
            sql = "select * from product where sid = ? limit ?,?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,sid);
            pstmt.setInt(2,index);
            pstmt.setInt(3,currentCount);
            rs = pstmt.executeQuery();
            System.out.println("通过sid查询product成功");
            while(rs.next()){
                Product product = new Product();
                product.setPid(rs.getString("pid"));
                product.setSid(rs.getInt("sid"));
                product.setPname(rs.getString("pname"));
                product.setCid(rs.getString("cid"));
                product.setShop_price(rs.getFloat("shop_price"));
                product.setPdesc(rs.getString("pdesc"));
                product.setPsold(rs.getInt("psold"));
                product.setIncompleteness_pturnover(rs.getFloat("incompleteness_pturnover"));
                product.setPturnover(rs.getFloat("pturnover"));
                product.setPimage(rs.getString("pimage"));
                product.setPflag(rs.getInt("pflag"));
                list.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
        /*QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product where sid = ? limit ?,?";
        List<Product> query = runner.query(sql, new BeanListHandler<Product>(Product.class), sid, index, currentCount);
        return query;*/
    }

    public int getCount(String sid) throws SQLException {
        int count = 0;
        try {
            List<Product> list = null;
            conn = JdbcUtils.getConnection();
            sql = "select count(*) from product where sid = ? and pflag = 1 ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,sid);
            rs = pstmt.executeQuery();
            System.out.println("通过sid查询product成功");
            if(rs.next())
            {
                count = rs.getInt(1);
            }
        }
            catch (SQLException e) {
            e.printStackTrace();
        }
        /*QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) from product where sid = ? and pflag = 1 ";
        Long query = (Long) runner.query(sql, new ScalarHandler(),sid);
        return query.intValue();*/
        return count;
    }

    //通过pid访问数据库查询商品
    public Product findProductByPid(String pid) throws SQLException {
        Product product = new Product();
        try {
            conn = JdbcUtils.getConnection();
            sql = "select * from product where pid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,pid);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                System.out.println("通过pid查询product成功");
                product.setPid(rs.getString("pid"));
                product.setSid(rs.getInt("sid"));
                product.setPname(rs.getString("pname"));
                product.setCid(rs.getString("cid"));
                product.setShop_price(rs.getFloat("shop_price"));
                product.setPdesc(rs.getString("pdesc"));
                product.setPsold(rs.getInt("psold"));
                product.setIncompleteness_pturnover(rs.getFloat("incompleteness_pturnover"));
                product.setPturnover(rs.getFloat("pturnover"));
                product.setPimage(rs.getString("pimage"));
                product.setPflag(rs.getInt("pflag"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    //下架商品
    public Product downProduct(String pid) throws SQLException {
        Product product = new Product();
        try {
            conn = JdbcUtils.getConnection();
            sql = "UPDATE product SET pflag = 0 WHERE pid = "+pid;
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            //重新获取此pid的内容
            product = findProductByPid(pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    //上架商品
    public Product upProduct(String pid) throws SQLException {
        Product product = new Product();
        try {
            conn = JdbcUtils.getConnection();
            sql = "UPDATE product SET pflag = 1 WHERE pid = "+pid;
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            //重新获取此pid的内容
            product = findProductByPid(pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    //更改商品信息
    public Product modifyProduct(String pid) throws SQLException {
        Product product = new Product();
        return product;
    }

    //修改商品图片
    public Product modifyProductImage(String pid) throws SQLException {
        Product product = new Product();
        try {
            conn = JdbcUtils.getConnection();
            sql = "UPDATE product SET pimage = 'images/Files/" +pid+".jpg'WHERE pid = "+pid;
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            //重新获取此pid的内容
            product = findProductByPid(pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }
}
