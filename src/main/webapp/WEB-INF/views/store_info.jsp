<%--
  Created by IntelliJ IDEA.
  User: 24540
  Date: 2018/3/28
  Time: 15:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>店铺信息</title>
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css"/>
    <script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
    <!-- 引入表单校验jquery插件 -->
    <script src="js/jquery.validate.min.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js" type="text/javascript"></script>
    <!-- 引入自定义css文件 style.css -->
    <link rel="stylesheet" href="css/style.css" type="text/css"/>

    <style>
        body {
            margin-top: 20px;
            margin: 0 auto;
        }

        .carousel-inner .item img {
            width: 100%;
            height: 300px;
        }

        font {
            color: #3164af;
            font-size: 18px;
            font-weight: normal;
            padding: 0 10px;
        }

        .error {
            color: red
        }
    </style>
</head>
<body>

<!-- 引入mystore_header.jsp -->
<jsp:include page="/mystore_header"></jsp:include>

<div class="container"
     style="width: 100%; background: url('image/regist_bg.jpg');">
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8"
             style="background: #fff; padding: 40px 80px; margin: 30px; border: 7px solid #ccc;">
            <font>店铺信息</font>USER INFORMATION
            <form id="myform" class="form-horizontal"
                  method="post" style="margin-top: 5px;">


                <div class="form-group">
                    <label for="sname" class="col-sm-2 control-label">店铺名称</label>
                    <div class="col-sm-6">
                        <p id="sname" class="form-control">${store.sname}</p>
                    </div>
                </div>


                <div class="form-group">
                    <label for="saddress" class="col-sm-2 control-label">店铺地址</label>
                    <div class="col-sm-6">
                        <p id="saddress" class="form-control">${store.saddress}</p>

                    </div>
                </div>

                <div class="form-group">
                    <label for="introduce" class="col-sm-2 control-label">店铺介绍</label>
                    <div class="col-sm-6">
                        <p id="introduce" class="form-control">${store.introduce}</p>
                    </div>
                </div>

                <div class="form-group">
                    <label for="state" class="col-sm-2 control-label">运营状态</label>
                    <div class="col-sm-6">
                        <c:if test="${store.state eq '1'}">
                            <a id="state" class="form-control">营业中</a>
                        </c:if>
                        <c:if test="${store.state eq '0'}">
                            <a id="state" class="form-control">停业中</a>
                        </c:if>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-2">
                        <a width="100" name="change_info" href="store_info_change" class="btn btn-danger">
                            修改信息
                        </a>
                    </div>
                    <div class=" col-sm-2">
                        <c:if test="${store.state eq '1'}">
                            <a width="100" name="change_info" href="store_info_change" class="btn btn-danger">
                               关闭店铺
                            </a>
                        </c:if>
                        <c:if test="${store.state eq '0'}">
                            <a width="100" name="change_info" href="store_info_change" class="btn btn-danger">
                                开启店铺
                            </a>
                        </c:if>
                    </div>
                </div>
            </form>
        </div>


        <div class="col-md-2"></div>

    </div>
</div>

<!-- 引入footer.jsp -->
<jsp:include page="/footer"></jsp:include>


</body>
</html>
