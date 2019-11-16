//全局
var c1Value = ""; //分类1
var c2Value = ""; //分类2
var goodsId = ""; //商品id
var firstClassify = ""; //商品一级分类
var Iusername = "tammie";//用户名
    
var NowBox ="";//记录是否打折
var NowPrice = ""; //记录显示的价格

var highPri = "";
var lowPri = "";

var placeOrder = false;//判断是立即购买还是结算，false为立即购买

// 侧边栏全局变量

//模拟数据
var wPriceValue = ['15.90','18.00','80.80']
var comment = [
    '宝贝不错，是正品，物美价廉，物超所值，样式新颖，值得购买。',
    '裙子很柔软，但是我个人上身效果不太理想。做工不错的。好评',
    '物廉价美！物流很快！售后服务也非常好！值得购买！',
    '时尚简约，百塔型的风格，休闲的时候穿着舒服比较喜欢，质量挺好的，物流也比较快。',
    '全五星好评，很不错的卖家，生意兴隆，期待下次合作'
]

//首部搜索框
var Dom = {
    topSearch: {
        input: document.getElementsByTagName("header")[0].getElementsByTagName("input")[0],
        aside: document.getElementsByTagName("header")[0].getElementsByTagName("aside")[0]
    }
}

//获取价格
function isDiscount(status,market_Price,shop_Price){
    var bfBox = document.getElementById("bfPriBox");
    var afBox = document.getElementById("afPriBox");
    var bfPri1 = document.getElementById("beforePrice1");
    var afPri = document.getElementById("afterPrice");
    var bfPri2 = document.getElementById("beforePrice2");
    //不打折
    if(status != 1){
        afBox.style.display = "none";
        bfBox.style.display = "inline";
        bfPri1.innerHTML = parseFloat(market_Price).toFixed(2); 
        NowBox = bfPri1
        NowPrice = market_Price
    }
    //打折
    else{
        afBox.style.display = "inline";
        bfBox.style.display = "none";
        bfPri2.innerHTML = parseFloat(market_Price).toFixed(2) ;
        afPri.innerHTML = parseFloat(shop_Price).toFixed(2);
        NowBox = afPri
        NowPrice = shop_Price
    }
}

//获取商品两个分类数据，自动生成li
function getClassify(classify1,classify2,classifyLen1,classifyLen2){
    var c1 = document.getElementById("classify1");
    var c2 = document.getElementById("classify2");
    var cDt1 = document.getElementById("cDt1");
    var cDt2 = document.getElementById("cDt2");
    var str1 = "";
    var str2 = "";
    //切割分类名，在中间加空格
    cDt1.innerHTML = classify1[0].charAt(0)+"&nbsp;&nbsp;&nbsp;&nbsp;"+classify1[0].charAt(1)+":&nbsp;";
    cDt2.innerHTML = classify2[0].charAt(0)+"&nbsp;&nbsp;&nbsp;&nbsp;"+classify2[0].charAt(1)+":&nbsp;";
    for(var i=1; i<classifyLen1; i++){
        str1 += "<li><span>"+classify1[i]+"</span></li>"
    }
    for(var i=1; i<classifyLen2; i++){
        str2 += "<li><span>"+classify2[i]+"</span></li>"
    }
    c1.innerHTML = str1;
    c2.innerHTML = str2;
}


//获取商品标题、发货地、图片
function getGoodsData(src1,src2,src3,src4,src5,title,address,historyData){
    var titleId = document.getElementById("title");
    var addressId = document.getElementById("address");
    var str = "" //自动生成浏览列表
    titleId.innerHTML = title;
    addressId.innerHTML = address;

    //获取图片
    var showImg = $("#showButtom ul li img");
    $(showImg[0]).attr("src", src1);//改变src
    $(showImg[1]).attr("src", src2);//改变src
    $(showImg[2]).attr("src", src3);//改变src
    $(showImg[3]).attr("src", src4);//改变src

    //大图绑定第一张
    $("#showPic").attr("src", src1);

    //商品详情图片
    var detailImg = $("#detail li img");
    $(detailImg[0]).attr("src", src5);//改变src
    
    //看了又看内容
    var historyUl = document.getElementById("wPicBoxUl");
    for ( var i = 0; i < historyData.length; i++) {
    	str += "<li>"+
					"<img src='"+historyData[i].psrc1+"' class='wPic'>"+
					"<div class='wPrice'>¥"+
						"<span>"+historyData[i].shop_price+"</span>"+
					"</div>"+
				"</li>";
    }
    historyUl.innerHTML = str;
    //看了又看赋pid
    $("#wPicBoxUl li").each(function(index){
        $(this).attr("pid",historyData[index].pid);
    })
    
    //获取评价
    var contentP = $("#content ul li p");
    for ( var i = 0; i < 5; i++) {
        $(contentP[i]).text(comment[i]);
    }
}

