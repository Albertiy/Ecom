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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    System.out.println("[getjson.jsp]");
    //1. Order类实例化
    Order order=new Order();
    order.setOid(123456);
    order.setSid(456789);
    order.setUid(567890);
    //2. 得到配置创建的对象 gson
    ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext1.xml");
    Gson gson = (Gson)context.getBean("gson");
    //3. 将Order对象转化为JSON
    String json = gson.toJson(order);
    //4. 没有值的属性不会被添加到Json中
    int total  =2;
    String result = "{\n" +" \"total\":"+total+", \n" + " \"rows\":[\n";
    result +=json;
    result += ",\n";
    result +=json;
    result += "]\n" + "}";
    System.out.println("JSON输出： \n"+result);
/*    String ss = "{\n" +
        "    \"total\":6, \n" +
        "    \"rows\":[\n" +
        "            {\"oid\":1, \"uid\":\"sallency\", \"money\": 26},\n" +
        "            {\"oid\":2, \"uid\":\"sallency\", \"money\": 26},\n" +
        "            {\"oid\":3, \"uid\":\"sallency\", \"money\": 26},\n" +
        "            {\"oid\":4, \"uid\":\"sallency\", \"money\": 26},\n" +
        "            {\"oid\":5, \"uid\":\"sallency\", \"money\": 26},\n" +
        "            {\"oid\":6, \"uid\":\"sallency\", \"money\": 26}]\n" +
        "}";*/

    //js 中是 params.limit和total和offset，就是表单POST提交的属性,都存在Params里
    //参数表request.getParameter中有：search,order,offset,limit,other

    Enumeration paramNames = request.getParameterNames();
    // Tests if this enumeration contains more elements.
    while(paramNames.hasMoreElements()) {
        // Returns the next element of this enumeration
        // if this enumeration object has at least one more element to provide.
        String paraName = (String) paramNames.nextElement();
        System.out.println("参数名：" + paraName );
        //request parameter has, or null if the parameter does not exist.
        String[] paramValues = request.getParameterValues(paraName);
        System.out.println("参数值：");
        for (String sss: paramValues) {
            System.out.println(sss);
        }
    }

    //Attribute中有request_uri
    System.out.println(request.getAttributeNames().nextElement());

    if(request.getAttribute("offest")!=null){
        System.out.println("offest: "+request.getAttribute("offest"));
    }
    if(request.getAttribute("limit")!=null){
        System.out.println("limit: "+request.getAttribute("limit"));
    }
//分页时需要获取记录总数，键值为 total
    //$result["total"] = count($list_temp);
//根据传递过来的分页偏移量和分页量截取模拟分页 rows 可以根据前端的 dataField 来设置
    //$result["rows"] = array_slice($list_temp, $_POST['offset'], $_POST['limit']);
    response.getWriter().print(result);
%>
