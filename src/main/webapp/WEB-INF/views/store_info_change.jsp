<%--
  Created by IntelliJ IDEA.
  User: 24540
  Date: 2018/3/28
  Time: 15:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>会员信息</title>
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


    <script type="text/javascript">


        $(function () {
            $("#myform").validate({

                rules: {

                    "nickname": {
                        "required": true,
                        "rangelength": [1, 12]

                    },
                    "uname": {
                        "required": true

                    },
                    "gender": {
                        "required": true
                    },
                    "birthday": {
                        "required": true
                    },


                },
                messages: {

                    "nickname": {
                        "required": "昵称不能为空",
                        "rangelength": "昵称长度不能超过12位"

                    },
                    "uname": {
                        "required": "真实姓名不能为空"

                    },
                    "birthday": {
                        "required": "出生日期不能为空"

                    }

                },
                onsubmit: true,//是否在提交时验证，默认也是true
                submitHandler: function (form) {
                    if (confirm("确认修改信息？")) {
                        form.submit();
                    }
                },
                invalidHandler: function (form, validator) {
                    return false;
                }

            });
        });

    </script>
</head>
<body>

<!-- 引入header.jsp -->
<jsp:include page="/mystore_heade"></jsp:include>

<div class="container"
     style="width: 100%; background: url('image/regist_bg.jpg');">
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8"
             style="background: #fff; padding: 40px 80px; margin: 30px; border: 7px solid #ccc;">
            <font>修改资料</font>INFORMATION CHANGE
            <form id="myform" class="form-horizontal" action="${pageContext.request.contextPath }/changeUserInfo"
                  method="post" style="margin-top: 5px;">

                <input type="hidden" name="sid" value="${store.sid}">

                <div class="form-group">
                    <label for="sname" class="col-sm-2 control-label">店铺名称</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="sname" name="sname"
                               placeholder="请输入店铺名称" value="${store.sname}">
                    </div>
                </div>


                <div class="form-group">
                    <label for="saddress" class="col-sm-2 control-label">店铺地址</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="saddress" name="saddress"
                               placeholder="请输入店铺地址" value="${store.saddress}">

                    </div>
                </div>

                <div class="form-group">
                    <label for="introduce" class="col-sm-2 control-label">店铺介绍</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="introduce" name="introduce"
                               placeholder="请输入店铺介绍" value="${store.introduce}">

                    </div>
                </div>

                <div class="form-group">
                    <label for="sex1" class="col-sm-2 control-label">性别</label>
                    <div class="col-sm-6">
                        <c:if test="${user.gender eq 'male'}">
                            <label class="radio-inline">

                                <input type="radio" name="gender" id="sex1" value="male" checked>男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="sex2" value="female">女
                            </label>
                        </c:if>

                        <c:if test="${user.gender eq 'female'}">
                            <label class="radio-inline">

                                <input type="radio" name="gender" id="sex1" value="male">男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="sex2" value="female" checked>女
                            </label>
                        </c:if>

                        <label class="error" for="sex1" style="display:none ">您没有第三种选择</label>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-2">

                        <input type="submit" width="100" value="确认修改" name="submit"
                               style="background: url('./images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0); height: 35px; width: 100px; color: white;">
                    </div>
                    <div class=" col-sm-2">
                        <a width="100"  href="user_info" class="btn btn-danger">
                            取消
                        </a>
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





