<%--
  Created by IntelliJ IDEA.
  User: YUAN YUE
  Date: 2018/3/13
  Time: 10:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%--必须有，否则无法获取到设备窗口大小--%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>我的店铺-订单列表</title>
<%--古董玩意儿，别用了，丢人
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css"/>--%>
    <script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
    <%--<script src="js/bootstrap.min.js" type="text/javascript"></script>--%>

    <%--最新版网络资源，不能和上面的一起用--%>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
    <%--<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>--%>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!-- 引入自定义css文件 style.css -->
    <%--别用这个：会导致搜索框无法水平对齐<link rel="stylesheet" href="css/style.css" type="text/css"/>--%>
    <link rel="stylesheet" href="css/bootstrap-table.css" type="text/css"/>
    <script src="js/bootstrap-table.js" type="text/javascript"></script>
</head>
<body>
<jsp:include page="mystore_header.jsp" flush="true"></jsp:include>

<!-- start: 自定义表格 -->
<div class="container-fluid">
    <div>
        <div class="col-lg-12">

<%--            <div id="toolbar">
                <div class="btn btn-primary" data-toggle="modal" data-target="#addModal">添加记录</div>
            </div>--%>

            <table id="mytab" class="table table-hover"></table>

            <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                ×
                            </button>
                            <h4 class="modal-title" id="myModalLabel">填写物流</h4>
                        </div>
                        <div class="modal-body">
                            <form role="form" action="javascript:void(0)">
                                <div class="form-group">
                                    <input type="text" class="form-control" id="company" placeholder="请输入物流公司">
                                </div>
                                <div class="form-group">
                                    <input type="text" class="form-control" id="did" placeholder="请输入快递单号">
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary" id="addRecord">确认发货</button>
                        </div>
                    </div>
                </div>
            </div>
<!-- end: 自定义表格 -->
<jsp:include page="footer.jsp" flush="true"></jsp:include>
<script type="text/javascript">
    $(function() {
        //根据窗口调整表格高度
        $(window).resize(function() {
            $('#mytab').bootstrapTable('resetView', {
                height: tableHeight()
            })
        })

        $('#mytab').bootstrapTable({
            url: "getjson",//数据源
            dataField: "rows",//服务端返回数据键值 就是说记录放的键值是rows，分页时使用总记录数的键值为total
            height: tableHeight(),//高度调整
            search: true,//是否搜索
            pagination: true,//是否分页
            pageSize: 20,//单页记录数
            pageList: [5, 10, 20, 50],//分页步进值
            sidePagination: "server",//服务端分页server，client
            contentType: "application/x-www-form-urlencoded",//请求数据内容格式 默认是 application/json 自己根据格式自行服务端处理
            dataType: "json",//期待返回数据类型
            method: "post",//请求方式
            searchAlign: "left",//查询框对齐方式
            queryParamsType: "limit",//查询参数组织方式
            queryParams: function getParams(params) {
                //params obj
                params.other = "otherInfo";
                return params;
            },
            searchOnEnterKey: false,//回车搜索，默认是关闭的
            showRefresh: true,//刷新按钮
            showColumns: true,//列选择按钮
            buttonsAlign: "left",//按钮对齐方式
            toolbar: "#toolbar",//指定工具栏
            toolbarAlign: "right",//工具栏对齐方式
            columns: [
                /*{
                    title: "全选",
                    field: "select",
                    checkbox: true,
                    width: 20,//宽度
                    align: "center",//水平
                    valign: "middle"//垂直
                },*/
                {
                    title: "订单号",//标题
                    field: "oid",//键名
                    sortable: false,//是否可排序
                    order: "desc",//默认排序方式
                    titleTooltip: "订单号"
                },
                {
                    title: "UID",
                    field: "uid",
                    sortable: false,
                    titleTooltip: "买家ID"
                },
                {
                    field: "amount",
                    title: "商品金额",
                    sortable: true,
                    titleTooltip: "实付款"
                },
                {
                    field: "create_time",
                    title: "下单时间",
                    //formatter: 'infoFormatter',//对本列数据做格式化
                    sortable: true,
                    titleTooltip: "下单时间"
                },
                {
                    field: "pay_time",
                    title: "付款时间",
                    //formatter: 'infoFormatter',//对本列数据做格式化
                    sortable: true,
                    titleTooltip: "付款时间"
                },
                {
                    field: "consignee",
                    title: "收货人",
                    sortable: false,
                    titleTooltip: "收件人真实姓名"
                },{
                    field: "phone",
                    title: "手机号",
                    titleTooltip: "收件人联系方式"
                },
                {
                    field: "address",
                    title: "收货地址",
                    sortable: false,
                    titleTooltip: "收货地址"
                },
                {
                    field: "ps",
                    title: "备注",
                    sortable: false,
                    titleTooltip: "备注信息"
                },
                {
                    field: "state",
                    title: "订单状态",
                    titleTooltip: "状态"
                },
                {
                    field: "option",
                    title: "操作",
                    sortable: false,
                    formatter: 'addButton',
                    titleTooltip: "可行的操作"
                }
            ],
            onClickRow: function(row, $element) {
                //$element是当前tr的jquery对象
                //alert("Click a order\r\n那你很棒棒哦");
                //$element.css("background-color", "green");
            },//单击row事件
            locale: "zh-CN", //中文支持
            detailView: true, //是否显示详情折叠
            detailFormatter: function(index, row, element) {
                var html = '';
                $.each(row, function(key, val){
                    html += "<p>" + key + ":" + val +  "</p>"
                });
                return html;
            }
        });

        //Model的确认按钮
        $("#addRecord").click(function(){
            alert("name:" + $("#name").val() + " age:" +$("#age").val());
        });
    })

    function tableHeight() {
        return $(window).height() - 150;
    }
    /**
     * 列的格式化函数 在数据从服务端返回装载前进行处理，可以处理诸如时间格式、性别等
     * @param  {[type]} value [description]
     * @param  {[type]} row   [description]
     * @param  {[type]} index [description]
     * @return {[type]}       [description]
     */
    function infoFormatter(value, row, index)
    {
        return "id:" + row.id + " name:" + row.name + " age:" + row.age;
    }
    function addButton(value,row,index)
    {
        var buttons='<button class="btn btn-success">详情</button>'
            + '&nbsp;&nbsp;'
            + '<button class="btn btn-warning" data-toggle="modal" data-target="#addModal">发货</button>';
        return buttons;
    }
</script>
</body>
</html>