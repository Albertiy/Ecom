<%--
  Created by IntelliJ IDEA.
  User: Damon
  Date: 2018/3/13
  Time: 10:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>My Store</title>
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css"/>
    <script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js" type="text/javascript"></script>
</head>
<body>
<!-- 引入header.jsp -->
<jsp:include page="mystore_header.jsp" flush="true"></jsp:include>

<div class="row" style="width: 1210px; margin: 0 auto;">

    <c:forEach items="${pageBean.list}" var="pro">

        <div class="col-md-2" style="height: 250px; width: 300px;text-align: center;">
            <a href="${pageContext.request.contextPath}/product?method=productInfo&pid=${pro.pid}&cid=${cid}&currentPage=${pageBean.currentPage}">
                <img src="${pageContext.request.contextPath}/${pro.pimage}"
                     width="170" height="170" style="display: inline-block;">
            </a>
            <p>
                <a href="${pageContext.request.contextPath}/product?method=productInfo&pid=${pro.pid}&cid=${cid}&currentPage=${pageBean.currentPage}"
                   style='color: green'>${pro.pname}</a>
            </p>
            <p>
                <font color="#FF0000">单价：${pro.shop_price}</font>
            </p>
        </div>

    </c:forEach>
</div>

<!--分页 -->
<div style="width: 380px; margin: 0 auto; margin-top: 50px; text-align: center;">
    <ul class="pagination" style="text-align: center; margin-top: 10px;">

        <%--上一页--%>
        <c:if test="${pageBean.currentPage==1}">
            <li class="disabled">
                <a href="javascript:void(0);"
                   aria-label="Previous">
                <span
                        aria-hidden="true">&laquo;</span>
                </a>
            </li>
        </c:if>

        <c:if test="${pageBean.currentPage!=1}">
            <li>
                <a href="${pageContext.request.contextPath}/product_list?sid=456&currentPage=${pageBean.currentPage-1}"
                   aria-label="Previous">
                <span
                        aria-hidden="true">&laquo;</span>
                </a>
            </li>
        </c:if>


        <%--显示每一页--%>
        <c:forEach begin="1" end="${pageBean.totalPage}" var="page">
            <%--判断是否是当前页--%>
            <c:if test="${page==pageBean.currentPage}">
                <li class="active"><a href="javacript:void(0);">${page}</a></li>

            </c:if>
            <c:if test="${page!=pageBean.currentPage}">
                <li>
                    <a href="${pageContext.request.contextPath}/product_list?sid=456&currentPage=${page}">${page}</a>
                </li>

            </c:if>
        </c:forEach>


        <%--下一页--%>
        <c:if test="${pageBean.currentPage==pageBean.totalPage}">
            <li class="disabled">
                <a href="javascript:void(0);"
                   aria-label="Next"> <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </c:if>

        <c:if test="${pageBean.currentPage!=pageBean.totalPage}">
            <li>
                <a href="${pageContext.request.contextPath}/product_list?sid=456&currentPage=${pageBean.currentPage+1}"
                   aria-label="Next"> <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </c:if>

    </ul>
</div>
<!-- 分页结束 -->

<!-- 引入footer.jsp -->
<jsp:include page="footer.jsp" flush="true"></jsp:include>
</body>
</html>
