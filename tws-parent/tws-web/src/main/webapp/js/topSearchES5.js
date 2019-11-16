'use strict';

var topSearch = void 0,
    nav = void 0,
    choose = void 0,
    rotation = void 0,
    buyToBuy = void 0,
    _recommend = void 0,

    //classify记录现在在哪个分类中，默认为在首页0
    classify = 0,

    //保存筛选信息
    chooseArray = ['', ''];;
//使用单例模式将页面所有dom管理起来
var Dom = {
    user: {
        userName: document.getElementById("userName")
    },
    topSearch: {
        input: document.getElementsByTagName("header")[0].getElementsByTagName("input")[0],
        aside: document.getElementsByTagName("header")[0].getElementsByTagName("aside")[0]
    },
    navigation: {
        navLis: document.getElementsByClassName("navigation")[0].getElementsByTagName("li")
    },
    choose: {
        choose: document.getElementsByClassName("choose")[0],
        choosePrice: document.getElementsByClassName("price")[0].getElementsByTagName("button"),
        choosePlace: document.getElementsByClassName("place")[0].getElementsByTagName("button")
    },
    rotation: {
        carousel: document.getElementById("myCarousel"),
        myCarousel: $('.carousel'),
        rotationImgs: document.getElementsByClassName("item")
    },
    buyToBuy: {
        buyOneBuy: document.getElementsByClassName("buy-one-buy")[0],
        buyLis: document.getElementsByClassName("buy-one-buy")[0].getElementsByTagName("li")
    },
    recommend: {
        recommendH: document.getElementsByClassName("recommend")[0].getElementsByTagName("h3")[0],
        recommendUl: document.getElementsByClassName("recommend")[0].getElementsByTagName("ul")[0],
        recLis: document.getElementsByClassName("recommend")[0].getElementsByTagName("img")
    }

    //与后端对接的接口对象
}; var Docking = {
    //为你推荐中的商品
    recommend: function recommend() {
        console.log("接口recommend");
        $.ajax({
            url: 'productAction_recommendProduct.action', //路径
            type: 'post', //方法
            async:false,
            cache: false, //是否缓存
            dataType: 'json', //返回值的类型
            success: function(getComResult) {
                if (getComResult.code == "1" || getComResult.code == 1) {
            		 for (var i = 0; i < getComResult.data.length; i++) {
                         Dynamic.createCommodity(getComResult.data[i]);
                     }
                     _recommend.initital();
                	Dom.user.userName.innerText = getComResult.username;
                } else {
                    alert(getComResult.msg);
                }
            },
            error: function error() {
                alert("网络传输有误！请检查网络连接！");
            }
        });
    },
    navigation: function navigation(classify, pstatus) {
        console.log("接口navigation：" + classify + ":" + pstatus);
        $.ajax({
            url: 'productAction_classificationProduct.action', //路径
            type: 'post', //方法
            cache: false, //是否缓存
            data: {
                "categoryId": classify,
                "isDiscount": pstatus
            },
            dataType: 'json', //返回值的类型
            success: function(getComResult) {
                if (getComResult.code == "1" || getComResult.code == 1) {
            		for (var i = 0; i < getComResult.data.length; i++) {
                        Dynamic.createCommodity(getComResult.data[i]);
                    }
                    _recommend.initital();
                } else {
                    alert(getComResult.msg);
                }
            },
            error: function error() {
                alert("网络传输有误！请检查网络连接！");
            }
        });
    },
    search: function search(searchName) {
        console.log("接口search：" + searchName);
        $.ajax({
            url: 'productAction_searchProductInHome.action', //路径
            type: 'post', //方法
            cache: false, //是否缓存
            data: {
                "pname": searchName
            },
            dataType: 'json', //返回值的类型
            success: function(getComResult) {
                if (getComResult.code == "1" || getComResult.code == 1) {
                    for (var i = 0; i < getComResult.data.length; i++) {
                        Dynamic.createCommodity(getComResult.data[i]);
                    }
                    _recommend.initital();
                }else {
                    alert(getComResult.msg);
                }
            },
            error: function error() {
                alert("网络传输有误！请检查网络连接！");
            }
        });
    },

    clickRecommend: function clickRecommend(cid, pid) {
        console.log("接口clickRecommend：" + cid + ":" + pid);
        $.ajax({
            url: 'productAction_clickProduct.action', //路径
            type: 'post', //方法
            async:false,
            cache: false, //是否缓存
            data: {
                "cid": cid,
                'pid': pid
            },
            dataType: 'json', //返回值的类型
            success: function(getGoDetailsResult) {
                if (getGoDetailsResult.code == "1" || getComResult.code == 1) {
                    //跳转页面
                	window.location.href = "../html/ItemsDetails.html";
                } else {
                    alert(getGoDetailsResult.msg);
                }
            },
            error: function error() {
                alert("网络传输有误！请检查网络连接！");
            }
        });
    },
    clickClassify: function clickClassify(classify, price, place) {
        var p = void 0;
        if (price == "100元以下") p = -100; else if (price == "100元以上") p = 100; else p = "";
        console.log("接口clickClassify：" + classify + ":" + p + ":" + place);
        $.ajax({
            url: 'productAction_screenProductInCategory.action', //路径
            type: 'post', //方法
            cache: false, //是否缓存
            data: {
                "categoryId": classify,
                'searchPrice': p,
                'searchAddress': place
            },
            dataType: 'json', //返回值的类型
            success: function(getComResult) {
                if (getComResult.code == "1" || getComResult.code == 1) {

                    for (var i = 0; i < getComResult.data.length; i++) {
                        Dynamic.createCommodity(getComResult.data[i]);
                    }
                    _recommend.initital();
                } else {
                    alert(getComResult.msg);
                }
            },
            error: function error() {
                alert("网络传输有误！请检查网络连接！");
            }
        });
    },
    clickSearchClassify: function clickSearchClassify(searchClassify, price, place) {
        var p = void 0;
        if (price == "100元以下") p = -100; else if (price == "100元以上") p = 100; else p = "";
        console.log("接口clickSearchClassify：" + searchClassify + ":" + p + ":" + place);
        $.ajax({
            url: 'productAction_screenProductInHome.action', //路径
            type: 'post', //方法
            cache: false, //是否缓存
            data: {
                "pname": searchClassify,
                'searchPrice': p,
                'searchAddress': place
            },
            dataType: 'json', //返回值的类型
            success: function(getComResult) {
                if (getComResult.code == "1" || getComResult.code == 1) {
                    for (var i = 0; i < getComResult.data.length; i++) {
                        Dynamic.createCommodity(getComResult.data[i]);
                    }
                    _recommend.initital();	
                } else {
                    alert(getComResult.msg);
                }
            },
            error: function error() {
                alert("网络传输有误！请检查网络连接！");
            }
        });
    }

    //动态生成商品和隐藏显示功能对象
}; var Dynamic = {
    createCommodity: function createCommodity(commodity) {
        var img = document.createElement("img");
        img.src = commodity.psrc1;
        img.alt = commodity.cid + "/" + commodity.pid;
        var h3 = document.createElement("h3");
        h3.innerText = commodity.pname;
        // let p = document.createElement("p");
        // p.innerText = commodity.details;
        var b = document.createElement("b");
        b.innerText = "￥" + commodity.shop_price;
        var div = document.createElement("div");
        div.className = "commodity";
        div.appendChild(img);
        div.appendChild(h3);
        // div.appendChild(p);
        div.appendChild(b);
        if (commodity.pstatus == 1 || commodity.pstatus == '1') {
            var s = document.createElement("s");
            s.innerText = "￥" + commodity.market_price;
            div.appendChild(s);
        }
        var li = document.createElement("li");
        li.appendChild(div);
        Dom.recommend.recommendUl.appendChild(li);
    },
    //隐藏轮播图、淘一淘、为你推荐中的h3，显示筛选
    hideMany: function hideMany() {
        Dom.rotation.carousel.className = "dn";
        Dom.buyToBuy.buyOneBuy.className = "dn";
        Dom.recommend.recommendH.className = "dn";
        Dom.choose.choose.className = "choose";
        //将筛选的样式清空
        chooseArray[0] = '';
        chooseArray[1] = '';

        for (var i = 0; i < Dom.choose.choosePrice.length; i++) {
            Dom.choose.choosePrice[i].style.backgroundColor = "white";
        }
        for (var index = 0; index < Dom.choose.choosePlace.length; index++) {
            Dom.choose.choosePlace[index].style.backgroundColor = "white";
        }
    },
    //显示轮播图、淘一淘、为你推荐中的h3，隐藏筛选
    showMany: function showMany() {
        Dom.rotation.carousel.className = "carousel slide rotation";
        Dom.buyToBuy.buyOneBuy.className = "buy-one-buy";
        Dom.recommend.recommendH.className = "";
        Dom.choose.choose.className = "dn";
    },
    //清空为你推荐中的所有商品
    clearAllCom: function clearAllCom() {
        var length = Dom.recommend.recommendUl.childNodes.length;
        for (var i = length - 1; i >= 0; i--) {
            Dom.recommend.recommendUl.removeChild(Dom.recommend.recommendUl.childNodes[i]);
        }
    }

    //首页搜索栏的构造函数
}; var TopSearch = function TopSearch() { };
//首页搜索栏的原型
TopSearch.prototype = {
    constructor: TopSearch,
    clickSearchEvent: function clickSearchEvent() {
        Dom.topSearch.aside.onclick = function () {
            classify = 0;
            if (Dom.topSearch.input.value == "" || Dom.topSearch.input.value == " ") alert("请输入你想要搜索的商品!"); else {
                //点击搜索按钮调用接口
                //Array.from将NodeList集合转换成对象，第二个参数对数组的每个元素进行操作并返回
                for (var i = 0; i < Dom.navigation.navLis.length; i++) {
                    Dom.navigation.navLis[i].className = "";
                }
                Dynamic.hideMany();
                Dynamic.clearAllCom();
                //调用搜索接口
                Docking.search(Dom.topSearch.input.value);
            }
        };
    }

    //首页分类栏的构造函数
}; var Navigation = function Navigation() { };
//首页分类栏的原型
Navigation.prototype = {
    constructor: Navigation,
    clickNavEvent: function clickNavEvent() {
        var _loop = function _loop(index) {
            Dom.navigation.navLis[index].onclick = function () {
                classify = index;
                //清空搜索
                Dom.topSearch.input.value = "";
                //点击当前li实现样式，其他li消除样式
                // for (let [i, v] of Array.from(Dom.navigation.navLis).entries()) {
                for (var i = 0; i < Dom.navigation.navLis.length; i++) {
                    if (i == index) Dom.navigation.navLis[i].className = "active"; else Dom.navigation.navLis[i].className = "";
                }
                if (Dom.navigation.navLis[index].getElementsByTagName("a")[0].innerText == "首页") {
                    Dynamic.showMany();
                    Dynamic.clearAllCom();
                    Docking.recommend();
                } else {
                    Dynamic.hideMany();
                    Dynamic.clearAllCom();
                    //传分类和混合状态码（-1）给后端
                    Docking.navigation(index, -1);
                }
            };
        };

        //keys()是对键名index的遍历,values()是对键值value的遍历,entries()是对键值对的遍历。都返回一个遍历器Iterator对象
        for (var index = 0; index < Dom.navigation.navLis.length; index++) {
            _loop(index);
        }
    }
};

