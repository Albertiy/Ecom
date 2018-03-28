package com.ecom.service;

import com.ecom.dao.StoreDao;
import com.ecom.pojo.Store;

import javax.annotation.Resource;
import java.sql.SQLException;

public class StoreService {
    //通过店铺id和订单id查询物流信息
    public Store findExpressInfo(String uid){
        Store store = new Store();
        StoreDao dao = new StoreDao();
        try {
            System.out.println("进入Service uid="+uid);
            store = dao.findStoreDao(uid);
            System.out.println("ExpressService执行成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return store;
    }

}
