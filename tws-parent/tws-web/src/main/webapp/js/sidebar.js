////////////////////////////////////// 侧 边 栏 ///////////////////////////////////////////////
var countList = [];//结算商品列表
var placeOrder = false;//判断是立即购买还是结算，false为立即购买
//获取侧边栏足迹数据
function getHistoryData(hData){
    var str = "";
    var hUl = document.getElementById("sHistoryUl");
    for ( var i = 0; i < hData.length; i++) {
    	str +=  "<li>"+
                    "<img src='"+hData[i].psrc1+"'>"+
                    "<span>¥"+hData[i].shop_price+"</span>"+
                "</li>" ;
    }
    hUl.innerHTML = str
    //赋pid
    $("#sHistoryUl li").each(function(index){
        $(this).attr("pid",hData[index].pid)
    })
}

//获取侧边栏购物车
function getCartData(cData){
    var str = "";
    var cUl = document.getElementById("sCartUl");
    for ( var i = 0; i < cData.length; i++) {
    	str +=  "<li>"+
                    "<input type='checkbox' class='cartCheck' name='cCheck'/>"+
                    "<img src='"+cData[i].psrc1+"' class='cartPic' id='cartPic'/>"+
                    "<div class='cartClassify'>"+
                        "<span id='cartClassify1'>"+cData[i].classify1+"</span>"+
                        "<span id='cartClassify2' style='display: block;margin-top:5px;'>"+cData[i].classify2+"</span>"+
                    "</div>"+
                    "<div class='cartNum'>"+
                        "<img src='../image/minus.png' class='cartBtn' id='cSubBtn'/>"+
                        "<span style='font-size:16px;margin-left:2px;' id='cartNum'>"+cData[i].buyNum+"</span>"+
                        "<img src='../image/plus.png' class='cartBtn' id='cAddBtn'/>"+
                    "</div>&nbsp;"+
                    "<div class='cartPrice'>"+
                        "¥<span id='cPirce'>"+cData[i].shop_price+"</span>"+
                    "</div>"+
                "</li>" ;
    }
    cUl.innerHTML = str
    //赋pid和cid
    $("#sCartUl li").each(function(index){
        $(this).attr("pid",cData[index].pid)
    })
}

//计算总价
function getTotal(){
    var total = 0;
    $("input[name='cCheck']:checked").each(function(){
        total += parseFloat($(this).parent().find("#cPirce")[0].innerText);  
    })
    $("#cartTotal")[0].innerText = total.toFixed(2);
}

//判断侧边栏足迹和购物车是否为空
function sidebarIsEmpty(){
    //购物车为空
    if($("#sCartUl").find("li").length === 0){
        document.getElementById("emptyFont1").style.display = "block";
    }
    //足迹为空
    if($("#sHistoryUl").find("li").length === 0){
        document.getElementById("emptyFont2").style.display = "block";
    }
}


