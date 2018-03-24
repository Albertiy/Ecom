package com.ecom.service;

import com.ecom.dao.OrderDao;
import com.ecom.pojo.Order;
import com.ecom.pojo.OrderPageBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;

@Service
public class OrderService {

    @Resource(name = "orderDao")
    private OrderDao orderDao;

    public OrderPageBean<Order> findUnFilledOrdersBySid(String sid,OrderPageBean<Order> orderPageBean){
        System.out.println("【OrderService：开始】");
        try {
            orderPageBean = orderDao.findUnfilledOrders(sid, orderPageBean);
        }catch(SQLException se){
            System.out.println(se);
            System.out.println("【OrderService：失败】获取数据异常！");
        }catch(Exception e){
            System.out.println(e);
            System.out.println("【OrderService：失败】");
        }
        System.out.println("【OrderService：成功】");
        return orderPageBean;
    }
}
