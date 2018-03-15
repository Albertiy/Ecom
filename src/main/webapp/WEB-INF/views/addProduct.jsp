<%--
  Created by IntelliJ IDEA.
  User: Damon
  Date: 2018/3/14
  Time: 14:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Add Product</title>
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css"/>
    <script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js" type="text/javascript"></script>
    <script src="js/product_info.js" type="text/javascript"></script>
</head>
<body>
<!-- 引入header.jsp -->
<jsp:include page="mystore_header.jsp" flush="true"></jsp:include>

&nbsp;

<div style="margin: 0 auto; width: 950px; height: 350px">
    <div class="col-md-6">
        <img style="opacity: 1; width: 400px; height: 350px;" title="" class="medium"
             src="products/c_0001.jpg">
    </div>

    <div class="col-md-6" style="height:350px">
        <dl class="form-group">
            <dt class="input-label">
                <label class="form-label f5">名称</label>
            </dt>
            <dd>
                <input type="text" id="productName" name="productName" class="form-control form-control-lg">
            </dd>
        </dl>

        <dl class="form-group">
            <dt class="input-label">
                <label class="form-label f5">单价</label>
            </dt>
            <dd>
                <input type="text" id="unit_price" name="unit_price" class="form-control form-control-lg">
            </dd>
        </dl>

        <dl class="form-group">
            <dt class="input-label">
                <label class="form-label f5">库存</label>
            </dt>
            <dd>
                <input type="text" id="stock" name="stock" class="form-control form-control-lg">
            </dd>
        </dl>

        <dl class="form-group">
            <dt class="input-label">
                <label class="form-label f5">分类</label>
            </dt>
            <dd>
                <select class="btn btn-default  dropdown-toggle" id="bindZ" onChange="getKcbh()" style="width:100px; margin-left: 0px"></select>

                <select class="btn btn-default  dropdown-toggle" id="bindK"  onChange="getZsd()" style="width:100px;margin-left: 68.5px; margin-right: 68.5px">
                    <option  value="-1">--</option>
                </select>
                <select class="btn btn-default  dropdown-toggle" id="bindZsd" style="width:100px; margin-right: 0px">
                    <option  value="-1">--</option>
                </select>

                <%--<button type="button" class="btn btn-default btn-block dropdown-toggle sbutton"
                        data-toggle="dropdown" aria-haspopup="true"
                        aria-expanded="false" id="drop_button">
                    Science
                    <span class="caret"></span>
                </button>
                <ul class="dropdown-menu">
                    <li id="Science"><a href="#">Science</a></li>
                    <li id="Math"><a href="#">Math</a></li>
                    <li id="Art"><a href="#">Art</a></li>
                    <li id="Ohters"><a href="#">Others</a></li>
                </ul>--%>

            </dd>

            <input type="text" name="Category" id="Category" value="Science" hidden>
        </dl>

        <a id="down" style="background-color: #d3d3d3; width: 100px; float:right" class="btn btn-default" role="button"><strong>确定</strong></a>

    </div>
</div>


<!-- 引入footer.jsp -->
<jsp:include page="footer.jsp" flush="true"></jsp:include>
</body>
</html>
