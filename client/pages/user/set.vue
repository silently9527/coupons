<template>
	<view class="container">
		<title-nav bg-color="bg-white solid-bottom" :isBack="true">
			<block slot="content">设置</block>
		</title-nav>

		<!--<view class="list-cell m-t">-->
			<!--<text class="cell-tit">消息推送</text>-->
			<!--<switch checked color="#fa436a" @change="switchChange" />-->
		<!--</view>-->
		<!--<view class="list-cell m-t b-b" @click="navTo('清除缓存')" hover-class="cell-hover" :hover-stay-time="50">-->
			<!--<text class="cell-tit">清除缓存</text>-->
			<!--<text class="cell-more icon icon-right"></text>-->
		<!--</view>-->
		<!--<view class="list-cell b-b" @click="navTo('关于美券')" hover-class="cell-hover" :hover-stay-time="50">-->
			<!--<text class="cell-tit">关于美券</text>-->
			<!--<text class="cell-more icon icon-right"></text>-->
		<!--</view>-->
		<view class="list-cell">
			<text class="cell-tit">检查更新</text>
			<text class="cell-tip">当前版本 1.0.3</text>
			<text class="cell-more icon icon-right"></text>
		</view>
		<view class="list-cell log-out-btn" v-if="hasLogin" @click="toLogout">
			<text class="cell-tit">退出登录</text>
		</view>
	</view>
</template>

<script>
  import {
	    mapMutations, mapGetters
	} from 'vuex';
	export default {
    components: {
    },
		data() {
			return {

			};
		},
		computed: {
		  ...mapGetters(['hasLogin'])
		},
		methods:{
			...mapMutations(['logout']),

			navTo(url){
				this.$api.msg(`跳转到${url}`);
			},
			//退出登录
			toLogout(){
				uni.showModal({
				    content: '确定要退出登录么',
				    success: (e)=>{
				    	if(e.confirm){
				    		this.logout();
				    		setTimeout(()=>{
				    			uni.navigateBack();
				    		}, 1000)
				    	}
				    }
				});
			},
			//switch
			switchChange(e){
				let statusTip = e.detail.value ? '打开': '关闭';
				this.$api.msg(`${statusTip}消息推送`);
			},

		}
	}
</script>

<style lang='scss'>
	page, .container{
		background: $page-color-base;
	}
	.list-cell{
		display:flex;
		align-items:baseline;
		padding: 20upx $page-row-spacing;
		line-height:60upx;
		position:relative;
		background: #fff;
		justify-content: center;
		&.log-out-btn{
			margin-top: 40upx;
			.cell-tit{
				color: $uni-color-primary;
				text-align: center;
				margin-right: 0;
			}
		}
		&.cell-hover{
			background:#fafafa;
		}
		&.b-b:after{
			left: 30upx;
		}
		&.m-t{
			margin-top: 16upx;
		}
		.cell-more{
			align-self: baseline;
			font-size:$font-lg;
			color:$font-color-light;
			margin-left:10upx;
		}
		.cell-tit{
			flex: 1;
			font-size: $font-base + 2upx;
			color: $font-color-dark;
			margin-right:10upx;
		}
		.cell-tip{
			font-size: $font-base;
			color: $font-color-light;
		}
		switch{
			transform: translateX(16upx) scale(.84);
		}
	}
</style>
