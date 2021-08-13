<template>
  <view class="container">
    <title-nav bg-color="bg-white solid-bottom text-black" :isBack="true">
      <block slot="content">登录</block>
    </title-nav>

    <view class="logo margin-top-xl flex justify-center">
      <image mode="aspectFit" src="/static/logo.png"></image>
    </view>

    <view class="login-form margin-top-xl">
      <form>
        <view class="cu-form-group">
          <view class="title">
            <text class="icon icon-mobilefill text-black"></text>
          </view>
          <input placeholder="请输入手机号" v-model="mobile" name="input"/>
        </view>

        <view class="cu-form-group">
          <view class="title">
            <text class="icon icon-messagefill text-black"></text>
          </view>
          <input placeholder="请输入验证码" v-model="code" name="input"/>
          <text class='text-orange' v-if="coding === false" @tap="generateCode()">获取验证码</text>
          <view v-else><text class="text-orange text-sm">{{ authTime }}秒后获取</text></view>
        </view>
      </form>

      <view class="login-btn flex justify-center">
        <text class="icon icon-roundrightfill text-lg text-orange" @tap="doLogin"></text>
      </view>

      <view class="tips flex justify-center margin-top-xl">
        <view>
          <text class="text-gray text-sm">登录即表示同意</text>
          <text class="text-gray text-sm agreement"
                @tap="navToWebView('用户协议', 40, 'http://static.szjx.top/agreement.html')">用户协议</text>
        </view>
      </view>

    </view>

  </view>
</template>

<script>
  import { mapMutations } from 'vuex';
  // #ifdef APP-PLUS
  const hsMobmessage = uni.requireNativePlugin('Hs-MobMessage');
  // #endif

  export default {
    data() {
      return {
        mobile: '',
        code: '',
        coding: false,
        authTime: 60
      }
    },
    onLoad() {
    },
    methods: {
      ...mapMutations(['storeToken', 'storeUser']),
      // 获取验证码
      async generateCode() {
        if(!this.mobile) {
          uni.showToast({ icon: 'none', title: '手机号必填' });
          return false;
        }
        let myReg = /^[1][3,4,5,6,7,8,9][0-9]{9}$/;
        if (!myReg.test(this.mobile)) {
          uni.showToast({ icon: 'none', title: '请输入正确的手机号' });
          return false;
        }
        //设置倒计时秒
        this.authTime = 60;
        this.coding = true;
        let authTimer = setInterval(() => {
          this.authTime--;
          if (this.authTime < 0) {
            this.coding = false;
            clearInterval(authTimer);
          }
        }, 1000);
        //发送验证码
        // #ifdef APP-PLUS
        hsMobmessage.sendCode({
          phoneNumber: this.mobile,
          zone: "86",
          template: '',
          getCodeMethod: 'SMS',
        }, result => {
          if(result === 'error') {
            uni.showToast({ icon: 'none', title: '操作太频繁，请稍后再试' });
          }
        });
        // #endif
      },
      navToWebView(title, top, url) {
        // #ifdef H5
        window.open(url)
        // #endif
        // #ifndef H5
        uni.navigateTo({
          url: '/pages/webview/index?top=' + top + '&title=' + title + '&url=' + encodeURIComponent(url)
        });
        // #endif
      },
      doLogin() {
        let phoneReg = /^1[1-9][0-9]\d{8}$/;
        try {
          if (this.mobile === '') {
            throw '请填写手机号';
          }
          if (!phoneReg.test(this.mobile)) {
            throw '手机号格式有误';
          }
          if (this.code === '') {
            throw '请填写验证码';
          }
        } catch (err) {
          uni.showToast({ title: err, icon: 'none', duration: 2000 });
          return;
        }

        this.$api.backend.htmlLogin(this.mobile, this.code).then(resp => {
          if(resp.responseCode === 7001){
            uni.showToast({ title: "验证码错误或已过期", icon: 'none', duration: 2000 });
            return
          }
          this.storeToken(resp.data.xAuthToken)
          this.$api.backend.getLoginUser().then(resp => {
            this.storeUser(resp.data)
            this.navBack()
          })
        })
      },
      navBack(){
        let url = '/pages/user/user'
        return uni.switchTab({ url })
      }

    }

  }
</script>

<style lang='scss' scoped>
  page{
    background: #fff;
  }

  .container{
    position:relative;
    width: 100vw;
    height: 100vh;
    overflow: hidden;
    background: #fff;
  }

  .logo {
		image{
      height: 100px;
    }
  }

  .agreement {
    text-decoration: underline;
  }

  .login-btn {
    margin-top: 90upx;

    text{
      font-size: 60px;

    }
  }

</style>
