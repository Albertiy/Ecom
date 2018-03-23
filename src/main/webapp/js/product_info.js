function modifyinfo() {
    var rs = document.querySelectorAll("input[readonly='readonly']");
    for (var i = 0; i < rs.length; i++) {
        rs[i].readOnly = false;
    }
    var a1 =document.getElementById("modify");
    a1.style.display="none";
    var a2 =document.getElementById("confirm");
    a2.style.display="block";
};
/*
function clearNoNum(obj){
    //修复第一个字符是小数点 的情况.
    if(obj.value !=''&& obj.value.substr(0,1) == '.'){
        obj.value="";
    }
    obj.value = obj.value.replace(/^0*(0\.|[1-9])/, '$1');//解决 粘贴不生效
    obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符
    obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数
    if(obj.value.indexOf(".")< 0 && obj.value !=""){//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额
        if(obj.value.substr(0,1) == '0' && obj.value.length == 2){
            obj.value= obj.value.substr(1,obj.value.length);
        }
    }
};*/

$(document).ready(function () {
    /*     $("#search_type").click(function () {
            var val=$(this).text();
            $("#drop_button").val(val);
            $('.footer').css("background-color", "#336699");
        }); */

    //获取所有li的节点
    var items = document.querySelectorAll("li");
    var dropButton=document.getElementById("drop_button");
    // 可以使用Array.prototype.forEach.call进行遍历
    [].forEach.call(items, function (item) {
        //添加click事件
        item.addEventListener("click", function () {
            console.log(this.id);
            var stype = this.id;
            dropButton.innerHTML=stype+" <span class='caret'></span>";
            $("#Category").val(stype);
            var s = $("#Category").val();
        });
    });
});

