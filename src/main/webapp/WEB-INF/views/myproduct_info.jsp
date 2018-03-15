<%--
  Created by IntelliJ IDEA.
  User: Damon
  Date: 2018/3/13
  Time: 14:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>My Product Info</title>
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css"/>
    <script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js" type="text/javascript"></script>
    <script src="js/product_info.js" type="text/javascript"></script>
</head>
<body>
<div class="container-fluid">
    <!-- 引入header.jsp -->
    <jsp:include page="mystore_header.jsp" flush="true"></jsp:include>

    <div class="container">
        <div class="row">
            <div style=" width: 930px; padding: 10px; margin-bottom: 10px;">
                <a href="./index.htm">首页&nbsp;&nbsp;&gt;</a>
                <a href="./蔬菜分类.htm">蔬菜&nbsp;&nbsp;&gt;</a>
                <a>无公害蔬菜</a>
            </div>
            <form id="fm" class="form-inline" role="form" action="" method="post">
                <div style="margin: 0 auto; width: 950px; height: 350px">
                    <div class="col-md-6">
                        <img style="opacity: 1; width: 400px; height: 350px;" title="" class="medium"
                             src="products/c_0001.jpg">
                    </div>

                    <div class="col-md-6" style="height:350px">
                        <div class="col-md-6">
                            <div style="width: 350px; margin: 20px 0 10px 0;">
                                    <label class="form-label">名称:</label>
                                    <input class="form-control" type="text" id="productName" name="productName" value="${product.pname}" readonly="readonly">
                            </div>
                            <div style="width: 350px; margin: 20px 0 10px 0;">
                                <label class="form-label">编号:</label>
                                <input class="form-control" type="text" id="pid" name="pid" value="${product.pid}" readonly>
                            </div>

                            <div style="width: 350px; margin: 20px 0 10px 0;">
                                <label class="form-label">单价:</label>
                                <input class="form-control" type="text" id="price" name="price" value="${product.shop_price}" maxlength="4" readonly="readonly">
                                元 / 件
                            </div>
                            <div  style="width: 350px; margin: 20px 0 10px 0;">
                                <label class="form-label">库存:</label>
                                <input class="form-control form-control-sm" type="text" id="storage" name="storage" value="${product.pstorage}" maxlength="4" readonly="readonly">
                                件
                            </div>
                        </div>
                        <div class="col-md-6">
                            <a id="modify" style="background-color: #d3d3d3; float:right; display: block" class="btn btn-default" role="button" onclick="modifyinfo()"><strong>修改</strong></a>
                            <a id="confirm" style="background-color: #d3d3d3; float:right; display: none" class="btn btn-default" role="button" onclick=document.getElementById("fm").submit();><strong>确定</strong></a>
                        </div>
                        <div class="col-md-12" style="margin-top: 85px">
                            <a id="down" style="background-color: #eb5339; width: 100px; float:right" class="btn btn-default" role="button"><strong>下架</strong></a>
                            <a id="up" style="background-color: #59d089; width: 100px; float: right" class="btn btn-default" role="button"><strong>上架</strong></a>
                        </div>
                    </div>
                </div>
            </form>
            <div class="clear"></div>

            <div style="width: 950px; margin: 0 auto;">
                <div style="margin-top: 10px; width: 930px;">
                    <table class="table table-bordered">
                        <tbody>
                        <tr class="active">
                            <th><strong>商品介绍</strong></th>
                        </tr>
                        <tr>
                            <th>暂无商品介绍信息 </th>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <div style="background-color: #d3d3d3; width: 930px;">
                    <table class="table table-bordered">
                        <tbody>
                        <tr class="active">
                            <th><strong>商品评论</strong></th>
                        </tr>
                        <tr class="warning">
                            <th>暂无商品评论信息 <a>[发表商品评论]</a></th>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <div style="background-color: #d3d3d3; width: 930px;">
                    <table class="table table-bordered">
                        <tbody>
                        <tr class="active">
                            <th><strong>商品咨询</strong></th>
                        </tr>
                        <tr class="warning">
                            <th>暂无商品咨询信息 <a>[发表商品咨询]</a></th>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>
    </div>


    <!-- 引入footer.jsp -->
    <jsp:include page="footer.jsp" flush="true"></jsp:include>
</div>
</body>
</html>
