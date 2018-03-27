package com.ecom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import com.ecom.service.*;
import com.ecom.pojo.*;
import com.ecom.utils.*;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@Controller
public class ProductController {

    //根据店铺号获得商品的目录
    @RequestMapping("/store_productlist")
    public void productList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获得 SID
        String sid = request.getParameter("sid");
        String currentPageStr = request.getParameter("currentPage");
        if (currentPageStr == null) {
            currentPageStr = "1";
        }
        int currentPage = Integer.parseInt(currentPageStr);
        int currentCount = 12;

        ProductService service = new ProductService();
        //获得cid
        String cid = request.getParameter("cid");
        if(cid!=null) {
            PageBean pageBean = service.findStoreProductListByCid(sid, cid, currentPage, currentCount);
            request.setAttribute("pageBean", pageBean);
            request.setAttribute("cid", cid);
        }
        else{
            PageBean pageBean = service.findProductListBySid(sid, currentPage, currentCount);
            request.setAttribute("pageBean", pageBean);
        }

        request.getRequestDispatcher("/mystore").forward(request, response);
    }

    @RequestMapping("/store_productinfo")
    public void productInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Product product = new Product();
        String pid = request.getParameter("pid");
        ProductService service = new ProductService();
        //调用service查询具体商品信息
        product = service.findProductInfoByPid(pid);

        request.setAttribute("product", product);
        request.getRequestDispatcher("/myproduct_info").forward(request, response);
    }

    @RequestMapping("/downproduct")
    public void downProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = new Product();
        String pid = request.getParameter("pid");
        ProductService service = new ProductService();
        //调用service下架商品
        product = service.downProduct(pid);

        request.setAttribute("product", product);
        request.getRequestDispatcher("/myproduct_info").forward(request, response);
    }

    @RequestMapping("/upproduct")
    public void upProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = new Product();
        String pid = request.getParameter("pid");
        ProductService service = new ProductService();
        //调用service下架商品
        product = service.upProduct(pid);

        request.setAttribute("product", product);
        request.getRequestDispatcher("/myproduct_info").forward(request, response);
    }

    //修改商品信息
    @RequestMapping("/modifyproduct")
    public void modifyProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = new Product();
        String pid = request.getParameter("pid");
        System.out.println(pid);
        String pname = request.getParameter("pname");
        String sprice = request.getParameter("price");
        Float price = Float.parseFloat(sprice);
        String spstorage = request.getParameter("pstorage");
        int pstorage = Integer.parseInt(spstorage);
        String pdesc = request.getParameter("pdesc");
        ProductService service = new ProductService();
        //调用service更新产品信息
        product = service.modifyProduct(pid, pname, price, pstorage,pdesc);

        request.setAttribute("product", product);
        request.getRequestDispatcher("/myproduct_info").forward(request, response);
    }

    //上传图片
    @RequestMapping("/uploadimage")
    public void uploadImage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pwout = response.getWriter();
        String savePath = null;
        String tempPath = request.getServletContext().getRealPath("/Temp");
        String redirect="";
        boolean inputSuccess = false;
        String url = "";
        String pid = null;
        ProductService service = new ProductService();
        Product product = new Product();

        //传统方法无法获取到request的参数！
        if (request.getParameter("cpath") != null) {

	            savePath = request.getParameter("cpath");
	            savePath = request.getServletContext().getRealPath("/images/Files")+"/"+savePath;
	            System.out.println("Custom savePath: "+savePath);
	            //Attribute传的参数没有自动转换
	            //request.setAttribute("redirect", "videolist.jsp?path="+savePath);
        } else {
            savePath = request.getServletContext().getRealPath("/images/Files");
            System.out.println("savePath: "+savePath);
        }
        File tmpFile = new File(tempPath);
        if (!tmpFile.exists() && !tmpFile.isDirectory()) {//创建临时文件夹
            tmpFile.mkdir();
        }
        File saveFile = new File(savePath);
        if (!saveFile.exists() && !saveFile.isDirectory()) {//创建保存文件夹
            saveFile.mkdir();
        }

        System.out.println("tempPath: "+tempPath);
        String message="";
        System.out.println("========== Handle Servlet Beginning ===========");
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //设置工厂的缓冲区的大小
            //设定缓冲区的大小为100KB，如果不指定，那么缓冲区的大小默认是10KB
            factory.setSizeThreshold(1024 * 100);
            //设置上传时生成的临时文件的保存目录
            factory.setRepository(tmpFile);
            //2、创建文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            //监听文件上传进度
            upload.setProgressListener((long pBytesRead, long pContentLength, int arg2) -> {
                System.out.println("文件大小为：" + pContentLength + ",当前已处理：" + pBytesRead);
            });
            //解决中文乱码
            upload.setHeaderEncoding("UTF-8");
            //判断是否是表单提供的文件
            if (!ServletFileUpload.isMultipartContent(request)) {
                //按照传统方式获取数据
                System.out.println("============== request 无效 ================");
                pwout.print("无效的上传请求<br>");
                return;
            }
            //设置上传单个文件的大小的最大值，目前是设置为128*1024*1024字节，也就是128MB
            upload.setFileSizeMax(128 * 1024 * 1024);
            //设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和，目前设置为512MB
            upload.setSizeMax(1024 * 1024 * 512);

            //☆☆☆☆☆☆☆☆参数解析中☆☆☆☆☆☆☆☆☆
            System.out.println("☆☆☆☆☆☆☆☆ 参数解析中 ☆☆☆☆☆☆☆☆☆");
            //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，
            //	  每一个FileItem对应一个Form表单的输入项
            String imagePid = null;
            List<FileItem> list = upload.parseRequest(request);
            for (FileItem item : list) {
                //如果封装的是普通输入项文本数据
                if (item.isFormField()) {
                    String fieldname = item.getFieldName();
                    String value = item.getString("UTF-8");
                    System.out.println("[参数]：\t" + fieldname + " = " + value);
                    if(fieldname.equals("image_pid")){
                        imagePid = value;
                        pid=value;
                        System.out.println("PID: "+pid);
                    }
                    //pwout.print(fieldname + " = " + value);
                    //获得了id
                    //owner_id = Integer.parseInt(value);
                    //savePath = savePath + owner_id;
                } else {        //上传的是文件
                    //获取文件名
                    String filename = item.getName();
                    System.out.println("原文件名："+filename);
                    if (filename == null || filename.trim().equals("")) {
                        System.out.println("[参数]：\t上传文件为空！");
                        message=message.concat("\n\t| 某一文件输入为空！\n");
                        continue;
                    }
                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                    filename = filename.substring(filename.lastIndexOf("\\") + 1);
                    //pwout.print("文件名：" + filename + "<br>");
                    message=message.concat("文件名：" + filename + "\n");
                    //如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
                    //String fileExtName = filename.substring(filename.lastIndexOf(".") + 1);
                    //后缀名统一为jpg
                    String fileExtName = "jpg";
                    System.out.println("上传的文件的扩展名是：" + fileExtName);
                    //pwout.print("扩展名：" + fileExtName + "<br>");
                    message=message.concat("扩展名：" + fileExtName + "\n");
                    if(imagePid != null){
                        System.out.println("保存到服务器的文件名是：" + imagePid+"."+fileExtName);
                        filename = imagePid+"."+fileExtName;
                    }
                    message=message.concat("保存到服务器的文件名是：" + filename + "\n");
                    //用来传递的url
                    url = filename;
                    FileOutputStream out;
                    //获取item中上传文件的输入流
                    try (InputStream in = item.getInputStream()) {
                        //得到文件保存的名称
                        //String saveFilename = makeFileName(filename);
                        String saveFilename = filename;
                        //得到文件的保存目录
                        //String realSavePath = makePath(saveFilename, savePath);
                        String realSavePath = savePath;
                        System.out.println("目标文件夹：" + realSavePath);
                        //创建一个文件输出流
                        out = new FileOutputStream(realSavePath + "\\" + saveFilename);
                        //创建一个缓冲区
                        byte buffer[] = new byte[1024];
                        //判断输入流中的数据是否已经读完的标识
                        int len = 0;
                        //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                        while ((len = in.read(buffer)) > 0) {
                            //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                            out.write(buffer, 0, len);
                        }
                    }
                    out.close();
                    item.delete();
                    message=message.concat("文件上传成功！！！");
                    inputSuccess = true;
                    //pwout.print("上传结果：文件上传成功！<br>");
                    //filename是之前的文件名，完整路径中的文件名是修改过的防重复名
                    //n1.newFile(owner_id, filename, realSavePath+"\\"+saveFilename);
                }
            }
        } catch (FileUploadBase.FileSizeLimitExceededException e) {
            //pwout.print("单个文件超出最大值！！！");
            message=message.concat("\n单个文件超出最大值！！！\n");
            //request.setAttribute("message", "单个文件超出最大值！！！");
            //request.getRequestDispatcher("/message.jsp").forward(request, response);
            //return;
        } catch (FileUploadBase.SizeLimitExceededException e) {
            //pwout.print("上传文件的总的大小超出限制的最大值！！！");
            message=message.concat("\n上传文件的总的大小超出限制的最大值！！！\n");
            //request.setAttribute("message", "上传文件的总的大小超出限制的最大值！！！");
            //request.getRequestDispatcher("/message.jsp").forward(request, response);
            //return;
        } catch (IOException | FileUploadException e) {
            message=message.concat("\n文件上传失败！\n");
            e.printStackTrace();
        }
        System.out.println("--------------上传结果--------------\n" + message);

        //这个跳转不改变URl，导致刷新后Servlet会再次执行
        //request.getRequestDispatcher("/testInput.jsp"+redirect).forward(request, response);
        //用这个！
        if(inputSuccess) {
            product = service.modifyProductImage(pid);
            request.setAttribute("product", product);
            request.getRequestDispatcher("/myproduct_info").forward(request, response);
        }
        else{
            product = service.findProductInfoByPid(pid);
            request.setAttribute("product", product);
            request.getRequestDispatcher("/myproduct_info").forward(request, response);
        }
    }

    //添加商品
    @RequestMapping("/addproduct")
    public void addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pwout = response.getWriter();
        String savePath = null;
        String tempPath = request.getServletContext().getRealPath("/Temp");
        String redirect="";
        boolean inputSuccess = false;
        String url = "";
        String sid = null;
        String pid = CommonUtils.getUUID();
        String pimage = "images/Files/"+pid+".jpg";
        String pname = null;
        Float price = Float.parseFloat("0");
        int pstorage = 0;
        String cid = null;
        String pdesc=null;
        ProductService service = new ProductService();

        //传统方法无法获取到request的参数！
        if (request.getParameter("cpath") != null) {

            savePath = request.getParameter("cpath");
            savePath = request.getServletContext().getRealPath("/images/Files")+"/"+savePath;
            System.out.println("Custom savePath: "+savePath);
            //Attribute传的参数没有自动转换
            //request.setAttribute("redirect", "videolist.jsp?path="+savePath);
        } else {
            savePath = request.getServletContext().getRealPath("/images/Files");
            System.out.println("savePath: "+savePath);
        }
        File tmpFile = new File(tempPath);
        if (!tmpFile.exists() && !tmpFile.isDirectory()) {//创建临时文件夹
            tmpFile.mkdir();
        }
        File saveFile = new File(savePath);
        if (!saveFile.exists() && !saveFile.isDirectory()) {//创建保存文件夹
            saveFile.mkdir();
        }

        System.out.println("tempPath: "+tempPath);
        String message="";
        System.out.println("========== Handle Servlet Beginning ===========");
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //设置工厂的缓冲区的大小
            //设定缓冲区的大小为100KB，如果不指定，那么缓冲区的大小默认是10KB
            factory.setSizeThreshold(1024 * 100);
            //设置上传时生成的临时文件的保存目录
            factory.setRepository(tmpFile);
            //2、创建文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            //监听文件上传进度
            upload.setProgressListener((long pBytesRead, long pContentLength, int arg2) -> {
                System.out.println("文件大小为：" + pContentLength + ",当前已处理：" + pBytesRead);
            });
            //解决中文乱码
            upload.setHeaderEncoding("UTF-8");
            //判断是否是表单提供的文件
            if (!ServletFileUpload.isMultipartContent(request)) {
                //按照传统方式获取数据
                System.out.println("============== request 无效 ================");
                pwout.print("无效的上传请求<br>");
                return;
            }
            //设置上传单个文件的大小的最大值，目前是设置为128*1024*1024字节，也就是128MB
            upload.setFileSizeMax(128 * 1024 * 1024);
            //设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和，目前设置为512MB
            upload.setSizeMax(1024 * 1024 * 512);

            //☆☆☆☆☆☆☆☆参数解析中☆☆☆☆☆☆☆☆☆
            System.out.println("☆☆☆☆☆☆☆☆ 参数解析中 ☆☆☆☆☆☆☆☆☆");
            //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，
            //	  每一个FileItem对应一个Form表单的输入项
            String imagePid = null;
            List<FileItem> list = upload.parseRequest(request);
            for (FileItem item : list) {
                //如果封装的是普通输入项文本数据
                if (item.isFormField()) {
                    String fieldname = item.getFieldName();
                    String value = item.getString("UTF-8");
                    System.out.println("[参数]：\t" + fieldname + " = " + value);
                    if(fieldname.equals("sid")){
                        sid = value;
                        System.out.println("sid: " + sid);
                    }else if(fieldname.equals("pname")){
                        pname = value;
                        System.out.println("pname: " + pname);
                    }else if(fieldname.equals("price")){
                        price = Float.parseFloat(value);
                        System.out.println("price: " + price);
                    }else if(fieldname.equals("pstorage")) {
                        pstorage = Integer.parseInt(value);
                        System.out.println("pstorage: " + pstorage);
                    }else if(fieldname.equals("category")) {
                        if(value.equals("移动通讯")){
                            cid="1";
                        }else if(value.equals("数码")){
                            cid="2";
                        }else if(value.equals("家电")){
                            cid="3";
                        }
                        System.out.println("category: " + cid);
                    }else if(fieldname.equals("pdesc")) {
                        pdesc = value;
                        System.out.println("pdesc: " + pdesc);
                    }

                } else {        //上传的是文件
                    //获取文件名
                    String filename = item.getName();
                    System.out.println("原文件名："+filename);
                    if (filename == null || filename.trim().equals("")) {
                        System.out.println("[参数]：\t上传文件为空！");
                        message=message.concat("\n\t| 某一文件输入为空！\n");
                        continue;
                    }
                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                    filename = filename.substring(filename.lastIndexOf("\\") + 1);
                    //pwout.print("文件名：" + filename + "<br>");
                    message=message.concat("文件名：" + filename + "<br>");
                    //如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
                    //String fileExtName = filename.substring(filename.lastIndexOf(".") + 1);
                    //后缀名统一为jpg
                    String fileExtName = "jpg";
                    System.out.println("上传的文件的扩展名是：" + fileExtName);
                    //pwout.print("扩展名：" + fileExtName + "<br>");
                    message=message.concat("扩展名：" + fileExtName + "<br>");
                    if(pid != null){
                        System.out.println("保存到服务器的文件名是：" + pid+"."+fileExtName);
                        filename = pid+"."+fileExtName;
                    }
                    message=message.concat("保存到服务器的文件名是：" + filename + "<br>");
                    //用来传递的url
                    url = filename;
                    FileOutputStream out;
                    //获取item中上传文件的输入流
                    try (InputStream in = item.getInputStream()) {
                        //得到文件保存的名称
                        //String saveFilename = makeFileName(filename);
                        String saveFilename = filename;
                        //得到文件的保存目录
                        //String realSavePath = makePath(saveFilename, savePath);
                        String realSavePath = savePath;
                        System.out.println("目标文件夹：" + realSavePath);
                        //创建一个文件输出流
                        out = new FileOutputStream(realSavePath + "\\" + saveFilename);
                        //创建一个缓冲区
                        byte buffer[] = new byte[1024];
                        //判断输入流中的数据是否已经读完的标识
                        int len = 0;
                        //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                        while ((len = in.read(buffer)) > 0) {
                            //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                            out.write(buffer, 0, len);
                        }
                    }
                    out.close();
                    item.delete();
                    message=message.concat("文件上传成功！");
                    inputSuccess = true;
                    //pwout.print("上传结果：文件上传成功！<br>");
                    //filename是之前的文件名，完整路径中的文件名是修改过的防重复名
                    //n1.newFile(owner_id, filename, realSavePath+"\\"+saveFilename);
                }
            }
        } catch (FileUploadBase.FileSizeLimitExceededException e) {
            //pwout.print("单个文件超出最大值！！！");
            message=message.concat("\n单个文件超出最大值！！！\n");
            //request.setAttribute("message", "单个文件超出最大值！！！");
            //request.getRequestDispatcher("/message.jsp").forward(request, response);
            //return;
        } catch (FileUploadBase.SizeLimitExceededException e) {
            //pwout.print("上传文件的总的大小超出限制的最大值！！！");
            message=message.concat("\n上传文件的总的大小超出限制的最大值！！！\n");
            //request.setAttribute("message", "上传文件的总的大小超出限制的最大值！！！");
            //request.getRequestDispatcher("/message.jsp").forward(request, response);
            //return;
        } catch (IOException | FileUploadException e) {
            message=message.concat("\n文件上传失败！\n");
            e.printStackTrace();
        }
        System.out.println("--------------上传结果--------------\n" + message);

        //这个跳转不改变URl，导致刷新后Servlet会再次执行
        //request.getRequestDispatcher("/testInput.jsp"+redirect).forward(request, response);
        //用这个！
        if(inputSuccess) {
            System.out.println("数据库写入数据！");
            service.addProduct(sid, pid, pname, price, pstorage, cid, pimage, pdesc);
            request.getRequestDispatcher("/store_productlist?sid="+sid+"&cid="+cid).forward(request, response);
        }
        else{
            request.getRequestDispatcher("/mystore").forward(request, response);
        }
    }
}
