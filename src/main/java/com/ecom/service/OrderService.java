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

    public OrderPageBean<Order> findUnFilledOrdersBySid2(String sid,OrderPageBean<Order> orderPageBean){
        System.out.println("【OrderService：开始】");
        try {
            orderPageBean = orderDao.findUnfilledOrders2(sid, orderPageBean);
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

    public OrderPageBean<Order> findUnFilledOrdersBySid3(String sid,OrderPageBean<Order> orderPageBean){
        System.out.println("【OrderService：开始】");
        try {
            orderPageBean = orderDao.findUnfilledOrders3(sid, orderPageBean);
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

    public String findPnameByPid(String pid) {
        String pname = "读取商品名称异常";
        try{
            pname = orderDao.findPname(pid);
        }catch(SQLException se){
            System.out.println("由【pid】："+pid+" 读取【pname】失败");
        }
        return pname;
    }
}
