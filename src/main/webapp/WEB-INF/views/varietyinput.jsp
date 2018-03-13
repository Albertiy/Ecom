<%--
  Created by IntelliJ IDEA.
  User: YUAN YUE
  Date: 2018/3/12
  Time: 10:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Variety List</title>
</head>
<body>
<c:if test="${empty requestScope.varieties}">
    没有商品类目信息
</c:if>
<c:if test="${!empty requestScope.varieties}">
    <table border="1" cellpadding="10" cellspacing="0">
        <tr>
            <th>一级类目</th>
            <th>二级类目</th>
            <th>三级类目</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${requestScope.varieties}" var="variety">
            <tr>
                <td>${variety.c1}</td>
                <td>${variety.c2}</td>
                <td>${variety.c3}</td>
                <td><a href="">Edit</a> </td>
                <td><a href="">Delete</a> </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<br><br>
<a href="variety">Add New Category</a>
</body>
</html>
