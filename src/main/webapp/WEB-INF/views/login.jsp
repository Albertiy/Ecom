<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>会员登录</title>
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css"/>
    <script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
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

        .container .row div {
            /* position:relative;
                         float:left; */

        }

        font {
            color: #666;
            font-size: 22px;
            font-weight: normal;
            padding-right: 17px;
        }
    </style>


    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        String password = "";
        String username = "";
        String checked = "";
        Cookie[] cookies = request.getCookies();        //取出cookie对象组
        for (int i = 0; cookies != null && i < cookies.length; i++) {
            Cookie cookie = cookies[i];       //  取出其中的一个对象，含有name ,value
            if (cookie != null && "username".equals(cookie.getName())) {      //获取第一个cookie对象的name
//            name = URLDecoder.decode(cookie.getValue(), "UTF-8");//进行解码
                username = cookie.getValue();
                checked = "checked";
            }
            if (cookie != null && "password".equals(cookie.getName())) {
                password = cookie.getValue();
            }
        }
    %>
</head>
<body>

<!-- 引入header.jsp -->
<jsp:include page="/header"></jsp:include>


<div class="container"
     style="width: 100%; height: 460px; background: #FF2C4C url('images/loginbg.jpg') no-repeat;">
    <div class="row">
        <div class="col-md-7">
            <!--<img src="./image/login.jpg" width="500" height="330" alt="会员登录" title="会员登录">-->
        </div>

        <div class="col-md-5">
            <div
                    style="width: 440px; border: 1px solid #E7E7E7; padding: 20px 0 20px 30px; border-radius: 5px; margin-top: 60px; background: #fff;">
                <font>会员登录</font>USER LOGIN
                <div>&nbsp;</div>
                <form class="form-horizontal" method="post" action="${pageContext.request.contextPath }/userLogin">


                    <div class="form-group">
                        <label for="username" class="col-sm-2 control-label">用户名</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="username" name="username"
                                   placeholder="请输入邮箱或是手机号" value="<%=username%>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputPassword3" class="col-sm-2 control-label">密码</label>
                        <div class="col-sm-6">
                            <input type="password" class="form-control" id="inputPassword3" name="password"
                                   placeholder="请输入登录密码" value="<%=password%>">
                        </div>
                    </div>
                    <c:if test="${ not empty loginError }">
                        <div class="form-group">
                            <label for="inputPassword3" class="col-sm-2 control-label"></label>
                            <div class="col-sm-6">
                                <label class="error" style="color: red">${loginError}</label>

                            </div>
                        </div>
                    </c:if>

                    <div class="form-group">
                        <label for="inputPassword3" class="col-sm-2 control-label">验证码</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="inputPassword3"
                                   placeholder="请输入验证码">
                        </div>
                        <div class="col-sm-3">
                            <img src="./image/captcha.jhtml"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <div class="checkbox">
                                <label> <input type="checkbox" name="autoLogin"> 自动登录
                                </label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <label> <input
                                    type="checkbox" value="yes" <%=checked%> name="remember"> 记住用户名
                            </label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <input type="submit" width="100" value="登录" name="submit"
                                   style="background: url('./images/login.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0); height: 35px; width: 100px; color: white;">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- 引入footer.jsp -->
<jsp:include page="/footer"></jsp:include>

</body>
</html>