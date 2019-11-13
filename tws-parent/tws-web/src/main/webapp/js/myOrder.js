//假数据
var orders11 = [
    {
        itemid: '1',
        src:'../image/pic1.jpg',
        pname:'【电子书赠品】记忆宫殿一本书快速提升记忆力 下载天猫读书读',
        subtotal:'79.00',
        classify1:'白色',
        classify2:'均码',
        punm:'1',
        state:"0"
    },
    {
        itemid: '2',
        src:'../image/pic1.jpg',
        pname:'【电子书赠品】记忆宫殿一本书快速提升记忆力 下载天猫读书读',
        subtotal:'79.00',
        classify1:'白色',
        classify2:'均码',
        punm:'2',
        state:"0"
    },
    {
        itemid: '3',
        src:'../image/pic1.jpg',
        pname:'【电子书赠品】记忆宫殿一本书快速提升记忆力 下载天猫读书读',
        subtotal:'79.00',
        classify1:'白色',
        classify2:'均码',
        punm:'3',
        state:"0"
    },
    {
        itemid: '4',
        src:'../image/pic1.jpg',
        pname:'【电子书赠品】记忆宫殿一本书快速提升记忆力 下载天猫读书读',
        subtotal:'79.00',
        classify1:'白色',
        classify2:'均刷上海市码',
        punm:'4',
        state:"0"
    },
    {
        itemid: '1',
        src:'../image/pic1.jpg',
        pname:'【电子书赠品】记忆宫殿一本书快速提升记忆力 下载天猫读书读',
        subtotal:'79.00',
        classify1:'白色',
        classify2:'均码',
        punm:'2',
        state:"0"
    },
];
var Ftotal = 10;
//全局数据
var orderList = [];
var orderTotal = 0;


/**************************接口********************************/
var orderDocking = {
    //获取详情页信息
    getOrderRecommend: function(indexPage) {
        $.ajax({
            url:"productAction_detailProduct.action",//路径
            type:"post",//方法
            async:false,//是否缓存
            dataType:"json",//返回值类型
            data: {
                "currentPage": indexPage
            },
            success: function(getComResult) {
                //成功
                if(getComResult.code == "1" ||getComResult.code == 1 ){
                    //将数据写入全局数据
                    orderList = getComResult.data;
                    orderTotal = parseInt(getComResult.total);
                    
                }
                else{
                    alert(getComResult.msg);
                }
            },
            error: function error() {
                alert("网络传输有误！请检查网络连接！");
                 //函数调用
                 orderList = orders11;
                 orderTotal = parseInt(Ftotal);
                
            }
        })
    },
    //删除订单 未完成
    deleteOrderRecommend: function() {
        $.ajax({
            url:"adminOrderAction_deleteOrderItemById.action",//路径
            type:"post",//方法
            async:false,//是否缓存
            dataType:"json",//返回值类型
            data: {
                
            },
            success: function(getComResult) {
                //成功
                if(getComResult.code == "1" ||getComResult.code == 1 ){
                    //将数据写入全局数据
                    orderList = getComResult.data;
                    orderTotal = parseInt(getComResult.total);
                    
                }
                else{
                    alert(getComResult.msg);
                }
            },
            error: function error() {
                alert("网络传输有误！请检查网络连接！");
                 //函数调用
                 orderList = orders11;
                 orderTotal = parseInt(Ftotal);
                
            }
        })
    },
    //搜索订单接口
    searchOrderRecommend: function(str) {
        $.ajax({
            url:"orderAction_findOrdersByPname.action",//路径
            type:"post",//方法
            async:false,//是否缓存
            dataType:"json",//返回值类型
            data: {
                "pname": str,
                "currentPage" : 1
            },
            success: function(getComResult) {
                //成功
                if(getComResult.code == "1" ||getComResult.code == 1 ){
                    //将数据写入全局数据
                    orderList = getComResult.data;
                    orderTotal = parseInt(getComResult.total);
                    
                }
                else{
                    alert(getComResult.msg);
                }
            },
            error: function error() {
                alert("网络传输有误！请检查网络连接！");
                 //函数调用
                 orderList = orders11;
                 orderTotal = parseInt(Ftotal);
                
            }
        })
    },
}


