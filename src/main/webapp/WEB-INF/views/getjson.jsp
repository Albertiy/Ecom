<%--
  Created by IntelliJ IDEA.
  User: YUAN YUE
  Date: 2018/3/13
  Time: 14:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.Enumeration" %>
<%@ page import="com.ecom.pojo.Order" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.context.support.ClassPathXmlApplicationContext" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.ecom.pojo.Express" %>
<%@ page import="com.ecom.pojo.OrderData" %>
<%@ page import="com.google.gson.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    System.out.println("[getjson.jsp]");
    request.setCharacterEncoding("UTF-8");//解决中文传值乱码
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
if(request.getSession().getAttribute("orderList")==null) {
    for (int i = 1; i <= total; i++) {
        Order order = new Order();
        order.setOid(i);
        System.out.println("oid: " + order.getOid());
        order.setSid(123);
        order.setUid(i + 100000);
        order.setAmount(i * 100);
        order.setConsignee("小王");
        order.setAddress("小王隔壁家");
        order.setPhone("11235712193");
        order.setPs("我想给她一个惊喜");
        OrderData orderData = new OrderData();
        orderData.setPid(i);
        orderData.setShopPrice(i * 100 % 17 * 50);
        orderData.setPcount(i % 5);
        orderData.setSubTotal((i % 3) * (i % 5));
        order.setOdata(gson.toJson(orderData));
        orderList.add(order);
    }
    request.getSession().setAttribute("orderList",orderList);
}else{
    orderList = (List<Order>)request.getSession().getAttribute("orderList");
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

    List<Order> tempList = new ArrayList<Order>();  //临时列表

    /*
    * 检索：可以从数据库检索，也可以从后台检索，要考虑到订单状态，
    * 检索列：收获地点，手机号，下单时间
    * */
    System.out.println("search: " + request.getParameterValues("search")[0]);
    if(request.getParameterValues("search")!=null&&!request.getParameterValues("search")[0].trim().equals("")){
        String search = request.getParameterValues("search")[0];
        for(Order oo : orderList){
            if(oo.getPhone()!=null&&oo.getPhone().contains(search)){//手机号
                tempList.add(oo);
            }else if(oo.getAddress()!=null&&oo.getAddress().contains(search)){//地址
                tempList.add(oo);
            }else if(oo.getCreateTime()!=null&&oo.getCreateTime().contains(search)){//下单时间
                tempList.add(oo);
            }else if(oo.getPayTime()!=null&&oo.getPayTime().contains(search)){//支付时间
                tempList.add(oo);
            }
        }
        //request.getSession().setAttribute("tempList",tempList);
    }else {
        tempList = orderList;
    }
    /*
    * 排序：
    *
    * */
    //PHP：
    //    if (isset($_POST['sort'])) {
    //        $temp = [];
    //        foreach ($list_temp as $row) {
    //            $temp[] = $row[$_POST['sort']];
    //        }
    //        //php的多维排序
    //        array_multisort($temp,
    //                $_POST['sort'] == 'name' ? SORT_STRING : SORT_NUMERIC,
    //                $_POST['order'] == 'asc' ? SORT_ASC : SORT_DESC,
    //                $list_temp
    //        );
    //    }
    if(request.getParameterValues("sort")!=null) {
        System.out.println("sort: "+request.getParameterValues("sort")[0]);
        System.out.println("order: "+request.getParameterValues("order")[0]);
    }
    int temp_total = tempList.size();

    String temp_list = gson.toJson(tempList);
    //System.out.println(order_list);
    String temp_result = "{\n" +" \"total\":"+temp_total+", \n" + " \"rows\":\n";
    temp_result += temp_list;
    temp_result  += "\n" + "}";
    //System.out.println("临时 JSON输出： \n"+temp_result);
    //根据传递过来的分页偏移量和分页量截取模拟分页 rows 可以根据前端的 dataField 来设置
    int offset = 0;
    if(request.getParameterValues("offset")!=null)
        offset = Integer.parseInt(request.getParameterValues("offset")[0]);
    int limit = 0;
    if(request.getParameterValues("limit")!=null)
        limit = Integer.parseInt(request.getParameterValues("limit")[0]);
    System.out.println("offset: "+offset);
    System.out.println("limit "+limit);
    //最外层
    JsonObject jsonObject=new JsonParser().parse(temp_result).getAsJsonObject();
    //需要遍历的数组
    JsonArray jsonArray = jsonObject.getAsJsonArray("rows");
    //循环遍历数组
    jsonArray.size();
    JsonArray pageArray = new JsonArray();
    for(int i = offset;i < offset+limit && i < jsonArray.size();i++){//根据偏移量和显示条数得到数组内容
        pageArray.add(jsonArray.get(i));
    }
    jsonObject.remove("rows");
    jsonObject.add("rows",pageArray);
    String page_result = jsonObject.toString(); //最终分页后返回的json语句
    System.out.println("page_result: "+page_result);
    response.getWriter().print(page_result);
%>
