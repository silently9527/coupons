var waitTime = 60;

function setTime(self) {
    var html;
    if (waitTime === 0) {
        $('.verifyCode').removeClass('text-999');
        html = '重新获取';
        waitTime = 60;
        $('.verifyCode').html(html);
        $('.verifyCode').attr('onclick', 'getCode()');
        return;
    } else {
        waitTime--;
        $('.verifyCode').addClass('text-999');
        html = "重新获取(" + waitTime + "s)";
    }
    $('.verifyCode').html(html);
    setTimeout(function () {
        setTime()
    }, 1000)
}

//获取验证码
function getCode() {
    var mobile = document.getElementById("mobile");
    if (mobile.value.length !== 11) {
        layer.msg('手机号码错误');
        return false;
    }
    var url = "http://api.szjx.top/mi/send_mobile_code";
    var sign = md5(Base64.encode("mobile=" + mobile.value + "&type=register"));
    var parameter = {
        mobile: mobile.value,
        type: 'register',
        sign: sign
    };

    $.getJSON(url, parameter, function (result) {
        if (result.code === 1) {
            //设置倒计时
            $('.verifyCode').attr('onclick', '');
            setTime(this);
        }
        // if (result.code == 3) {
        //     var androidUrl = result.androidUrl;
        //      var iosUrl = result.androidUrl;
        //     $('.register').addClass('hide');
        //     $('.down').removeClass('hide');
        //     $('#downBtn').attr('data-android-url',androidUrl);
        //     $('#downBtn').attr('data-ios-url',iosUrl);
        // }
        layer.msg(result.msg);
    });
}


function register() {
    var inviteCode = getQueryVariable("inviteCode");
    var mobile = $('#mobile').val();
    var verifyCode = $('#verifyCode').val();
    var password = $('#password').val();
    if (mobile.length !== 11) {
        layer.msg('手机号码错误');
        return false;
    }
    if (verifyCode.length === '') {
        layer.msg('验证码为空');
        return false;
    }
    if (password.length === '') {
        layer.msg('密码为空');
        return false;
    }
    if (password.length < 6) {
        layer.msg('密码长度小于6位');
        return false;
    }

    var url = "/index.php/api/wechat_bind_get_request/register.html";
    var sign = md5(Base64.encode("mobile=" + mobile.value + "&code=" + code + "&verifyCode=" + verifyCode));
    var parameter = {
        mobile: mobile,
        inviteCode: inviteCode,
        verifyCode: verifyCode,
        sign: sign
    };
    $.post(url, parameter, function (result) {
        if (result.code === 1) {
            $('.register').addClass('hide');
            $('.down').removeClass('hide');
        }
        layer.msg(result.msg);
    });
}

$('#downBtn').click(function () {
    var androidUrl = $('#downBtn').attr('data-android-url');
    var iosUrl = $('#downBtn').attr('data-ios-url');
    var u = navigator.userAgent, app = navigator.appVersion;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1;
    var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/);
    //这个是ios操作系统
    if (isIOS && iosUrl) {
        window.location = iosUrl;
    } else {
        //这个是安卓操作系统
        window.location = androidUrl;
    }
})

function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        if (pair[0] == variable) {
            return pair[1];
        }
    }
    return (false);
}