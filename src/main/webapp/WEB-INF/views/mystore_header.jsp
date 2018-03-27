<%--
  Created by IntelliJ IDEA.
  User: Damon
  Date: 2018/3/13
  Time: 10:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!-- 登录 注册 购物车... -->
<div class="container-fluid">
    <div class="col-md-4">
        <img src="img/logo2.png"/>
    </div>
    <div class="col-md-5">
        <img src="img/header.png"/>
    </div>
    <div class="col-md-3" style="padding-top:20px">
        <ol class="list-inline">
            <c:if test="${empty user }">
                <li><a href="login.jsp">登录</a></li>
                <li><a href="register.jsp">注册</a></li>
            </c:if>
            <c:if test="${!empty user }">
                <li style="color:red">欢迎您，${user.username }</li>
            </c:if>
            <li><a href="cart.jsp">购物车</a></li>
            <li><a href="order_list.jsp">我的订单</a></li>
            <li><a href="${pageContext.request.contextPath}/store_productlist?sid=456">我的店铺</a></li>
        </ol>
    </div>
</div>

&nbsp;

<!-- 导航条 -->
<div class="container-fluid">
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <ul class="nav-pills" role="tablist">
                    <li role="presentation" class="dropdown">
                        <a class="navbar-brand" id="myproduct" href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                            我的商品
                            <span class="caret"></span>
                        </a>
                        <ul id="menu1" class="dropdown-menu" aria-labelledby="myproduct">
                            <%--<li><a href="myproduct_info">移动通讯</a></li>
                            <li><a href="#">数码</a></li>
                            <li><a href="#">家电</a></li>--%>
                        </ul>
                    </li>
                    <li role="presentation" class="dropdown">
                        <a class="navbar-brand" id="ordermanagement" href="#"  class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false" >
                            订单管理
                            <span class="caret"></span>
                        </a>
                        <ul id="menu2" class="dropdown-menu" aria-labelledby="ordermanagement">
                            <li><a href="#">未发货</a></li>
                            <li><a href="#">已发货</a></li>
                            <li><a href="#">已完成</a></li>
                        </ul>
                    </li>
                    <li>
                        <a class="navbar-brand" id="storeinfo" href="#">
                            店铺信息
                        </a>
                    </li>
                    <li>
                        <a class="navbar-brand" id="storeinfo" href="#">
                            我的收益
                        </a>
                    </li>
                </ul>
            </div>


            <!--需要变成调用店内搜索的方法 -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav" id="categoryUL">

                    <%--<c:forEach items="${categoryList}" var="category">--%>
                    <%--<li><a href="#">${category.cname}</a></li>--%>
                    <%--</c:forEach>--%>
                </ul>
                <form class="navbar-form navbar-right" role="search">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="店内搜索">
                    </div>
                    <button type="submit" class="btn btn-default">搜索</button>
                </form>
            </div>
        </div>
    </nav>
</div>
<script type="text/javascript">
    // hearder.jsp加载完成后，去服务器端获得所有的category数据
    $(function () {
        var content = "";
        $.post(
            "${pageContext.request.contextPath}/categoryList",
            function (data) {
                //    [{"cid":"xxx","cname":"xxx"},{},{}]
                //    动态的创建li
                for (var i = 0; i < data.length; i++) {
                    content += "<li><a href='${pageContext.request.contextPath}/store_productlist?cid="+data[i].cid+"'>" + data[i].cname + "</a></li>";
                }

                //拼接添加商品li
                content+="<li role=\"separator\" class=\"divider\"></li><li><a href='${pageContext.request.contextPath}/store_productlist?sid=456'>所有商品</a></li>";
                content+="<li role=\"separator\" class=\"divider\"></li><li><a href=\"addProduct\">添加商品</a></li>";

                //    将拼接好的li放置到ul中
                $("#menu1").html(content);
            },
            "json"
        );
    });

</script>
