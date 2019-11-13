// $(document).ready(function(){
    
//     $('#userName').hover(  //鼠标滑过导航栏目时
//         function(){
//           $('#manageUl').show();  //显示下拉列表
//           $(this).css({'color':'red','background-color':'orange'}); //设置导航栏目样式，醒目
//          },
//         function(){
//           $('#manageUl').hide();  //鼠标移开后隐藏下拉列表
//           }
//      );
//      $('#manageUl').hover(  //鼠标滑过下拉列表自身也要显示，防止无法点击下拉列表
//         function(){
//          $('#manageUl').show();
//         },
//         function(){
//          $('#manageUl').hide();
//          $('#it').css({'color':'white','background-color':'black'});  //鼠标移开下拉列表后，导航栏目的样式也清除
//          }
//     );
//     $('#manageUl li').hover(  //鼠标滑过下拉列表是改变当前栏目样式
//         function(){
         
//          },
//         function(){
    
//          }
//     );
// })
/* 点击按钮，下拉菜单在 显示/隐藏 之间切换 */
$(document).ready(function(){

    //弹出层
    var sheight = document.documentElement.scrolllHeight;
    var swidth = document.documentElement.scrollWidth;
    var ilogin = document.getElementById("login");
    var ibutton = document.getElementById("button");
    var imask = document.getElementById("mask");
    var iclose = document.getElementById("close");
    var nameform = document.getElementById("nameForm");
    var nameform = document.getElementById("nameForm");
    var nameform = document.getElementById("nameForm");

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
        swift(1,nameform);
    })
    //点击修改密码
    $('#changePsword').click(function(){
        swift(1);
    })
    //点击修改地址
    $('#changeAddress').click(function(){
        swift(1);
    })
    //点击退出登录
    $('#logOut').click(function(){
        
    })
    //点击遮罩层
    $('#mask').click(function(){
        swift(2,nameform);
        window.parent.location.reload();  //刷新
    })
    //点击关闭弹框
    $('#close').click(function(){
        swift(2,nameform);
        window.parent.location.reload();  //刷新
    })


    function swift(now,form){
        console.log(form)
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
    }
})
// 点击下拉菜单意外区域隐藏
window.onclick = function(event) {
  if (!event.target.matches('#userName')) {
    $('#userName').css("color","black");
    document.getElementById("manageUl").classList.remove("show");
  }
}