/***********************接口*****************************/
var sidebarDocking = {
    //足迹
    sidebarRecommend1: function() {
        $.ajax({
            url:"productAction_historyProduct.action",//路径
            type:"post",//方法
            async:false,//是否缓存
            dataType:"json",//返回值类型
            success: function(getComResult) {
                //成功
                if(getComResult.code == "1" ||getComResult.code == 1 ){
                    //调用函数生成页面数据 
                    getHistoryData(getComResult.historyData);
                }
                else{
                    alert(getComResult.message);
                }   
            },
            error: function error() {
                alert("网络传输有误！请检查网络连接！");
                getHistoryData([
                   {
                       "psrc1": "../image/pic1.jpg",
                       "pid": "1",
                       "shop_price": "20.00"
                   },
                   {
                       "psrc1": "../image/pic2.jpg",
                       "pid": "2",
                       "shop_price": "40.00"
                   },
                   {
                       "psrc1": "../image/pic1.jpg",
                       "pid": "1",
                       "shop_price": "20.00"
                   },
                   {
                       "psrc1": "../image/pic2.jpg",
                       "pid": "2",
                       "shop_price": "40.00"
                   }
                ])
                
            }
        });
    },
    //清空足迹
    cleanHistoryRecommend: function() {
        $.ajax({
            url:"productAction_clearHistoryProduct.action",//路径
            type:"post",//方法
            async:false,//是否缓存
            dataType:"json",//返回值类型
            data:{
                //传个清空标识
            },
            success: function(getComResult) {
                //成功
                if(getComResult.code == "1" ||getComResult.code == 1 ){
                    alert(getComResult.msg);
                }
                else{
                    alert(getComResult.msg);
                }   
            },
            error: function error() {
                alert("网络传输有误！请检查网络连接！");  
            }
        });
    },
    //购物车
    sidebarRecommend2: function() {
        $.ajax({
            url:"productAction_getCart.action",//路径
            type:"post",//方法
            async:false,//是否缓存
            dataType:"json",//返回值类型
            success: function(getComResult) {
                //成功
                if(getComResult.code == "1" ||getComResult.code == 1 ){
                    //调用函数生成页面数据 
                    getCartData(getComResult.data);
                }
                else{
                    alert(getComResult.msg);
                }   
            },
            error: function error() {
                alert("网络传输有误！请检查网络连接！");
                getCartData([
                    {
                        "psrc1": "../image/pic1.jpg",
                        "pid": "1",
                        "shop_price": "20.00",
                        "classify1": "黑色",
                        "classify2": "均码",
	                    "buyNum": "2",
                    },
                    {
                        "psrc1": "../image/pic1.jpg",
                        "pid": "1",
                        "shop_price": "20.00",
                        "classify1": "黑色",
                        "classify2": "均码",
	                    "buyNum": "4",
                    },
                    {
                        "psrc1": "../image/pic1.jpg",
                        "pid": "1",
                        "shop_price": "20.00",
                        "classify1": "白色",
                        "classify2": "均码",
	                    "buyNum": "2",
                    },
                    {
                        "psrc1": "../image/pic1.jpg",
                        "pid": "1",
                        "shop_price": "20.00",
                        "classify1": "均码",
                        "classify2": "薰衣草味",
	                    "buyNum": "4",
                    }
                ])
                
            }
        });
    },
    //删除购物车
    cleanCartRecommend: function(pid) {
        $.ajax({
            url:"productAction_delProFromCart.action",//路径
            type:"post",//方法
            async:false,//是否缓存
            dataType:"json",//返回值类型
            data:{
                //传数组
                "pid" : pid
            },
            success: function(getComResult) {
                //成功
                if(getComResult.code == "1" ||getComResult.code == 1 ){
                }
                else{
                    alert(getComResult.msg);
                }   
            },
            error: function error() {
                alert("网络传输有误！请检查网络连接！");  
            }
        });
    },
    //购物车结算接口
    CartCountRecommend: function(goodsList,total) {
        console.log("结算接口",goodsList);
        console.log("总金额",total);
        var object={};
        object['orderArray'] = goodsList;
        $.ajax({
            url:"orderAction_addToOredrsInCast.action",//路径
            type:"post",//方法
            async:false,//是否缓存
            dataType:"json",//返回值类型
            data:{
                //传数组
                "total" : total,
                "orderJson" : JSON.stringify(object)
            },
            success: function(getComResult) {
                //成功
                if(getComResult.code == "1" ||getComResult.code == 1 ){
                    alert(getComResult.msg);
                }
                else{
                    alert(getComResult.msg);
                }   
            },
            error: function error() {
                alert("网络传输有误！请检查网络连接！");  
            }
        });
    },
     //获取收货信息
     getReceivingRecommend:function(){
        $.ajax({
            url:"userAction_getUserInform.action",//路径
            type:"post",//方法
            async:false,//是否缓存
            dataType:"json",//返回值类型
            data:{
            },
            success: function(getComResult) {
                //成功
                if(getComResult.code == "1" ||getComResult.code == 1 ){
                    return getComResult.data;
                }
                else{
                    alert(getComResult.msg)
                }   
            },
            error: function error() {
                alert("网络传输有误！请检查网络连接！");
            }
            
        })
    },
}



