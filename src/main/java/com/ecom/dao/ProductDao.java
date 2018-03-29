package com.ecom.dao;


import com.ecom.pojo.Category;
import com.ecom.pojo.Product;
import com.ecom.utils.DataSourceUtils;
import com.ecom.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class ProductDao {
    private	static Connection conn = null;
    private	static PreparedStatement pstmt = null;
    String sql=null;
    public static ResultSet rs;

    public int getCount2(String cid) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) from product where cid = ?  ";
        Long query = (Long) runner.query(sql, new ScalarHandler(),cid);
        return query.intValue();
    }

    //某店铺查询所有商品总数
    public int getCount(String sid) throws SQLException {
        int count = 0;
        try {
            List<Product> list = null;
            conn = JdbcUtils.getConnection();
            sql = "select count(*) from product where sid = ? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,sid);
            rs = pstmt.executeQuery();
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

    //获取某一店铺中某一种类商品总数
    public int getStoreCount(String sid, String cid) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) from product where cid = ?  and sid = ?";
        Long query = (Long) runner.query(sql, new ScalarHandler(),cid,sid);
        return query.intValue();
    }

    //   获得热门商品
    public List<Product> findHotProductList() throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product where is_hot = ? limit ?,?";
        List<Product> query = runner.query(sql, new BeanListHandler<Product>(Product.class), 1, 0, 12);
        return query;
    }

    //   获得热门商品
    public List<Product> findNewProductList() throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product  order by pdate desc limit ?,?";
        List<Product> query = runner.query(sql, new BeanListHandler<Product>(Product.class), 0, 12);
        return query;
    }

    //获得某店铺所有商品
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
            while(rs.next()){
                Product product = new Product();
                product.setPid(rs.getString("pid"));
                product.setSid(rs.getString("sid"));
                product.setPname(rs.getString("pname"));
                product.setCid(rs.getString("cid"));
                product.setPrice(rs.getFloat("price"));
                product.setPdesc(rs.getString("pdesc"));
                product.setPdate(rs.getString("pdate"));
                product.setPstorage(rs.getInt("pstorage"));
                product.setPsold(rs.getInt("psold"));
                product.setUnpturnover(rs.getFloat("unpturnover"));
                product.setPturnover(rs.getFloat("pturnover"));
                product.setPimage(rs.getString("pimage"));
                product.setState(rs.getString("state"));
                product.setIs_hot(rs.getInt("is_hot"));
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

    //获得某店铺某一种商品
    public List<Product> findCidProductByPage(String sid, String cid, int index, int currentCount) throws SQLException {
        ArrayList<Product> list = new ArrayList<Product>();
        try {
            conn = JdbcUtils.getConnection();
            sql = "select * from product where sid = ? and cid = ? limit ?,?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,sid);
            pstmt.setString(2,cid);
            pstmt.setInt(3,index);
            pstmt.setInt(4,currentCount);
            rs = pstmt.executeQuery();
            while(rs.next()){
                Product product = new Product();
                product.setPid(rs.getString("pid"));
                product.setSid(rs.getString("sid"));
                product.setPname(rs.getString("pname"));
                product.setCid(rs.getString("cid"));
                product.setPrice(rs.getFloat("price"));
                product.setPdesc(rs.getString("pdesc"));
                product.setPdate(rs.getString("pdate"));
                product.setPstorage(rs.getInt("pstorage"));
                product.setPsold(rs.getInt("psold"));
                product.setUnpturnover(rs.getFloat("unpturnover"));
                product.setPturnover(rs.getFloat("pturnover"));
                product.setPimage(rs.getString("pimage"));
                product.setState(rs.getString("state"));
                product.setIs_hot(rs.getInt("is_hot"));
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
                product.setPid(rs.getString("pid"));
                product.setSid(rs.getString("sid"));
                product.setPname(rs.getString("pname"));
                product.setCid(rs.getString("cid"));
                product.setPrice(rs.getFloat("price"));
                product.setPdesc(rs.getString("pdesc"));
                product.setPdate(rs.getString("pdate"));
                product.setPstorage(rs.getInt("pstorage"));
                product.setPsold(rs.getInt("psold"));
                product.setUnpturnover(rs.getFloat("unpturnover"));
                product.setPturnover(rs.getFloat("pturnover"));
                product.setPimage(rs.getString("pimage"));
                product.setState(rs.getString("state"));
                product.setIs_hot(rs.getInt("is_hot"));
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
            sql = "UPDATE product SET state = 0 WHERE pid = "+pid;
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
            sql = "UPDATE product SET state = 1 WHERE pid = "+pid;
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
    public Product modifyProduct(String pid, String pname, Float price, int pstrorage, String pdesc) throws SQLException {
        Product product = new Product();
        try {
            conn = JdbcUtils.getConnection();
            sql = "UPDATE product SET pname ='"+pname+"', price ="+price+", pstorage = "+pstrorage+", pdesc = '"+pdesc+"' WHERE pid = '"+pid+"'";
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            //重新获取此pid的内容
            product = findProductByPid(pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    //修改商品图片
    public Product modifyProductImage(String pid) throws SQLException {
        Product product = new Product();
        try {
            conn = JdbcUtils.getConnection();
            sql = "UPDATE product SET pimage = 'images/Files/" +pid+".jpg'WHERE pid = '"+pid+"'";
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            //重新获取此pid的内容
            product = findProductByPid(pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public List<Category> findAllCategroy() throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from category";
        List<Category> query = runner.query(sql, new BeanListHandler<Category>(Category.class));
        return query;
    }

    public List<Product> findProductByPage2(String cid, int index, int currentCount) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product where cid = ? limit ?,?";
        List<Product> query = runner.query(sql, new BeanListHandler<Product>(Product.class), cid, index, currentCount);
        return query;
    }

    public Product findProductByPid2(String pid) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product where pid = ? ";
        Product query = runner.query(sql, new BeanHandler<Product>(Product.class), pid);
        return query;
    }

    //添加商品
    public void addProduct(String sid, String pid, String pname, Float price, int pstorage, String cid, String pimage, String pdesc)throws SQLException{

        SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //规范时间
        try {
            conn = JdbcUtils.getConnection();
            String sql = null;
            sql = "insert into product values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,pid);
            pstmt.setString(2,sid);
            pstmt.setString(3,pname);
            pstmt.setString(4,cid);
            pstmt.setFloat(5,price);
            pstmt.setString(6,pdesc);
            pstmt.setString(7,fmt.format(System.currentTimeMillis()));
            pstmt.setInt(8,pstorage);
            pstmt.setInt(9,0);
            pstmt.setFloat(10,0);
            pstmt.setFloat(11,0);
            pstmt.setString(12,pimage);
            pstmt.setString(13,"0");
            pstmt.setInt(14,1);
            int s = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delNumByPid(String pid, int buyNum) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update product set pstorage=pstorage- ?  where pid = ?";
        runner.update(sql,buyNum,pid);
    }
}
