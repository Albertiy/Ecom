package com.ecom.controller;

import com.ecom.pojo.Express;
import com.ecom.service.ExpressService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class GustPigController {
    @RequestMapping("/test_GustPig")
    public String test_GustPig(){
        return "test_GustPig";
    }

    @RequestMapping("/expressmodal")
    public String expressmodal(){
        return  "expressmodal";
    }

    @RequestMapping("/findExpress")
    public void fineExpress(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入Controller");
        String sid = request.getParameter("sid");
        String oid = request.getParameter("oid");
        System.out.println(oid+"   "+sid);
        Express express = new Express();
        ExpressService service = new ExpressService();
        //调用service查询物流信息
        express = service.findExpressInfo(sid,oid);
        System.out.println("Controller执行成功，准备向expressmodal.jsp传参");
        request.setAttribute("express", express);
        request.getRequestDispatcher("/expressmodal").forward(request, response);
        System.out.println("传参成功");
    }
}