//加载后执行函数
$(document).ready(function(){
    //调用接口
    sidebarDocking.sidebarRecommend1();
    sidebarDocking.sidebarRecommend2();
    //判断购物车和足迹是否为空
    sidebarIsEmpty();

    // // 侧边栏变换
    // //点击历史记录图标
    var isH = true;
    var isOut = false;
    document.getElementById("historySty").onclick = function(){ 
        var hBox = document.getElementById("historyBox")
        var cBox = document.getElementById("cartBox")
        var sLeft = document.getElementById("sidebarLeft")
        cBox.style.display = "none"; //将购物车隐藏
        hBox.style.display = "block";
        //侧边栏未被拉出来,显示hBox
        if(isOut === false){
            sLeft.style.right = "336px";
            hBox.style.right = "0px";
            isOut = true;
        }
        //侧边栏被拉出来，此时状态为显示历史浏览，收回
        else if(isH === true){
            hBox.style.right = "-336px";
            sLeft.style.right = "0px";
            isOut = false;
        }
        //侧边栏被拉出来，此时状态为显示购物车，切换div
        else{
            sLeft.style.right = "336px";
            hBox.style.right = "0px";
            cBox.style.display = "none"; //将购物车隐藏
            hBox.style.display = "block";
            isH = true;
        }


    }

    //点击购物车图标
    document.getElementById("cartSty").onclick = function(){ 
        var hBox = document.getElementById("historyBox")
        var cBox = document.getElementById("cartBox")
        var sLeft = document.getElementById("sidebarLeft")
        hBox.style.display = "none"; //将历史记录隐藏
        cBox.style.display = "block";
        if(isOut === false){
            sLeft.style.right = "336px";
            cBox.style.right = "0px";
            isOut = true;
            isH = false;
        }
        //侧边栏被拉出来，此时状态为显示购物车，收回
        else if(isH === false){
            sLeft.style.right = "0px";
            cBox.style.right = "-336px";
            isOut = false;
        }
        //侧边栏被拉出来，此时状态为显示历史，切换div
        else{
            sLeft.style.right = "336px";
            cBox.style.right = "0px";
            hBox.style.display = "none"; 
            cBox.style.display = "block";
            isH = false;
        }
        
    }

    //点击侧边栏购物车内容
    $("#sCartUl li").each(function(index){
        //点击数量选择框 
        var txt = $(this).find("#cartNum");
        var total = $(this).find("#cPirce");
        var unitPrice = parseFloat(total[0].innerText / txt[0].innerText); //商品单价 -float型
        var pid = $(this).attr('pid');//某商品pid
        //加
        $(this).find("#cAddBtn").click(function(){
            if(txt[0].innerText<200){
                txt[0].innerText = parseInt(txt[0].innerText)+1;
            }
            else txt[0].innerText = 200;
            //计算某件商品总价，件数*单价
            total[0].innerText = (parseInt(txt[0].innerText) * unitPrice).toFixed(2);
            //计算总价
            getTotal();
        })

        //减
        $(this).find("#cSubBtn").click(function(){
            if(txt[0].innerText>1){
                txt[0].innerText = parseInt(txt[0].innerText)-1;
            }
            //计算某件商品总价，件数*单价
            total[0].innerText = (parseInt(txt[0].innerText) * unitPrice).toFixed(2);
            //计算总价
            getTotal();
        })

        //点击图片跳到详情页
        $(this).find("#cartPic").click(function(){
            sidebarDocking.toDetail(pid);
        })
    })

    //全选
    document.getElementById("cartCheckAll").onclick = function(){
        var checked = document.getElementById("cartCheckAll").checked;
        var checkson = document.getElementsByName("cCheck");
        if(checked){
            for(var i = 0; i < checkson.length ;i++){
                checkson[i].checked = true;
            }
        }else{
            for(var i = 0; i < checkson.length ;i++){
                checkson[i].checked = false;
            }
        }
        //计算件数
        var len = $("input[name='cCheck']:checked").length
        $("#cartSum")[0].innerText = len
        
        //计算总价
        getTotal();
    }

    //点击选择框，计算件数和
    $("input[name='cCheck']").click(function(){
        var total = 0;
        var len = $("input[name='cCheck']:checked").length; 
        $("#cartSum")[0].innerText = len ;
        //计算总价
        getTotal();
    })

    //购物车删除
    $("#cartDelete").click(function(){
        $("#sCartUl li").each(function(index){
            //删除被选中的列
            if($(this).find("input[name='cCheck']:checked").is(":checked")) {
                //删除购物车接口
                sidebarDocking.cleanCartRecommend($(this).attr("pid"));
                $(this).remove();
            }
        });
        //购物车为空
        if($("#sCartUl").find("li").length === 0){
            document.getElementById("emptyFont1").style.display = "block";
        }
        //已选变为 0
        $("#cartSum")[0].innerText = "0";
        //点击删除后，金额变为0
        $("#cartTotal")[0].innerText = "0.00";
        alert("删除成功！");
    })

    //清空历史浏览
    $("#historyClean").click(function(){
        $("#sHistoryUl li").remove();
        document.getElementById("emptyFont2").style.display = "block"
    })

    //点击订单 ，跳转页面
    $("#bagSty").click(function(){
        window.location.href='myOrder.html';
    })


    //点击页面跳转
    //遮罩层
    //弹出层
    var sheight = document.documentElement.scrolllHeight;
    var swidth = document.documentElement.scrollWidth;
    var imask = document.getElementById("mask");
    var iclose = document.getElementById("close");
    var buyform = document.getElementById("buyForm");

    var dheight = document.documentElement.clientHeight;
    var dwidth = document.documentElement.clientWidth;

    //点击结算
    $("#accountBtn").click(function(){
        $("#sCartUl li").each(function(index){
            //被选中的列
            if($(this).find("input[name='cCheck']:checked").is(":checked")) {
                var countObj = {
                            pid : '',
                            punm : '',
                            classify1 : '',
                            classify2 : '',
                            subtotal : '',
                            state : ''
                        };//一组商品对象，必须写在循环里，每次循环都创建新对象，如不创建新对象，只改变值，则数组全是对象最新的值。
                countObj.pid = $(this).attr('pid');
                countObj.punm = $(this).find("#cartNum")[0].innerText;
                countObj.classify1 = $(this).find("#cartClassify1")[0].innerText;
                countObj.classify2 = $(this).find("#cartClassify2")[0].innerText;
                countObj.subtotal = $(this).find("#cPirce")[0].innerText;
                countObj.state = -1;
                //往结算列表中添加一组数据
                countList.push(countObj);
            }
        });
        placeOrder = true;//判断是立即购买还是结算下单
        buySwift(1,buyform);
        //调接口取收货信息
        var Receiving = Docking.getReceivingRecommend();
        document.getElementById("Bconsignee").value = Receiving.autopconsignee;
        document.getElementById("Bphone").value = Receiving.autotelephone;
        document.getElementById("Baddres").value = Receiving.autoaddress;
    })
    //点击取消
    $('#BcloseBtn').click(function(){
        buySwift(2,buyform);
        //解除禁止滚动条
        $(document).unbind("scroll.unable");
        window.parent.location.reload();  //刷新
    })
    //点击遮罩层
    $('#mask').click(function(){
        buySwift(2,buyform);
        //解除禁止滚动条
        $(document).unbind("scroll.unable");
        window.parent.location.reload();  //刷新
    })
     //点击关闭弹框
     $('#close').click(function(){
        buySwift(2,buyform);
        //解除禁止滚动条
        $(document).unbind("scroll.unable");
        window.parent.location.reload();  //刷新
    })
    //确认下单
    $('#BsubmitBtn').click(function(){   
        var name = document.getElementById("Bconsignee").value;//收货人
        var address = document.getElementById("Baddres").value;//地址
        var phone = document.getElementById("Bphone").value;//电话
        if(placeOrder == false){//判断是立即购买还是结算下单,调用不同接口
            Docking.buyRecommend(txt.value,name,address,phone);
        }else{
            //购物车结算接口
            console.log("总金额1",$("#cartTotal")[0].innerText);
            sidebarDocking.CartCountRecommend(countList,$("#cartTotal")[0].innerText);
            $(this).remove();
        }
        buySwift(2,buyform);
        window.parent.location.reload();  //刷新
    })
    //遮罩层 #mask用户信息的body里自带。
    function buySwift(now,form){
        var dis;
        if(now == 1){
            dis = "block";
        }
        else if(now == 2){
            dis = "none";
        }
        form.style.display = dis;
        imask.style.display = dis;
        iclose.style.display = dis;
        imask.style.height = sheight+"px";
        var jwidth = form.offsetWidth;
        var jheight = form.offsetHeight;
        form.style.left = (dwidth-jwidth)/2+"px";
        form.style.top = (dheight-jheight)/2+"px";
        //阻止页面滚动特效
        var top = $(document).scrollTop();
        $(document).on('scroll.unable',function (e) {
            $(document).scrollTop(top);
        })
    }
})
/////////////////////////////////////////////////////////////////////////////////////////////////