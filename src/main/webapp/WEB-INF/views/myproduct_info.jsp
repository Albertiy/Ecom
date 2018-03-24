<%@ page import="com.ecom.pojo.Product" %><%--
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

    <%-- 判断商品是否上架 --%>
    <%
        Product product = (Product) request.getAttribute("product");
        String state=product.getState();
        String up = "disabled";
        String down="disabled";
        if(state.equals("1")){
            down="";
        }
        else if(state.equals("0")){
            up="";
        }
    %>

    <%-- 模态框触发 --%>
    <script>
        $(function(){
            function createfile(){
                $('#upBtn').append("<input type='file'  name='photos' class='upfile'>");
            }
            function showimg(url){
                $("#singleImg").attr('src',url);
                //var img='<img class="img-thumbnail img-responsive book_thumb center-block" src="'+url+'"/>';
                //$('#imglist').append(img);//上传多张图片使用，问题在于重新选择图片无法清除之前的选择
                //$('#imglist').html(img);    //上传单张图片使用，但没有办法放弃上传，决定使用固定的img标签
            }
            function addfile(){
                var file = this.files[0];//上传的图片的所有信息
                if(file==null){
                    console.log("没有选择图片，清空img的src");
                    showimg("");
                }
                console.log(this.files[0]);
                //首先判断是否是图片，若文件为空则console会报错
                if(!/image\/\w+/.test(file.type)){
                    alert('上传的不是图片');
                    return false;
                }
                //在此限制图片的大小
                var imgSize = file.size;
                console.log(imgSize);
                //35160  计算机存储数据最为常用的单位是字节(B)
                //在此处我们限制图片大小为2M
                if(imgSize>2*1024*1024){
                    alert('上传的图片的大于2M,请重新选择');
                    $(this).val('')
                    return false;
                }
                //如果还想限制图片格式也可以通过input的accept属相限制，或者file.name属性值做判断
                //建议使用accept属性，简单，方便。具体可以查看我的另一片文章
                //将图片信息通过如下方法获得一个http格式的url路径
                var objUrl = getObjectURL(this.files[0]);
                console.log(objUrl+'url');
                //blob:http://127.0.0.1:8020/6bf47c99-496b-4cc4-ae73-27aa06987036url
                showimg(objUrl)
                //showimg($(this).val());
                //$(this).val()本地上传的图片的绝对路径无法实现Img现实的，要将其转换为http格式的路径方能实现显示
                $(this).hide();
                //createfile();//上传多张图片时使用，现在只要一次上传
                $('.upfile').bind('change',addfile);
            }
            /*绑定了事件input file的值的改变*/
            $('.upfile').bind('change',addfile);

            function getObjectURL(file) {
                var url = null;
                if (window.createObjectURL != undefined) { //
                    url = window.createObjectURL(file);
                } else if (window.URL != undefined) {
                    //仅简单的验证仅如下的浏览器支持 webkit or chrome ie11 ie10 firefox oprea
                    url = window.URL.createObjectURL(file);
                } else if (window.webkitURL != undefined) { // webkit or chrome
                    url = window.webkitURL.createObjectURL(file);
                }
                return url;
            };
            /*var objUrl = getObjectURL(this.files[0]) ;
             if (objUrl) {
                       imgSrc.attr("src", objUrl);//给予jquery也可以如此使用
             }*/
            // URL对象是硬盘（SD卡等）指向文件的一个路径，如果我们做文件上传的时候，想要在图片没有上传服务器端的情况下
            // 看到上传图片的效果图的时候就可是以通过var url=window.URL.createObjectURL(obj.files[0]);
            // 获得一个http格式的url路径，此时候就可以设置到<img src="+url+">中显示了。window.webkitURL和window.URL是一样的，
            // window.URL标准定义，window.webkitURL是webkit内核的实现（一般手机上就是使用这个）。
        });
    </script>
    <%-- 模态框触发 --%>
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
            <div class="row">
            <form id="fm" class="form-inline" role="form" action="modifyproduct" method="post">
                <div style="margin: 0 auto; width: 950px; height: 350px">
                    <div class="col-md-6">
                        <img style="cursor: pointer; opacity: 1; width: 400px; height: 350px;" title="" class="medium"
                             src="${pageContext.request.contextPath}/${product.pimage}" onclick=$('#myModal').modal('show')>
                    </div>

                    <div class="col-md-6" style="height:350px">
                        <div class="col-md-6">
                            <div style="width: 350px; margin: 20px 0 10px 0;">
                                    <label class="form-label">名称:</label>
                                    <input class="form-control" type="text" id="pname" name="pname" value="${product.pname}" readonly="readonly">
                            </div>
                            <div style="width: 350px; margin: 20px 0 10px 0;">
                                <label class="form-label">编号:</label>
                                <input class="form-control" type="text" id="pid" name="pid" value="${product.pid}" readonly>
                            </div>

                            <div style="width: 350px; margin: 20px 0 10px 0;">
                                <label class="form-label">单价:</label>
                                <input class="form-control" type="text" id="price" name="price"
                                       required="required"
                                       oninvalid="setCustomValidaty('round to 2 decimal places')"
                                       oninput="setCustomValidity('')"
                                       pattern="^(([1-9]\d{0,9})|0)(\.\d{1,2})?$"
                                       value="${product.price}" readonly="readonly">
                                元 / 件
                            </div>

                            <div  style="width: 350px; margin: 20px 0 10px 0;">
                                <label class="form-label">库存:</label>
                                <input class="form-control form-control-sm" type="text" id="pstorage" name="pstorage" value="${product.pstorage}" maxlength="4" readonly="readonly">
                                件
                            </div>
                        </div>
                        <div class="col-md-6">
                            <a id="modify" style="background-color: #d3d3d3; float:right; display: block" class="btn btn-default" role="button" onclick="modifyinfo()"><strong>修改</strong></a>
                            <a id="confirm" style="background-color: #d3d3d3; float:right; display: none" class="btn btn-default" role="button" onclick=document.getElementById("fm").submit()><strong>确定</strong></a>
                        </div>
                        <div class="col-md-12" style="margin-top: 85px">
                            <a id="down" style="background-color: #eb5339; width: 100px; float:right" class="btn btn-default <%= down %>" role="button" href="${pageContext.request.contextPath}/downproduct?pid=${product.pid}"><strong>下架</strong></a>
                            <a id="up" style="background-color: #59d089; width: 100px; float: right" class="btn btn-default <%= up %>" role="button" href="${pageContext.request.contextPath}/upproduct?pid=${product.pid}"><strong>上架</strong></a>
                        </div>
                    </div>
                </div>
            </form>
            </div>
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

