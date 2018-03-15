package com.ecom.service;


import com.ecom.dao.ProductDao;
import com.ecom.pojo.PageBean;
import com.ecom.pojo.Product;
import java.sql.SQLException;
import java.util.List;


public class ProductService {

    //通过sid查找product
    public PageBean findProductListBySid(String sid, int currentPage, int currentCount) {

        ProductDao dao = new ProductDao();


//        封装一个PageBean返回给web层
        PageBean<Product> pageBean = new PageBean<Product>();


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


}
