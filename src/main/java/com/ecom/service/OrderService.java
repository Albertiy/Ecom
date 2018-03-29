package com.ecom.service;

import com.ecom.dao.OrderDao;
import com.ecom.pojo.Order;
import com.ecom.pojo.OrderPageBean;
import com.ecom.utils.DataSourceUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

@Service
public class OrderService {

    @Resource(name = "orderDao")
    private OrderDao orderDao;

    public OrderPageBean<Order> findUnFilledOrdersBySid(String sid, OrderPageBean<Order> orderPageBean) {
        System.out.println("【OrderService：开始】");
        try {
            orderPageBean = orderDao.findUnfilledOrders(sid, orderPageBean);
        } catch (SQLException se) {
            System.out.println(se);
            System.out.println("【OrderService：失败】获取数据异常！");
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("【OrderService：失败】");
        }
        System.out.println("【OrderService：成功】");
        return orderPageBean;
    }

    public OrderPageBean<Order> findUnFilledOrdersBySid2(String sid, OrderPageBean<Order> orderPageBean) {
        System.out.println("【OrderService：开始】");
        try {
            orderPageBean = orderDao.findUnfilledOrders2(sid, orderPageBean);
        } catch (SQLException se) {
            System.out.println(se);
            System.out.println("【OrderService：失败】获取数据异常！");
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("【OrderService：失败】");
        }
        System.out.println("【OrderService：成功】");
        return orderPageBean;
    }

    public OrderPageBean<Order> findUnFilledOrdersBySid3(String sid, OrderPageBean<Order> orderPageBean) {
        System.out.println("【OrderService：开始】");
        try {
            orderPageBean = orderDao.findUnfilledOrders3(sid, orderPageBean);
        } catch (SQLException se) {
            System.out.println(se);
            System.out.println("【OrderService：失败】获取数据异常！");
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("【OrderService：失败】");
        }
        System.out.println("【OrderService：成功】");
        return orderPageBean;
    }

    public String findPnameByPid(String pid) {
        String pname = "读取商品名称异常";
        try {
            pname = orderDao.findPname(pid);
        } catch (SQLException se) {
            System.out.println("由【pid】：" + pid + " 读取【pname】失败");
        }
        return pname;
    }

    //    提交订单 将订单的数据和订单项的数据存储到数据库中
    public void submitOrder(List<Order> orderList) {

        OrderDao dao = new OrderDao();
        try {
//            1.开启事务
            DataSourceUtils.startTransaction();
            for (Order order : orderList) {
//            2.调用dao存储order表数据的方法
                dao.addOrders(order);
            }


        } catch (SQLException e) {
            try {
                DataSourceUtils.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                DataSourceUtils.commitAndRelease();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
