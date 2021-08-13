<template>
	<view class="container">
		<title-nav bg-color="bg-white solid-bottom">
			<block slot="content">我</block>
		</title-nav>

		<view class="cu-list menu padding-top-sm">
			<view v-if="hasLogin" class="cu-item user">
				<view class="content flex align-center">
					<view class="cu-avatar round xl inline" :style="'background-image:url('+avatar+')'"></view>
					<text class="text-black margin-left text-xl">{{userInfo.nick}}</text>
				</view>
			</view>
			<view v-else class="cu-item user">
				<!--#ifdef APP-PLUS || H5-->
				<view class="content flex align-center" @tap="toLogin">
					<view class="cu-avatar round xl inline" style="background-image:url(http://file.szjx.top/fashion/missing-face.png)"></view>
					<text class="text-black margin-left text-xl">登录/注册></text>
				</view>
				<!-- #endif -->
				<!--#ifdef MP-WEIXIN || MP-QQ-->
				<view class="content flex align-center">
					<view class="cu-avatar round xl inline margin-right" style="background-image:url(http://file.szjx.top/fashion/missing-face.png)"></view>
					<button class="username login-btn" open-type="getUserInfo" @getuserinfo="minProgramLogin">登录/注册></button>
				</view>
				<!-- #endif -->
			</view>
		</view>

		<view class="cu-list menu">
			<view class="cu-item arrow" @tap="navTo('/pages/collect/collect', true)">
				<view class="content">
					<text class="text-black">收藏夹</text>
				</view>
			</view>
			<!--#ifdef H5 || APP-PLUS-->
			<view class="cu-item arrow" @tap="navTo('/pages/public/guide')">
				<view class="content">
					<text class="text-black">新手教程</text>
				</view>
			</view>
			<!-- #endif -->
			<view class="cu-item" @tap="copy('silently9527')">
				<view class="content">
					<!--#ifdef MP-WEIXIN || H5 || APP-PLUS-->
					<text class="text-black">客户微信</text>
					<!-- #endif -->
					<!--#ifdef MP-QQ-->
					<text class="text-black">客户QQ</text>
					<!-- #endif -->
				</view>
				<view class="action">
					<text class="text-grey">silently9527</text>
				</view>
			</view>
		</view>

		<view class="cu-list menu">
			<view class="cu-item arrow" @tap="navTo('/pages/user/set')">
				<view class="content">
					<text class="text-black">设置</text>
				</view>
			</view>
		</view>

		<!--#ifdef H5-->
		<view class="cu-list menu">
			<view class="cu-item arrow" @tap="downloadApp">
				<view class="content">
					<text class="text-black">App下载</text>
				</view>
			</view>
		</view>
		<!-- #endif -->

	</view>
</template>
<script>
  import { mapGetters, mapMutations } from 'vuex';
  export default {
    components: {
    },
    data(){
      return {
      }
    },
    onLoad(){
    },
    computed: {
      ...mapGetters(['hasLogin','userInfo','avatar'])
    },
    methods: {
      ...mapMutations(['storeUser','login']),
      navTo(url, loginCheck = false){
        if(loginCheck && !this.hasLogin){
          uni.showToast({
            title: '您还未登录，请先登录',
            icon: 'none',
            duration: 2000
          });
          return;
        }
        uni.navigateTo({ url })
      },
      downloadApp(){
        window.open("http://static.szjx.top/download/index.html")
			},
			toLogin(){
        // #ifndef H5
        this.navTo('/pages/public/login')
        // #endif

				// #ifdef H5
        uni.showModal({
          title: '提示',
          content: '请先下载App后再登录',
          confirmText: '下载',
          success:  (res) => {
            if (res.confirm) {
							this.downloadApp();
            } else if (res.cancel) {
            }
          }
        });
        // #endif
			},
      copy(data) {
        // #ifndef H5
        uni.setClipboardData({
          data: data
        });
        // #endif
      },
      minProgramLogin(res){
        let userInfo = res.detail.userInfo;
        uni.getProvider({
          service: 'oauth',
          success: (res) => {
            if (~res.provider.indexOf('qq')) {
              this.login({provider: 'qq', userInfo: userInfo});
            } else if (~res.provider.indexOf('weixin')) {
              this.login({provider: 'weixin', userInfo: userInfo});
            }
          }
        });
      }
    }
  }
</script>
<style lang='scss' scoped>
	page, .container{
		background: $page-color-base;
	}
	.user {
		padding: 10px 12px !important;
	}
	.cu-list+.cu-list {
		margin-top: 8px !important;
	}
	button {
		background-color:transparent;
		border-style:none;
		border: none;
		padding: 0;
		margin: 0;
	}

	button::after {
		border: none;
	}
</style>