<%-- 模态框 --%>
    <div class="modal fade bs-example-modal-lg in" id="myModal" tabindex="-1"
         role="dialog" aria-labelledby="mySmallModalLabel" style="top: 50px">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">更换图片</h4>
                </div>
                <div class="modal-body">
                    <form accept-charset="UTF-8" action="uploadimage" method="post"
                          enctype="multipart/form-data" class="form-horizontal" id="formImg">
                        <div class="form-group">
                            <!-- accept="image/*"， accept="image/png, image/jpeg, image/gif, image/jpg"无效 -->
                            <div class="col-sm-10" id="up_img">
                                <label for="form_file" class="btn btn-primary">选择图片</label>
                                <input type="text" name="image_pid" id="image_pid" style="display: none"
                                       class="form-control form-control-lg input-block" value="${product.pid}">
                                <!-- style="display:none"有效, hidden=hidden无效, style="position:absolute;clip:rect(0 0 0 0);"有效 -->
                                <input type="file" name="file" id="form_file" accept="image/*"
                                       class="upfile" style="display: none">
                            </div>
                            <div style="padding: 20px" id="imglist">
                                <img id="singleImg" class="img-thumbnail img-responsive book_thumb center-block"
                                     src="${pageContext.request.contextPath}/${product.pimage}" alt="尚未选择图片或图片无效"/>
                            </div>

                        </div>
                        <div class="modal-footer">
                            <input type="submit" class="btn btn-success" value="确认">
                            <button type="button" class="btn btn-default"
                                    data-dismiss="modal">取消
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!-- 引入footer.jsp -->
    <jsp:include page="footer.jsp" flush="true"></jsp:include>

</body>
</html>
