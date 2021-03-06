<%--
  Created by IntelliJ IDEA.
  User: YUAN YUE
  Date: 2018/3/17
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    System.out.println("【 order_table_details.jsp 】");
    System.out.println("orderDetails：" + request.getAttribute("orderDetails").toString());
%>
<center>
    <table class="table table-hover">
        <tbody>
            <tr class="success">
                <th>图片</th>
                <th>商品</th>
                <th>单价</th>
                <th>数量</th>
                <th>小计</th>
            </tr>
            <c:set var="total" value="0.0"/>
            <c:forEach items="${requestScope.orderDetails}" var="p">
                <tr class="default-color5">
                    <td width="10" width="15%"><input type="hidden" name="id" value="22">
                        <img src="./images/Files/${p.pid}.jpg" onerror="this.src='./images/Files/default.jpg'" alt="${p.pid}.jpg" width="70" height="60">
                    </td>
                    <td width="30%"><a target="_blank" href="${pageContext.request.contextPath}/store_productinfo?pid=${p.pid}"> ${p.pname} </a></td>
                    <td width="20%">${p.shopPrice}</td>
                    <td width="10%">${p.pcount}</td>
                    <td width="15%"><span class="subtotal">${p.subTotal}</span></td>
                </tr>
                <c:set var="total" value="${total+p.subTotal}"/>
            </c:forEach>
            <tr class="warning">
                <td colspan="12"><span class="subtotal" style="float:right">总价：￥${total}</span></td>
            </tr>
        </tbody>
    </table>
</center>


