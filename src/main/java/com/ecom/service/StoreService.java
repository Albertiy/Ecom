package com.ecom.service;

import com.ecom.dao.StoreDao;
import com.ecom.pojo.Store;

import javax.annotation.Resource;
import java.sql.SQLException;

public class StoreService {
    //通过店铺id和订单id查询物流信息
    public Store findStoreInfo(String uid){
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

    // 修改店铺信息 i=1,修改基本信息；i=2,修改店铺状态
    public int StoreChange(Store store,String i){
        StoreDao dao = new StoreDao();
        System.out.println("进入StoreChange Service");
        try {
            dao.StoreChange(store,i);
            return 200;
        }catch(Exception e){
            System.out.println("修改Store表失败");
            e.printStackTrace();
            return 1;
        }
    }
}