/************************函数方法*************************/
//获取拼接表格行 
var getTable = function(orders){
    var thHtml = 
    "<tr>"+
        "<th>"+
            "<input type='checkbox' class='all' id='checkAll'/>"+
            "<span>全选&nbsp&nbsp&nbsp&nbsp</span>"+
            "<img src='../image/dele.png'/>"+
            "<span id='delete'>&nbsp删除</span>"+
        "</th>"+
        "<th>商品</th>"+
        "<th>价格</th>"+
        "<th>款式</th>"+
        "<th>数量</th>"+
        "<th>操作状态</th>"+
    "</tr>";
    var tdHtml = ""
    //操作状态（-1未发货，0发货未收，1已经收货）
    var statusStr1 = "<span>未发货</span>" ;
    var statusStr2 = "<button id='statusBtn'>确认收货</button>" ;
    var statusStr3 = "<span>已收货</span>" ;
    var status = ""
    for(i=0; i< orders.length ;i++){
        if(orders[i].state === "-1") status = statusStr1;
        if(orders[i].state === "0") status = statusStr2;
        if(orders[i].state === "1") status = statusStr3;
        tdHtml +=
            "<tr>"+
            "<td>"+
                "<input type='checkbox' name='check'/>"+
                "<img src='"+orders[i].src+"'>"+
            "</td>"+
            "<td>"+
                "<span class='title'>"+orders[i].pname+"</span>"+
            "</td>"+
            "<td class='price'>"+
                "¥<span>"+orders[i].subtotal+"</span>"+
            "</td>"+
            "<td class='style'>"+
                "<span>"+orders[i].classify1+ " " +orders[i].classify2+"</span>"+
            "</td>"+
            "<td class='amount'>"+
                "<span>"+orders[i].punm+"</span>"+
            "</td>"+
            "<td class='status'>"+
                status + "<span id='afterStatus'>已收货</span>" +
            "</td>"+
        "</tr>"
        ;
    }
    var tableHtml = thHtml + tdHtml
    return tableHtml;
}
//js分页
//el:分页容器 count:总记录数 pageStep:每页显示多少个 pageNum:第几页 fnGo:分页跳转函数
var jsPage = function(el, count, mainStep, pageNum, fnGo) {
    this.getLink = function(fnGo, index, pageNum, text) {
        var s = '<a href="#p' + index + '" onclick="' + fnGo + '(' + index + ');" ';
        if(index == pageNum) {
            s += 'class="aCur" ';
        }
        text = text || index;
        s += '>' + text + '</a> ';            
        return s;
    }
    
    //总页数
    var pageNumAll = Math.ceil(count / mainStep);
    if (pageNumAll == 1) {
        divPage.innerHTML = '';
        return;
    }
    var itemNum = 5; //当前页左右两边显示个数
    pageNum = Math.max(pageNum, 1);
    pageNum = Math.min(pageNum, pageNumAll);
    var s = '';
    if (pageNum > 1) {
        s += this.getLink(fnGo, pageNum-1, pageNum, '上一页');
    } else {
        s += '<span>上一页</span> ';
    }
    var begin = 1;
    if (pageNum - itemNum > 1) {
        s += this.getLink(fnGo, 1, pageNum) + '... ';
        begin = pageNum - itemNum;
    }
    var end = Math.min(pageNumAll, begin + itemNum*2);
    if(end == pageNumAll - 1){
        end = pageNumAll;
    }
    for (var i = begin; i <= end; i++) {
        s += this.getLink(fnGo, i, pageNum);
    }
    if (end < pageNumAll) {
        s += '... ' + this.getLink(fnGo, pageNumAll, pageNum);
    }
    if (pageNum < pageNumAll) {
        s += this.getLink(fnGo, pageNum+1, pageNum, '下一页');
    } else {
        s += '<span>下一页</span> ';
    }
    var divPage = document.getElementById(el);
    divPage.innerHTML = s;
}
//展示订单内容
function goPage(pageIndex) {
    //调用接口
    console.log("循环")
    orderDocking.getOrderRecommend(pageIndex);//调接口取数据
    var mainStep = 5;//规定每页显示数量
    // var pageStep = orders.length<5?orders.length:5//每页显示数量
    document.querySelector('table').innerHTML = getTable(this.orderList) //传参数为第n页的第一件商品的
    //赋itemid
    // console.log($("#orderTable tr"));
    // $("#orderTable tr").each(function(index){
    //     console.log($(this));
    //     // console.log(orderList[index]);
    //     $(this).attr("itemid",this.orderList[index].itemid)
    // })
    jsPage('divPage', this.orderTotal , mainStep , pageIndex, 'goPage');
}



/********************加载后执行函数*******************/
$(document).ready(function(){
    goPage(1);//第一页

    //删除行
    $("#delete").click(function(){
        var table = $('#orderTable');
        var tab = document.querySelector('table');
        var deleteList = [];//存删除的订单pid
        $("#orderTable tr").each(function(index){
            //删除被选中的列
            if($(this).find("input[name='check']:checked").is(":checked")) {
                //删除购物车接口,传id
                console.log("id",$(this).attr("itemid"));
                orderDocking.deleteOrderRecommend($(this).attr("itemid"));
                $(this).remove();
            }
        });
        //重新请求数据
        // goPage(1);//第一页
    });

    //全选
    $("#checkAll").click(function(){
        console.log("全选");
        var checked = document.getElementById("checkAll").checked;
        var checkson = document.getElementsByName("check");
        if(checked){
            for(var i = 0; i < checkson.length ;i++){
                checkson[i].checked = true;
            }
        }else{
            for(var i = 0; i < checkson.length ;i++){
                checkson[i].checked = false;
            }
        }
    });
    //点击收货改变状态 
    $("#orderTable tr").each(function(index){
        var after = $(this).find("#afterStatus");
        $(this).find("#statusBtn").click(function(){
            console.log("ZHI")
            $(this).css("display","none");
            after.css("display","block");
        })
    });

    //点击搜索
    $("#searchBtn").click(function(){
        var str=document.getElementById("searchIp").value;
        orderDocking.searchOrderRecommend(str)
    });
})


