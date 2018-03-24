<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head></head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员注册</title>
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
        "checkEmail",
        //校验的函数
        function (value, element, params) {

            //定义一个标志
            var flag = false;

            //value:输入的内容
            //element:被校验的元素对象
            //params：规则对应的参数值
            //目的：对输入的邮箱和手机号进行ajax校验
            $.ajax({
                "async": false,
                "url": "${pageContext.request.contextPath}/checkEmail",
                "data": {"email": value},
                "type": "POST",
                "dataType": "json",
                "success": function (data) {
                    flag = data.isExist;
                }
            });


            //返回false代表该校验器不通过
            return !flag;
        }
    );

    $.validator.addMethod(
        //规则的名称
        "checkPhone",
        //校验的函数
        function (value, element, params) {

            //定义一个标志
            var flag = false;

            //value:输入的内容
            //element:被校验的元素对象
            //params：规则对应的参数值
            //目的：对输入的邮箱和手机号进行ajax校验
            $.ajax({
                "async": false,
                "url": "${pageContext.request.contextPath}/checkPhone",
                "data": {"phone": value},
                "type": "POST",
                "dataType": "json",
                "success": function (data) {
                    flag = data.isExist;
                }
            });


            //返回false代表该校验器不通过
            return !flag;
        }
    );

    $(function () {
        $("#myform").validate({
            rules: {
                "phone": {
                    "required": true,
                    "checkPhone": true
                },
                "l_pwd": {
                    "required": true,
                    "rangelength": [6, 16]
                },
                "p_pwd": {
                    "required": true,
                    "rangelength": [6, 6]
                },
                "confirmpwd": {
                    "required": true,
                    "rangelength": [6, 16],
                    "equalTo": "#l_pwd"
                },
                "nickname":{
                    "required":true,
                    "rangelength": [1, 12]

                },
                "uname":{
                    "required":true

                },
                "gender": {
                    "required": true
                },
                "birthday": {
                    "required": true
                },
                "email": {
                    "required": true,
                    "email": true,
                    "checkEmail":true
                },

            },
            messages: {
                "phone": {
                    "required": "手机号码不能为空",
                    "checkPhone": "手机号码已存在"
                },
                "l_pwd": {
                    "required": "登陆密码不能为空",
                    "rangelength": "密码长度6-16位"
                },
                "p_pwd": {
                    "required": "支付密码不能为空",
                    "rangelength": "密码长度为6位"
                },
                "confirmpwd": {
                    "required": "密码不能为空",
                    "rangelength": "密码长度6-16位",
                    "equalTo": "两次密码不一致"
                },
                "nickname":{
                    "required":"昵称不能为空",
                    "rangelength": "昵称长度不能超过12位"

                },
                "uname":{
                    "required":"真实姓名不能为空"

                },
                "birthday":{
                    "required":"出生日期不能为空"

                },
                "email": {
                    "required": "邮箱不能为空",
                    "email": "邮箱格式不正确",
                    "checkEmail": "邮箱已存在"
                }
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
            <font>会员注册</font>USER REGISTER
            <form id="myform" class="form-horizontal" action="${pageContext.request.contextPath }/registerUser"
                  method="post" style="margin-top: 5px;">


                <div class="form-group">
                    <label for="nickname" class="col-sm-2 control-label">昵称</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="nickname" name="nickname"
                               placeholder="请输入昵称">
                    </div>
                </div>


                <div class="form-group">
                    <label for="l_pwd" class="col-sm-2 control-label">登陆密码</label>
                    <div class="col-sm-6">
                        <input type="password" class="form-control" id="l_pwd" name="l_pwd"
                               placeholder="请输入登陆密码">
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
                    <label for="p_pwd" class="col-sm-2 control-label">支付密码</label>
                    <div class="col-sm-6">
                        <input type="password" class="form-control" id="p_pwd" name="p_pwd"
                               placeholder="请输入6位的支付密码" >
                    </div>
                </div>

                <div class="form-group">
                    <label for="email" class="col-sm-2 control-label">Email</label>
                    <div class="col-sm-6">
                        <input type="email" class="form-control" id="email" name="email"
                               placeholder="Email">
                    </div>
                </div>

                <div class="form-group">
                    <label for="phone" class="col-sm-2 control-label">手机号</label>
                    <div class="col-sm-6">
                        <input type="tel" class="form-control" id="phone" name="phone"
                               placeholder="手机号码">
                    </div>
                </div>

                <div class="form-group">
                    <label for="uname" class="col-sm-2 control-label">真实姓名</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="uname" name="uname"
                               placeholder="请输入姓名">
                    </div>
                </div>
                <div class="form-group opt">
                    <label for="sex1" class="col-sm-2 control-label">性别</label>
                    <div class="col-sm-6">
                        <label class="radio-inline">
                            <input type="radio" name="gender" id="sex1" value="male">男
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="gender" id="sex2" value="female">女
                        </label>
                        <label class="error" for="gender" style="display:none ">您没有第三种选择</label>
                    </div>
                </div>
                <div class="form-group">
                    <label for="birthday" class="col-sm-2 control-label">出生日期</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" name="birthday" id="birthday">
                    </div>
                </div>


                <%--<div class="form-group">--%>
                    <%--<label for="date" class="col-sm-2 control-label">验证码</label>--%>
                    <%--<div class="col-sm-3">--%>
                        <%--<input type="text" class="form-control" name="checkCode">--%>

                    <%--</div>--%>
                    <%--<div class="col-sm-2">--%>
                        <%--<img src="./image/captcha.jhtml"/>--%>
                    <%--</div>--%>

                <%--</div>--%>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <input type="submit" width="100" value="注册" name="submit"
                               style="background: url('./images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0); height: 35px; width: 100px; color: white;">
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




