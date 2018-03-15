<%@ page import="com.ecom.pojo.Express" %>
<%--
  Created by IntelliJ IDEA.
  User: 24540
  Date: 2018/3/15
  Time: 15:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Express Modal</title>
</head>
<body>
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
           ×
        </button>
        <h4 class="modal-title" id="myModalLabel">物流详情</h4>
    </div>
    <div class="modal-body">
        <div>
            <%
                Express re = new Express();
                re = (Express) request.getAttribute("express");
            %>
            <h6>快递单号：<%= re.getEid()%></h6>
            <h6>物流公司：<%= re.geteCompany()%></h6>
            <h6>发货时间：<%= re.geteTime()%></h6>
            <h6>发货地址：<%= re.geteAddress()%></h6>
            <h6>收获地址：<%= re.getsAddress()%></h6>
        </div>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
    </div>
</body>
</html>
