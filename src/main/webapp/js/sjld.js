$(document).ready(
    function(){
        $.ajax({
            url:"bindZ",
            type:"get",
            dataType:"json",
            success:bindZList
        });
    }
);

//回调函数
function bindZList(json){
    data=(json.a);
    for(zmc in data){
        var option = document.createElement("option");
        for(key in data[zmc]){
            document.getElementById("bindZ").appendChild(option);
            option.text=data[zmc];
        }
    }
}



function getKcbh(){
    //绑定之前 清空第一个以外的option
    $("#bindK").children().eq(0).siblings().remove();
    $("#bindZsd").children().eq(0).siblings().remove();
    var temp=$("#bindZ").find("option:selected").val();
    $.ajax({
        url:"bindK",
        type:"get",
        dataType:"json",
        data:"zmc="+temp,
        success:bindKList
    });
}
//回调函数
function bindKList(json){
    data=(json.k);
    for(CName in data){
        var option = document.createElement("option");
        for(key in data[CName]){
            document.getElementById("bindK").appendChild(option);
            option.text=data[CName];
        }
    }
}


function getZsd(){
    //绑定之前 清空第一个以外的option
    $("#bindZsd").children().eq(0).siblings().remove();
    var temp = $("#bindK").find("option:selected").val();
    $.ajax({
        url:"bindZsd",
        type:"get",
        dataType:"json",
        data:"CName="+temp,
        success:bindZsdList
    });
}
//回调函数
function bindZsdList(json){
    data=(json.s);
    for(zsdmc in data){
        var option = document.createElement("option");
        for(key in data[zsdmc]){
            document.getElementById("bindZsd").appendChild(option);
            option.text=data[zsdmc];
        }
    }
}