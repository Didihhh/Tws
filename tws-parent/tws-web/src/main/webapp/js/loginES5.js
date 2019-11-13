"use strict";

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var login = void 0,
    identifyCode = void 0;

var Dom = {
    id: document.getElementById('id'),
    password: document.getElementById("password"),
    loginBtn: document.getElementsByTagName("button")[0],
    changeCode: document.getElementById("changeCode"),
    code: document.getElementById("code")
};

var Login = function Login() { };

Login.prototype = {
    constructor: Login,
    initital: function initital() {
        Dom.loginBtn.onclick = function () {
            if (Dom.id.value == "") alert("请输入您的账号!"); else if (Dom.password.value == "") alert("请输入您的密码！"); else if (Dom.code.value.toLowerCase() != identifyCode.code.toLowerCase()) alert("您输入的验证码有误！"); else {
                $.ajax({
                    url: 'userAction_login.action',
                    type: 'post',
                    cache: false,
                    data: {
                        "uid": Dom.id.value,
                        "password": Dom.password.value
                    },
                    dataType: 'json',
                    success: function success(LoginResult) {
                        if (LoginResult.code == 1 || LoginResult.code == "1") {
                            alert(LoginResult.msg);
                            window.location.href = "topSearch.html";
                        } else if(LoginResult.code == 2 || LoginResult.code == "2"){
                            alert(LoginResult.msg);
                            window.location.href = "backOrderManage.html";
                        }else {
                            alert(LoginResult.msg);
                        }
                    },
                    error: function error() {
                        alert("网络传输有误！请检查网络连接！");
                    }
                });
            }
        };
    }

    //验证码
};
var IdentifyCode = function () {
    function IdentifyCode(canvasId, width, height) {
        _classCallCheck(this, IdentifyCode);

        this.width = width;
        this.height = height;
        this.canvas = document.querySelector(canvasId);
        this.ctx = this.canvas.getContext("2d");
        this.code = "";
        this.codeRange = [];

        this.generateCodeRange();
        this.freshCode();
    }

    //


    _createClass(IdentifyCode, [{
        key: "initCanvas",
        value: function initCanvas() {
            this.canvas.width = this.width;
            this.canvas.height = this.height;
        }

        // 生成随机色

    }, {
        key: "randomColor",
        value: function randomColor() {
            var colorStr = "#";
            for (var i = 0; i < 6; i++) {
                colorStr += Math.floor(Math.random() * 16).toString(16);
            }
            return colorStr;
        }

        // 生成二维码字母范围

    }, {
        key: "generateCodeRange",
        value: function generateCodeRange() {
            var codeRange = [];
            for (var i = "A".charCodeAt(0); i <= "Z".charCodeAt(0); i++) {
                codeRange.push(String.fromCharCode(i));
            }
            for (var _i = "a".charCodeAt(0); _i <= "z".charCodeAt(0); _i++) {
                codeRange.push(String.fromCharCode(_i));
            }
            for (var _i2 = "0".charCodeAt(0); _i2 <= "9".charCodeAt(0); _i2++) {
                codeRange.push(String.fromCharCode(_i2));
            }
            this.codeRange = codeRange;
            // return codeRange;
        }

        // 生成四位随机数

    }, {
        key: "randomCode",
        value: function randomCode() {
            this.code = "";
            var len = this.codeRange.length;
            for (var i = 0; i < 4; i++) {
                this.code += this.codeRange[Math.floor(Math.random() * len)];
            }
        }

        // 画背景色

    }, {
        key: "drawBackground",
        value: function drawBackground() {
            var bgColor = "#ffffff";
            this.ctx.rect(0, 0, this.width, this.height);
            this.ctx.fillStyle = bgColor;
            this.ctx.fill();
        }

        // 画干扰线

    }, {
        key: "drawDisturbLines",
        value: function drawDisturbLines() {
            for (var i = 0; i < 4; i++) {
                this.drawOneLine();
            }
        }
    }, {
        key: "drawOneLine",
        value: function drawOneLine() {
            var startX = Math.floor(Math.random() * this.width);
            var startY = Math.floor(Math.random() * this.height);
            var endX = Math.floor(Math.random() * this.width);
            var endY = Math.floor(Math.random() * this.height);
            this.ctx.strokeStyle = this.randomColor();
            this.ctx.lineWidth = Math.ceil(Math.random() * 2);
            this.ctx.beginPath();
            this.ctx.moveTo(startX, startY);
            this.ctx.lineTo(endX, endY);
            this.ctx.closePath();
            this.ctx.stroke();
        }

        // 画干扰点

    }, {
        key: "drawDisturbDots",
        value: function drawDisturbDots() {
            for (var i = 0, count = this.width * this.height / 100; i < count; i++) {
                this.drawOneDot();
            }
        }
    }, {
        key: "drawOneDot",
        value: function drawOneDot() {
            var ox = Math.floor(Math.random() * this.width);
            var oy = Math.floor(Math.random() * this.height);
            this.ctx.fillStyle = this.randomColor();
            this.ctx.beginPath();
            this.ctx.arc(ox, oy, 1, 0, 2 * Math.PI);
            this.ctx.closePath();
            this.ctx.fill();
        }

        // 画文字(数字或字母)

    }, {
        key: "drawLetters",
        value: function drawLetters() {
            for (var i = 0, len = this.code.length; i < len; i++) {
                var offsetX = this.width * 0.15; // 中间的70%画字母，两边各15%
                var offsetY = this.height * 0.15;
                var lineHeight = this.height * 0.7;
                var x = i * this.width * 0.7 / this.code.length + offsetX;
                var y = this.height / 2;
                var letter = this.code[i];
                var fontSize = Math.min(parseInt(this.height), parseInt(this.width * 0.7));
                //console.log(fontSize)
                this.drawOneLetter(letter, x, y, fontSize);
            }
        }
    }, {
        key: "drawOneLetter",
        value: function drawOneLetter(letter, x, y, fontSize) {
            this.ctx.font = fontSize + "px Times new roman";
            this.ctx.textBaseline = "middle";
            this.ctx.fillStyle = this.randomColor();
            this.ctx.fillText(letter, x, y);
        }

        // 更换一个验证码

    }, {
        key: "freshCode",
        value: function freshCode() {
            this.clear();
            this.randomCode();
            //console.log(this.code);
            this.initCanvas();
            this.drawBackground();
            this.drawDisturbLines();
            this.drawDisturbDots();
            this.drawLetters();
        }

        // 清除画布

    }, {
        key: "clear",
        value: function clear() {
            this.ctx.clearRect(0, 0, this.width, this.height);
        }
    }]);

    return IdentifyCode;
}();

window.onload = function () {
    login = new Login();
    login.initital();
    identifyCode = new IdentifyCode("#identify", 110, 30);
    Dom.changeCode.onclick = function () {
        identifyCode.freshCode();
    };
};
