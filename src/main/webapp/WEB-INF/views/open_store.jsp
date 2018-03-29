<%--
  Created by IntelliJ IDEA.
  User: YUAN YUE
  Date: 2018/3/28
  Time: 16:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Ecom购物车</title>
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css"/>
    <script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js" type="text/javascript"></script>
    <!-- 引入自定义css文件 style.css -->
    <link rel="stylesheet" href="css/style.css" type="text/css"/>

    <title>开通页面</title>
    <script language="javascript" type="text/javascript">
        function checkAgreement()
        {
            if (document.getElementById("agreement").checked == false)
            {
                alert("不同意协议不能注册");
                document.getElementById("agreement").focus();
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<jsp:include page="/header"></jsp:include>
<div class="container">
    <h1>尚未开通店铺！</h1>
</div>

<div class="container">
    <div class="top">用户注册协议</div>
    <div class="row">
        <div style="text-align: center">
            <p>一、总则</p>
            1.1 E-com的所有权和运营权归Team2所有。
            1.2 用户在注册之前，应当仔细阅读本协议，并同意遵守本协议后方可成为注册用户。一旦注册成功，则用户与E-com之间自动形成协议关系，用户应当受本协议的约束。用户在使用特殊的服务或产品时，应当同意接受相关协议后方能使用。
            1.3 本协议则可由E-com随时更新，用户应当及时关注并同意本站不承担通知义务。本站的通知、公告、声明或其它类似内容是本协议的一部分。
            <p>二、服务内容</p>
            2.1 E-com的具体内容由本站根据实际情况提供。
            2.2 本站仅提供相关的网络服务，除此之外与相关网络服务有关的设备(如个人电脑、手机、及其他与接入互联网或移动网有关的装置)及所需的费用(如为接入互联网而支付的电话费及上网费、为使用移动网而支付的手机费)均应由用户自行负担。
            <p>三、用户帐号</p>
            3.1 经本站注册系统完成注册程序并通过身份认证的用户即成为正式用户，可以获得本站规定用户所应享有的一切权限；未经认证仅享有本站规定的部分会员权限。E-com有权对会员的权限设计进行变更。
            3.2 用户只能按照注册要求使用真实姓名，及身份证号注册。用户有义务保证密码和帐号的安全，用户利用该密码和帐号所进行的一切活动引起的任何损失或损害，由用户自行承担全部责任，本站不承担任何责任。如用户发现帐号遭到未授权的使用或发生其他任何安全问题，应立即修改帐号密码并妥善保管，如有必要，请通知本站。因黑客行为或用户的保管疏忽导致帐号非法使用，本站不承担任何责任。

        </div>

    </div>


<div>
<form name="form1" id="form1" method="POST" action="shop" onsubmit="return checkAgreement();">
    <input type="checkbox" name="agreement" id="agreement">我已阅读并同意<a>商店注册协议</a></input>
    <input name="submit" type="submit" value="立即注册" >
</form>
</div>
</div>
<jsp:include page="/footer"></jsp:include>
</body>
</html>
