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

    <%-- 图片触发 --%>
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

</head>
<body>
<!-- 引入header.jsp -->
<jsp:include page="mystore_header.jsp" flush="true"></jsp:include>

&nbsp;

<div class="container" style="margin: 0 auto;">
    <div class="row">
    <form id="fm" role="form" action="addproduct" method="post">
        <input type="text" style="display: none" name="sid" id="sid" value="${user.sid}">
    <div class="col-md-6">
        <label for="form_file" class="btn btn-primary">选择图片</label>
        <input type="file" name="file" id="form_file" accept="image/*"
               class="upfile" style="display: none">
        <img style="width:300px; height: 300px;" id="singleImg" class="img-thumbnail img-responsive book_thumb center-block"
             src="${pageContext.request.contextPath}/images/Files/default.jpg" alt="尚未选择图片或图片无效"/>
    </div>

    <div class="col-md-6">
        <dl class="form-group" style="width: 350px; margin: 20px 0 10px 0;">
            <dt class="input-label">
                <label class="form-label f5">名称</label>
            </dt>
            <dd>
                <input class="form-control" type="text" id="pname" name="pname"
                       required="required" pattern="([A-Za-z0-9\u4e00-\u9fa5]){1,50}"
                       oninvalid="setCustomValidity('最多输入50个汉字、字母或数字')"
                       oninput="setCustomValidity('')"
                       placeholder="请输入商品名">
            </dd>
        </dl>

        <dl class="form-group" style="width: 350px; margin: 20px 0 10px 0;">
            <dt class="input-label">
                <label class="form-label f5">单价</label>
            </dt>
            <dd>
                <input class="form-control" type="text" id="price" name="price"
                       required="required" pattern="^(([1-9]\d{0,9})|0)(\.\d{1,2})?$"
                       placeholder="请输入单价"
                       oninvalid="setCustomValidity('保留两位小数')"
                       oninput="setCustomValidity('')">
            </dd>
        </dl>

        <dl class="form-group" style="width: 350px; margin: 20px 0 10px 0;">
            <dt class="input-label">
                <label class="form-label f5">库存</label>
            </dt>
            <dd>
                <input class="form-control"  type="number" id="pstorage" name="pstorage"
                       required="required" min="1" max="9999"
                       placeholder="请输入库存数量">
            </dd>
        </dl>

        <dl class="form-group" style="width: 350px; margin: 20px 0 10px 0;">
            <dt class="input-label">
                <label class="form-label f5">分类</label>
            </dt>
            <dd class="btn-group">
                <button type="button" class="btn btn-default btn-block dropdown-toggle sbutton"
                        data-toggle="dropdown" aria-haspopup="true"
                        aria-expanded="false" id="drop_button">
                    移动通讯
                    <span class="caret"></span>
                </button>
                <ul id="categoryMenu" class="dropdown-menu">
                    <%-- 动态加载 --%>
                </ul>
            </dd>
            <input type="text" name="category" id="category" value="移动通讯" hidden>
        </dl>

        <dl class="form-group" style="width: 350px; margin: 20px 0 10px 0;">
            <dt class="input-label">
                <label class="form-label f5">商品简介</label>
            </dt>
            <dd>
                <input class="form-control" type="text" id="pdesc" name="pdesc"
                       required="required"
                       placeholder="请输入商品简介">
            </dd>
        </dl>

        <button type="submit" id="confirm" class="btn btn-success" style="float:right;"><strong>确定</strong></button>

    </div>
    </form>
    </div>
</div>
<script type="text/javascript">
    // hearder.jsp加载完成后，去服务器端获得所有的category数据
    $(function () {
        var content = "";
        $.post(
            "${pageContext.request.contextPath}/categoryList",
            function (data) {
                //    [{"cid":"xxx","cname":"xxx"},{},{}]
                //    动态的创建li
                for (var i = 0; i < data.length; i++) {
                    content += "<li id='" + data[i].cname + "'><a href=\"#\">"+data[i].cname+"</a></li>";
                }

                //    将拼接好的li放置到ul中
                $("#categoryMenu").html(content);
            },
            "json"
        );
    });
</script>

<!-- 引入footer.jsp -->
<jsp:include page="footer.jsp" flush="true"></jsp:include>
</body>
</html>
