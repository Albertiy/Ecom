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
        //自定义校验规则
        $.validator.addMethod(
            //规则的名称
            "checkL_Pwd",
            //校验的函数
            function (value, element, params) {

                //定义一个标志
                var flag = false;

                //value:输入的内容
                //element:被校验的元素对象
                //params：规则对应的参数值
                //目的：对输入的密码进行检测
                $.ajax({
                    "async": false,
                    "url": "${pageContext.request.contextPath}/checkL_Pwd",
                    "data": {"old_pwd": value,"uid":${user.uid}},
                    "type": "POST",
                    "dataType": "json",
                    "success": function (data) {
                        flag = data.isExist;
                    }
                });


                //返回false代表该校验器不通过
                return flag;
            }
        );

        $(function () {
            $("#myform").validate({

                rules: {

                    "old_pwd": {
                        "required": true,
                        "rangelength": [6, 16],
                        "checkL_Pwd": true
                    },
                    "l_pwd": {
                        "required": true,
                        "rangelength": [6, 16]
                    },
                    "confirmpwd": {
                        "required": true,
                        "rangelength": [6, 16],
                        "equalTo": "#l_pwd"
                    },


                },
                messages: {
                    "old_pwd": {
                        "required": "旧密码不能为空",
                        "rangelength": "密码长度6-16位",
                        "checkL_Pwd": "旧密码输入错误，请重新输入"
                    },
                    "l_pwd": {
                        "required": "登陆密码不能为空",
                        "rangelength": "密码长度6-16位"
                    },
                    "confirmpwd": {
                        "required": "密码不能为空",
                        "rangelength": "密码长度6-16位",
                        "equalTo": "两次密码不一致"
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
<jsp:include page="/header"></jsp:include>

<div class="container"
     style="width: 100%; background: url('image/regist_bg.jpg');">
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8"
             style="background: #fff; padding: 40px 80px; margin: 30px; border: 7px solid #ccc;">
            <font>修改密码</font>PASSWORD CHANGE
            <form id="myform" class="form-horizontal" action="${pageContext.request.contextPath }/changeUserL_pwd"
                  method="post" style="margin-top: 5px;">

                <input type="hidden" name="uid" value="${user.uid}">

                <div class="form-group">
                    <label for="old_pwd" class="col-sm-2 control-label">旧密码</label>
                    <div class="col-sm-6">
                        <input type="password" class="form-control" id="old_pwd" name="old_pwd"
                               placeholder="请输入旧密码">
                    </div>
                </div>

                <div class="form-group">
                    <label for="l_pwd" class="col-sm-2 control-label">新密码</label>
                    <div class="col-sm-6">
                        <input type="password" class="form-control" id="l_pwd" name="l_pwd"
                               placeholder="请输入新密码">
                    </div>
                </div>
                <div class="form-group">
                    <label for="confirmpwd" class="col-sm-2 control-label">确认密码</label>
                    <div class="col-sm-6">
                        <input type="password" class="form-control" id="confirmpwd" name="confirmpwd"
                               placeholder="请输入确认密码">
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-2">

                        <input type="submit" width="100" value="确认修改" name="submit"
                               style="background: url('./images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0); height: 35px; width: 100px; color: white;">
                    </div>
                    <div class=" col-sm-2">
                        <a width="100" href="user_info" class="btn btn-danger">
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