var Choose = function Choose() { };
Choose.prototype = {
    constructor: Choose,
    clickChoose: function clickChoose() {
        var lastPrice = 10,
            lastPlace = 10;
        //点击筛选价格

        var _loop2 = function _loop2(index) {
            Dom.choose.choosePrice[index].onclick = function () {
                chooseArray[0] = '';

                for (var i = 0; i < Dom.choose.choosePrice.length; i++) {
                    if (i == lastPrice && Dom.choose.choosePrice[i].style.backgroundColor != "white") {
                        Dom.choose.choosePrice[i].style.backgroundColor = "white";
                    } else if (i == index) {
                        Dom.choose.choosePrice[i].style.backgroundColor = "#F0E688";
                        chooseArray[0] = Dom.choose.choosePrice[i].innerText;
                    } else Dom.choose.choosePrice[i].style.backgroundColor = "white";
                }
                lastPrice = index;
                //调用筛选接口
                Dynamic.clearAllCom();
                if (classify == 0) Docking.clickSearchClassify(Dom.topSearch.input.value, chooseArray[0], chooseArray[1]); else Docking.clickClassify(classify, chooseArray[0], chooseArray[1]);
            };
        };

        for (var index = 0; index < Dom.choose.choosePrice.length; index++) {
            _loop2(index);
        }
        //点击筛选地区

        var _loop3 = function _loop3(index) {
            Dom.choose.choosePlace[index].onclick = function () {
                chooseArray[1] = '';
                for (var i = 0; i < Dom.choose.choosePlace.length; i++) {
                    if (i == lastPlace && Dom.choose.choosePlace[i].style.backgroundColor != "white") Dom.choose.choosePlace[i].style.backgroundColor = "white"; else if (i == index) {
                        Dom.choose.choosePlace[i].style.backgroundColor = "#F0E688";
                        chooseArray[1] = Dom.choose.choosePlace[i].innerText;
                    } else Dom.choose.choosePlace[i].style.backgroundColor = "white";
                }
                lastPlace = index;
                Dynamic.clearAllCom();
                if (classify == 0) Docking.clickSearchClassify(Dom.topSearch.input.value, chooseArray[0], chooseArray[1]); else Docking.clickClassify(classify, chooseArray[0], chooseArray[1]);
            };
        };

        for (var index = 0; index < Dom.choose.choosePlace.length; index++) {
            _loop3(index);
        }
    }

    //轮播图的构造函数
}; var Rotation = function Rotation() { };
//轮播图的原型
Rotation.prototype = {
    constructor: Rotation,
    clickRotEvent: function clickRotEvent() {
        var _loop4 = function _loop4(index) {
            Dom.rotation.rotationImgs[index].onclick = function () {
                classify = index + 1;
                //清空搜索
                Dom.topSearch.input.value = "";
                Dynamic.hideMany();
                Dynamic.clearAllCom();
                for (var i = 0; i < Dom.navigation.navLis.length; i++) {
                    if (i == index + 1) Dom.navigation.navLis[i].className = "active"; else Dom.navigation.navLis[i].className = "";
                }
                Docking.navigation(index + 1, 1);
            };
        };

        for (var index = 0; index < Dom.rotation.rotationImgs.length; index++) {
            _loop4(index);
        }
    }

    //淘一淘的构造函数
}; var BuyToBuy = function BuyToBuy() { };
//淘一淘的原型
BuyToBuy.prototype = {
    constructor: BuyToBuy,
    clickBuy: function clickBuy() {
        var _loop5 = function _loop5(index) {
            Dom.buyToBuy.buyLis[index].onclick = function () {
                //若clickBuy函数也为()=>,则this指向window对象，本来箭头函数内this为定义时所在的对象（最外层的对象）
                //但是因为对象不构成单独的作用域，导致这里的this指向了全局变量
                //所以clickBuy函数写成function()，使得clickBuy成为一个单独的作用域,使得this指向运行时所在的作用域
                classify = index;
                //清空搜索
                Dom.topSearch.input.value = "";
                if (index == 0) {
                    Dynamic.showMany();
                    Dynamic.clearAllCom();
                    Docking.recommend();
                    document.body.scrollTop = 0;
                    document.getElementsByTagName("html")[0].scrollTop = 0;
                } else {
                    for (var i = 0; i < Dom.navigation.navLis.length; i++) {
                        if (i == index) Dom.navigation.navLis[i].className = "active"; else Dom.navigation.navLis[i].className = "";
                    }
                    Dynamic.hideMany();
                    Dynamic.clearAllCom();
                    //传分类和不打折状态码给后端
                    Docking.navigation(index, 0);
                }
            };
        };

        for (var index = 0; index < Dom.buyToBuy.buyLis.length; index++) {
            _loop5(index);
        }
    }

    //为你推荐的构造函数
}; var Recommend = function Recommend() { };
//为你推荐的原型
Recommend.prototype = {
    constructor: Recommend,
    initital: function initital() {
        this.clickRecommend();
    },
    clickRecommend: function clickRecommend() {
        var _loop6 = function _loop6(index) {
            Dom.recommend.recLis[index].onclick = function () {
                var comNumbers = Dom.recommend.recLis[index].alt.split("/");
                Docking.clickRecommend(comNumbers[0], comNumbers[1]);
                //调用详情接口
            };
        };

        for (var index = 0; index < Dom.recommend.recLis.length; index++) {
            _loop6(index);
        }
    }
};

window.onload = function () {
	var url = window.location.href;
	var index = url.indexOf("flag");
	if (index != -1) {//由A页面跳转而来   
	    classify = 0;
	    var a = url.split('&');
        var value = a[1].split('=');
        Dom.topSearch.input.value = decodeURI(value[1]);
        for (let i = 0; i < Dom.navigation.navLis.length; i++) {
            Dom.navigation.navLis[i].className = "";
        }
        Dynamic.hideMany();
        Dynamic.clearAllCom();
        //调用搜索接口
        Docking.search(Dom.topSearch.input.value);
	}
    //轮播图自动滚动
    Dom.rotation.myCarousel.carousel({ interval: 1800 });
    topSearch = new TopSearch();
    topSearch.clickSearchEvent();
    nav = new Navigation();
    nav.clickNavEvent();
    choose = new Choose();
    choose.clickChoose();
    rotation = new Rotation();
    rotation.clickRotEvent();
    buyToBuy = new BuyToBuy();
    buyToBuy.clickBuy();
    _recommend = new Recommend();
    //页面一加载就调用为你推荐接口
    Docking.recommend();
    _recommend.initital();
};

