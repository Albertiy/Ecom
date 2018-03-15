package com.ecom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.ecom.service.*;
import com.ecom.pojo.*;


@Controller
public class ProductController {

    //    根据店铺号获得商品的目录
    @RequestMapping("/product_list")
    public void productList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获得 SID
        String sid = request.getParameter("sid");
        System.out.println(sid);

        String currentPageStr = request.getParameter("currentPage");
        if (currentPageStr == null) {
            currentPageStr = "1";
        }
        int currentPage = Integer.parseInt(currentPageStr);
        int currentCount = 12;

        ProductService service = new ProductService();
        ////        获得cid
        /*String cid = request.getParameter("cid");
        if(cid!=null) {
            PageBean pageBean = service.findProductListByCid(cid, currentPage, currentCount);

            request.setAttribute("pageBean", pageBean);
            request.setAttribute("cid", cid);
        }
        else{
            PageBean pageBean = service.findProductListBySid(sid, currentPage, currentCount);

            request.setAttribute("pageBean", pageBean);
        }*/

        PageBean pageBean = service.findProductListBySid(sid, currentPage, currentCount);

        request.setAttribute("pageBean", pageBean);

        request.getRequestDispatcher("/mystore").forward(request, response);
    }

    @RequestMapping("/product_info")
    public void productInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Product product = new Product();
        String pid = request.getParameter("pid");
        ProductService service = new ProductService();
        //调用service查询具体商品信息
        product = service.findProductInfoByPid(pid);

        request.setAttribute("product", product);
        request.getRequestDispatcher("/myproduct_info").forward(request, response);
    }
}
