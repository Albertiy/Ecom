package com.ecom.dao;
import com.ecom.pojo.Category;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CategoryDao {
    private static Map<Integer, Category> categories = null;

    static{
        categories = new HashMap<Integer, Category>();
        categories.put(1,new Category("c-1","水果蔬菜"));
        categories.put(2,new Category("c-2","日常洗化"));
        categories.put(3,new Category("c-3","家用电器"));
        categories.put(4,new Category("c-4","数码影音"));
        categories.put(5,new Category("c-5","手机电脑"));
    }

    public Collection<Category> getCategoryies(){
        return  categories.values();
    }

    public Category getCategory(Integer id){
        return categories.get(id);
    }
}
