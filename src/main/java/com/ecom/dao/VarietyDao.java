package com.ecom.dao;
import com.ecom.pojo.Variety;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class VarietyDao {
    private static Map<Integer,Variety> varieties = null;

    static{
        varieties = new HashMap<Integer, Variety>();
        varieties.put(1,new Variety("生活电器","厨卫","洗衣机"));
        varieties.put(2,new Variety("生活电器","厨卫","电冰箱"));
        varieties.put(3,new Variety("数码设备","摄影","单反"));
        varieties.put(4,new Variety("数码设备","摄影","卡片机"));
        varieties.put(5,new Variety("个人电脑","配件","显卡"));
    }

    public Collection<Variety> getVarieties(){
        return  varieties.values();
    }

    public Variety getVariety(Integer id){
        return varieties.get(id);
    }
}
