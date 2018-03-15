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