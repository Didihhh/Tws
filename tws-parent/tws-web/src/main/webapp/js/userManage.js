var receive = {};
//接口
var userManageDocking = {
    //修改用户名
    changenameRecommend: function(name) {
        $.ajax({
            url:"userAction_updateUserName.action",//路径
            type:"post",//方法
            async:false,//是否缓存
            dataType:"json",//返回值类型
            data: {
                "username": name
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
    //修改密码
    changepswRecommend: function(oldpsw,newpsw) {
        $.ajax({
            url:"userAction_updatePassword.action",//路径
            type:"post",//方法
            async:false,//是否缓存
            dataType:"json",//返回值类型
            data: {
                "oldPassword": oldpsw,
                "newPassword" : newpsw
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
                	receive = getComResult.data;
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
    //修改地址
    changeaddressRecommend: function(name,address,phone) {
        $.ajax({
            url:"userAction_updateAddress.action",//路径
            type:"post",//方法
            async:false,//是否缓存
            dataType:"json",//返回值类型
            data: {
                "autopconsignee": name,
                "autoaddress" : address,
                "autotelephone" : phone
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
}

/* 点击按钮，下拉菜单在 显示/隐藏 之间切换 */
$(document).ready(function(){

    //弹出层
    var sheight = document.documentElement.scrolllHeight;
    var swidth = document.documentElement.scrollWidth;
    var imask = document.getElementById("Umask");
    var nameform = document.getElementById("nameForm");
    var pswordform = document.getElementById("pswordForm");
    var addressform = document.getElementById("addressForm");

    var dheight = document.documentElement.clientHeight;
    var dwidth = document.documentElement.clientWidth;

    //显示下拉框
    $('#userName').mouseover(function(){
        $(this).css("cursor","pointer");
        $(this).css("color","red");
        document.getElementById("manageUl").classList.toggle("show");
    })

    //点击修改用户名
    $('#changeName').click(function(){
        Uswift(1,nameform);
        document.getElementById("username").value = document.getElementById("userName").innerHTML;
    })
    //点击修改密码
    $('#changePsword').click(function(){
        Uswift(1,pswordform);
    })
    //点击修改地址
    $('#changeAddress').click(function(){
        Uswift(1,addressform);
        //获取信息
        userManageDocking.getReceivingRecommend();
        document.getElementById("Uconsignee").value = receive.autopconsignee;
        document.getElementById("Uphone").value = receive.autotelephone;
        document.getElementById("Uaddres").value = receive.autoaddress;
    })
    //点击退出登录
    $('#logOut').click(function(){
        if(confirm("真的要退出登录吗?")){
            //跳到登录页
            window.location.href='login.html';
        }
        else{     
        }
    })


    //确认修改用户名
    $("#UnameSubmit").click(function(){
        //调用修改用户名接口
        userManageDocking.changenameRecommend(document.getElementById("username").value);
        Uswift(2,nameform);
        window.parent.location.reload();  //刷新
    })
    //确认修改密码
    $('#UpswSubmit').click(function(){
        let oldP = document.getElementById("oldpsword").value;
        let confirmP = document.getElementById("confirmpsw").value;
        let newP = document.getElementById("newpsword").value;
        //确认密码校对
        if( newP != confirmP){
            alert("密码不一致!请重新输入");
            return false;
        }
        else{
            //调用修改密码接口
            userManageDocking.changepswRecommend(oldP,confirmP);
            Uswift(2,pswordform);
            window.parent.location.reload();  //刷新
        }
        
    })
    //确认修改地址
    $('#UadsSubmit').click(function(){
        let name = document.getElementById("Uconsignee").value;
        let phone = document.getElementById("Uphone").value;
        let address = document.getElementById("Uaddres").value;
        //调用修改收货信息接口
        userManageDocking.changeaddressRecommend(name,address,phone);
        Uswift(2,addressform);
        window.parent.location.reload();  //刷新
    })


    //点击遮罩层
    $('#Umask').click(function(){
        Uswift(2,nameform);
        Uswift(2,pswordform);
        Uswift(2,addressform);
        window.parent.location.reload();  //刷新
    })

    //点击关闭弹框-name
    $('#Uuclose').click(function(){
        Uswift(2,nameform);
        window.parent.location.reload();  //刷新
    })
    //点击关闭弹框-password
    $('#Upclose').click(function(){
        Uswift(2,pswordform);
        window.parent.location.reload();  //刷新
    })
    //点击关闭弹框-address
    $('#Uaclose').click(function(){
        Uswift(2,addressform);
        window.parent.location.reload();  //刷新
    })

    //点击取消-name
    $('#Unameclose').click(function(){
        Uswift(2,nameform);
        window.parent.location.reload();  //刷新
    })
    //点击取消-password
    $('#Upswclose').click(function(){
        Uswift(2,pswordform);
        window.parent.location.reload();  //刷新
    })
    //点击取消-address
    $('#Uadsclose').click(function(){
        Uswift(2,addressform);
        window.parent.location.reload();  //刷新
    })


    function Uswift(now,form){
        var dis;
        if(now == 1){
            dis = "block";
        }
        else if(now == 2){
            dis = "none";
        }
        form.style.display = dis;
        imask.style.display = dis;
        imask.style.height = sheight+"px";
        var jwidth = form.offsetWidth;
        var jheight = form.offsetHeight;
        form.style.left = (dwidth-jwidth)/2+"px";
        form.style.top = (dheight-jheight)/2+"px";
    }
})
// 点击下拉菜单意外区域隐藏
window.onclick = function(event) {
  if (!event.target.matches('#userName')) {
    $('#userName').css("color","black");
    document.getElementById("manageUl").classList.remove("show");
  }
}