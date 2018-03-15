package com.ecom.service;

import com.ecom.dao.ExpressDao;
import com.ecom.pojo.Express;

import java.sql.SQLException;

public class ExpressService {
    //通过店铺id和订单id查询物流信息
    public Express findExpressInfo(String sid,String oid){
        ExpressDao dao = new ExpressDao();
        Express express = new Express();
        try {
            express = dao.findExpress(sid, oid);
            System.out.println("ExpressService执行成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return express;
    }
}
