<%--
  Created by IntelliJ IDEA.
  User: YUAN YUE
  Date: 2018/3/13
  Time: 10:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
<%--    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css"/>
    <script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js" type="text/javascript"></script>--%>
    <!-- 引入自定义css文件 style.css -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <%--最新版网络资源，不能一起用--%>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>
<body>
<jsp:include page="mystore_header.jsp" flush="true"></jsp:include>

<!-- Split button -->
<div class="btn-group">
    <button type="button" class="btn btn-danger">Action</button>
    <button type="button" class="btn btn-danger dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        <span class="caret"></span>
        <span class="sr-only">Toggle Dropdown</span>
        &nbsp;
    </button>
    <ul class="dropdown-menu">
        <li><a href="#">Action</a></li>
        <li><a href="#">Another action</a></li>
        <li><a href="#">Something else here</a></li>
        <li role="separator" class="divider"></li>
        <li><a href="#">Separated link</a></li>
    </ul>
</div>
<div class="container-fluid">
    <div class="fixed-table-toolbar">
        <div class="bs-bars pull-right">
            <div id="toolbar2">
                <div class="btn btn-primary" data-toggle="modal" data-target="#addModal">添加记录</div>
            </div>
        </div>
        <div class="columns columns-left btn-group pull-left">
            <div class="keep-open btn-group">
                <button name="refresh" title="Refresh" class="btn btn-default" aria-label="refresh" type="button">
                    <i class="glyphicon glyphicon-refresh icon-refresh"></i>&nbsp;
                </button>
            </div>
            <div title="Columns" class="keep-open btn-group">
                <button class="btn btn-default dropdown-toggle" aria-expanded="false" aria-label="columns" type="button" data-toggle="dropdown"><i class="glyphicon glyphicon-th icon-th"></i>
                    <span class="caret"></span>
                </button>
                <ul class="dropdown-menu" role="menu">
                    <li role="menuitem"><label><input type="checkbox" checked="checked" value="1" data-field="oid"> 订单号</label></li>
                    <li role="menuitem"><label><input type="checkbox" checked="checked" value="2" data-field="uid"> UID</label></li>
                    <li role="menuitem"><label><input type="checkbox" checked="checked" value="3" data-field="money"> 商品金额</label></li>
                    <li role="menuitem"><label><input type="checkbox" checked="checked" value="4" data-field="buydate"> 下单时间</label></li>
                    <li role="menuitem"><label><input type="checkbox" checked="checked" value="5" data-field="paydate"> 付款时间</label></li>
                    <li role="menuitem"><label><input type="checkbox" checked="checked" value="6" data-field="buyer"> 收货人</label></li>
                </ul>
            </div>
        </div>
        <div class="pull-left search">
            <input class="form-control" type="text" placeholder="Search">
        </div>
    </div>
</div>

<!-- end: custom table -->
<jsp:include page="footer.jsp" flush="true"></jsp:include>
</body>
</html>