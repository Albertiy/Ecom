<%--
  Created by IntelliJ IDEA.
  User: YUAN YUE
  Date: 2018/3/13
  Time: 14:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.Enumeration" %>
<%@ page import="com.ecom.pojo.Order" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.context.support.ClassPathXmlApplicationContext" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.ecom.pojo.Express" %>
<%@ page import="com.ecom.pojo.OrderData" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    System.out.println("[getjson.jsp]");

    //1. 类实例化
    //Express express = new Express();
    //2. 得到配置创建的对象 gson
    ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext1.xml");
    Gson gson = (Gson)context.getBean("gson");
    //3. 将Order对象转化为JSON
    //String json = gson.toJson(order);
    //4. 没有值的属性不会被添加到Json中
    int total = 50;

    List<Order> orderList=new ArrayList<Order>();
    for(int i=1;i<=total;i++){
        Order order=new Order();
        order.setOid(i);
        System.out.println("oid: " + order.getOid());
        order.setSid(123);
        order.setUid(i+100000);
        order.setAmount(i*100);
        order.setConsignee("小王");
        order.setAddress("小王隔壁家");
        order.setPhone("11235712193");
        order.setPs("我想给她一个惊喜");
        OrderData orderData = new OrderData();
        orderData.setPid(i);
        orderData.setShopPrice(i*100%17*50);
        orderData.setPcount(i%5);
        orderData.setSubTotal((i%3)*(i%5));
        order.setOdata(gson.toJson(orderData));
        orderList.add(order);
    }
    System.out.println(orderList.toString());
    //List的转换
    String order_list = gson.toJson(orderList);
    //System.out.println(order_list);
    String result = "{\n" +" \"total\":"+total+", \n" + " \"rows\":\n";
    result += order_list;
    result += "\n" + "}";

    System.out.println("JSON输出： \n"+result);


    /*
     * js中是 params.limit和total和offset，
     * 就是表单POST提交的属性,都存在Params里
     * Bootstrap-table的参数表：
     * request.getParameter：
     * search,order,offset,limit,other
     * */
    //分页时需要获取记录总数，键值为 total，我们自己生成
    //PHP: $result["total"] = count($list_temp);
    //根据传递过来的分页偏移量和分页量截取模拟分页 rows 可以根据前端的 dataField 来设置
    //PHP: $result["rows"] = array_slice($list_temp, $_POST['offset'], $_POST['limit']);
    response.getWriter().print(result);
%>
