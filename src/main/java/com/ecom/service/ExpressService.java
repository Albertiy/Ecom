package com.ecom.service;

import com.ecom.dao.ExpressDao;
import com.ecom.pojo.Express;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;

@Service
public class ExpressService {

    @Resource(name = "expressDao")
    private ExpressDao expressDao;

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

    //新增一个关于oid和sid的物流记录
    public int addExpress(Express express){
        try {
            expressDao.addExpress(express);
            return 200;
        }catch(SQLException e){
            System.out.println("插入Express表失败");
            e.printStackTrace();
            return 1;
        }
    }
}
