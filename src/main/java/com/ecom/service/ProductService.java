package com.ecom.service;


import com.ecom.dao.ProductDao;
import com.ecom.pojo.PageBean;
import com.ecom.pojo.Product;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;


public class ProductService {

    //通过sid查找product
    public PageBean findProductListBySid(String sid, int currentPage, int currentCount) {

//        封装一个PageBean返回给web层
        PageBean<Product> pageBean = new PageBean<Product>();
        ProductDao dao = new ProductDao();

//        1.封装当前页
        pageBean.setCurrentPage(currentPage);
//        2.封装每页显示的条数
        pageBean.setCurrentCount(currentCount);
//        3.封装总条数
        int totalCount = 0;
        try {
            totalCount = dao.getCount(sid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pageBean.setTotalCount(totalCount);
//        4.封装总页数
        int totalPage = (int) Math.ceil(1.0 * totalCount / currentCount);
        pageBean.setTotalPage(totalPage);
//        5.当前页显示的数据
//        当前页与起始索引index的关系
        int index = (currentPage - 1) * currentCount;
        List<Product> list = null;
        try {
            list = dao.findProductByPage(sid, index, currentCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pageBean.setList(list);
        return pageBean;
    }

    //通过Pid查询商品信息
    public Product findProductInfoByPid(String pid){
        Product product = new Product();
        ProductDao dao = new ProductDao();
        //调用dao借口访问数据库查询商品
        try {
            product = dao.findProductByPid(pid);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return product;
    }

    //下架商品
    public Product downProduct(String pid){
        Product product = new Product();
        ProductDao dao = new ProductDao();
        //调用dao下架
        try {
            product = dao.downProduct(pid);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return product;
    }

    //上架商品
    public Product upProduct(String pid){
        Product product = new Product();
        ProductDao dao = new ProductDao();
        //调用dao下架
        try {
            product = dao.upProduct(pid);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return product;
    }

    //更改商品信息
    public Product modifyProduct(String pid, String pname, Float price, int pstrorage){
        Product product = new Product();
        ProductDao dao = new ProductDao();
        //调用dao下架
        try {
            product = dao.modifyProduct(pid, pname, price, pstrorage);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return product;
    }

    //更改商品图片信息
    public Product modifyProductImage(String pid){
        Product product = new Product();
        ProductDao dao = new ProductDao();
        //调用dao修改数据库保存的图片路径
        try {
            product = dao.modifyProductImage(pid);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return product;
    }
}