//显示用户名
function showName(){
    document.getElementById("userName").innerHTML = Iusername;
}

//加载左边图片
$("#showButtom ul li img").each(function(index){
    $(this).mouseover(function(){
        const liNode = $(this);
        $("#showPic").attr("src", $(this)[0].src);
    })
})

//不同分类，记录价格不同
function setDifferPrice(shop_price){
    highPri = parseInt(shop_price)*0.9;
    lowPri = parseInt(shop_price)*0.8;
}


//获取后台数据
var Docking = {
    //获取详情页信息
    recommend1: function() {
        $.ajax({
            url:"productAction_detailProduct.action",//路径
            type:"post",//方法
            async:false,//是否缓存
            dataType:"json",//返回值类型
            success: function(getComResult) {
                //成功
                if(getComResult.code == "1" ||getComResult.code == 1 ){
                    //获取用户名
                    Iusername = getComResult.username;
                    //获取id
                    goodsId = getComResult.data.pid;
                    //获取一级分类
                    firstClassify = getComResult.data.cid;
                    //调用函数生成页面数据 
                    getGoodsData(
                        getComResult.data.psrc1,
                        getComResult.data.psrc2,
                        getComResult.data.psrc3,
                        getComResult.data.psrc4,
                        getComResult.data.psrc5,
                        getComResult.data.pname,
                        getComResult.data.paddress,
                        getComResult.historyData //看了又看数据为对象数组
                    )
                    //调用函数生成页面数据
                    isDiscount(getComResult.data.pstatus,
                        getComResult.data.market_price,
                        getComResult.data.shop_price
                    )
                    //调用函数生成两个分类信息
                    getClassify(getComResult.classify1, 
                                getComResult.classify2,
                                getComResult.classify1.length,
                                getComResult.classify2.length
                    )
                    //设置价格
                    setDifferPrice(getComResult.data.shop_price)
                }
                else{
                    alert(getComResult.msg);
                }   
            },
            error: function error() {
                alert("网络传输有误！请检查网络连接！");
               var getComResult = {
                   "code": 1,
                   "msg": "成功返回商品详情",
                   "uid": "用户账号",
                   "classify1": ["分类","大号","中号","小号"],
                   "classify2": ["口味","薰衣草","香草","柠檬"],
                   "data": {
                       "psrc1": "../image/pic1.jpg",
                       "psrc2": "../image/pic1.jpg",
                       "psrc3": "../image/pic1.jpg",
                       "psrc4": "../image/pic1.jpg",
                       "psrc5": "../image/pic1.jpg",
                       "pid":  "1",
                       "cid":  "1",
                       "pstatus": "1",
                       "pname":"秋冬连帽卫衣女潮ins宽松韩版上衣薄款春秋装2019新款中长款外套",
                       "market_price": "40.00",
                       "shop_price": "20.00",
                       "paddress": "广东广州",
                   },
                   "historyData":[
                       {
                       "psrc1": "../image/pic1.jpg",
                       "pid":  "1",
                       "shop_price": "20.00"
                       },
                       {
                       "psrc1": "../image/pic1.jpg",
                       "pid":  "2",
                       "shop_price": "40.00"
                       },
                       {
                       "psrc1": "../image/pic1.jpg",
                       "pid":  "3",
                       "shop_price": "50.00"
                       }
                   ]
               };
               // //获取id
                goodsId = getComResult.data.pid;
               // //获取一级分类
               firstClassify = getComResult.data.cid;
               //调用函数生成页面数据 
               getGoodsData(
                   getComResult.data.psrc1,
                   getComResult.data.psrc2,
                   getComResult.data.psrc3,
                   getComResult.data.psrc4,
                   getComResult.data.psrc5,
                   getComResult.data.pname,
                   getComResult.data.paddress,
                   getComResult.historyData //看了又看数据为对象数组
               )
               //调用函数生成页面数据
               isDiscount(getComResult.data.pstatus,
                   getComResult.data.market_price,
                   getComResult.data.shop_price
               )
               //调用函数生成两个分类信息
               getClassify(getComResult.classify1, 
                           getComResult.classify2,
                           getComResult.classify1.length,
                           getComResult.classify2.length
               )
               //设置价格
               setDifferPrice(getComResult.data.shop_price)
            }
        })
    },
    //点击加入购物车
    recommend2: function(num){
        $.ajax({
            url:"productAction_addToCart.action",//路径
            type:"post",//方法
            async:false,//是否缓存
            dataType:"json",//返回值类型
            data:{
                 "pid" : goodsId,
                 "buyNum" : num,
                 "classify11" : c1Value,
                 "classify22" : c2Value,
                 "buyMoney" : NowPrice
            },
            success: function(getComResult) {
                //成功
                if(getComResult.code == "1" ||getComResult.code == 1 ){
                    alert(getComResult.msg)
                }
                else{
                }   
            },
            error: function error() {
                alert("网络传输有误！请检查网络连接！");
            }
            
        })
    },
    //点击看了又看
    toDetail: function(pid,cid){
        $.ajax({
            url:"productAction_clickProduct.action",//路径
            type:"post",//方法
            async:false,//是否缓存
            dataType:"json",//返回值类型
            data:{
                 "pid" : pid
            },
            success: function(getComResult) {
                //成功
                if(getComResult.code == "1" ||getComResult.code == 1 ){
                    window.location.href = "ItemsDetails.html";
                }
                else{
                }   
            },
            error: function error() {
                alert("网络传输有误！请检查网络连接！");
            }
            
        })
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
    //点击立即购买确认下单
    buyRecommend:function(num,name,address,phone){
        console.log("立即购买接口")
        $.ajax({
            url:"orderAction_addToOredrs.action",//路径
            type:"post",//方法
            async:false,//是否缓存
            dataType:"json",//返回值类型
            data:{
                "pid" : goodsId,
                "pnum" : num,
                "classify1" : c1Value,
                "classify2" : c2Value,
                "subtotal" : NowPrice,
                "state" : -1,
                "pconsignee" : name,
                "address" : address,
                "telephone" : phone 

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
            
        })
    },
}

//加载后执行函数
$(document).ready(function(){

    //调用接口
    Docking.recommend1();

    //显示用户名
    showName();

    //首部搜索框
    Dom.topSearch.aside.onclick = function () {
        if (Dom.topSearch.input.value == "" || Dom.topSearch.input.value == " ")
            alert("请输入你想要搜索的商品!");
        else {
            window.open("topSearch.html?flag=true&A=" + Dom.topSearch.input.value, '_self');
        }
    }

    //点击切换标签页
    $("#tabarea ul li").each(function(index){ 
        $(this).click(function(){
            const liNode = $(this); 
            //将原来显示的内容进行隐藏
            $("div.contentIn").removeClass("contentIn"); 
            //将原来有tabHover属性的标签去掉tabin属性 
            $("#tabarea ul li.tabHover").removeClass("tabHover"); 
            //将当前标签对应的内容区域显示出来 
            $("div.contentFirst").eq(index).addClass("contentIn"); 
            //$("div.contentfirst:eq(" + index + ")").addClass("contentin"); 
            //将当前标签增加tabHover属性 
            liNode.addClass("tabHover"); 
        });
    });

    //点击分类信息
    $("#classify1 li").click(function(){
        var len = $("#classify1").find("li").length; //获取li个数
        classify = $(this).find("span")[0].innerText; //获取分类1的值
        //一级分类不是衣物，价格改变
        if(firstClassify != "2"){
            switch(classify){
                case "大号" :
                case "大瓶" :
                case "大份" :
                case "24瓶" :
                case "大包" :
                    NowBox.innerHTML = parseFloat(NowPrice).toFixed(2)
                    break;
                case "中号":
                case "中瓶" :
                case "中份" :
                case "16瓶" :
                case "中包" :
                    NowBox.innerHTML = highPri.toFixed(2)
                    break;
                case "小号" :
                case "小瓶" :
                case "小份" :
                case "12瓶" :
                case "小包" :
                    NowBox.innerHTML = lowPri.toFixed(2)
                    break;
            }
            
        }
        //遍历li
        $("#classify1 li").each(function(index){
            //按钮回复原来
            this.style.width='84px'
            this.style.height='31px'
            this.style.borderColor='#b5b6b6';
            this.style.borderWidth='0.5px';
        });
        //点击的按钮改变
        this.style.width='81px'
        this.style.height='30px'
        this.style.borderColor='#f50';
        this.style.borderWidth='2px';
        c1Value = this.textContent;
    });
    $("#classify2 li").click(function(){
        // var len = $("#classify2").find("li").length; //获取li个数
        //遍历li
        $("#classify2 li").each(function(index){
            //按钮回复原来
            this.style.width='84px'
            this.style.height='31px'
            this.style.borderColor='#b5b6b6';
            this.style.borderWidth='0.5px';
        });
        //点击的按钮改变
        this.style.width='81px'
        this.style.height='30px'
        this.style.borderColor='#f50';
        this.style.borderWidth='2px';
        c2Value = this.textContent;
    });

    //点击数量选择框
    var txt=document.getElementById('num');
    add=document.getElementById('addBtn');
    sub=document.getElementById('subBtn');
    add.onclick = function () {
        if(txt.value<200){
            txt.value = parseInt(txt.value)+1;
        }
        else txt.value = 200;
    }
    sub.onclick = function () {
        if(txt.value>1){
            txt.value = parseInt(txt.value)-1;
        }
    }

    //点击加入购物车
    $("#addIpt").click(function(){
        //调用购物车接口
        Docking.recommend2(txt.value);
    })

    //点击看了又看跳到详情页
    $("#wPicBoxUl li").click(function(){
        Docking.toDetail($(this).attr('pid'));
    })
    //历史浏览跳到详情页
    $("#sHistoryUl li").click(function(){
        Docking.toDetail($(this).attr('pid'));
    })

    //遮罩层
    //弹出层
    var sheight = document.documentElement.scrolllHeight;
    var swidth = document.documentElement.scrollWidth;
    var imask = document.getElementById("mask");
    var iclose = document.getElementById("close");
    var buyform = document.getElementById("buyForm");

    var dheight = document.documentElement.clientHeight;
    var dwidth = document.documentElement.clientWidth;

    //点击立即购买
    $("#buyIpt").click(function(){
        placeOrder = false;//判断是立即购买还是结算下单
        buySwift(1,buyform);
        //调接口取收货信息
        var Receiving = Docking.getReceivingRecommend();
        document.getElementById("Bconsignee").value = Receiving.autopconsignee
        document.getElementById("Bphone").value = Receiving.autotelephone
        document.getElementById("Baddres").value = Receiving.autoaddress
    })
    //点击取消
    $('#BcloseBtn').click(function(){
        buySwift(2,buyform);
        //解除禁止滚动条
        $(document).unbind("scroll.unable");
    })
    //点击遮罩层
    $('#mask').click(function(){
        buySwift(2,buyform);
        //解除禁止滚动条
        $(document).unbind("scroll.unable");
    })
     //点击关闭弹框
     $('#close').click(function(){
        buySwift(2,buyform);
        //解除禁止滚动条
        $(document).unbind("scroll.unable");
    })
    //确认下单
    $('#BsubmitBtn').click(function(){   
        console.log("判断",placeOrder)
        var name = document.getElementById("Bconsignee").value;//收货人
        var address = document.getElementById("Baddres").value;//地址
        var phone = document.getElementById("Bphone").value;//电话
        if(placeOrder == false){//判断是立即购买还是结算下单,调用不同接口
            Docking.buyRecommend(txt.value,name,address,phone);
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


});


