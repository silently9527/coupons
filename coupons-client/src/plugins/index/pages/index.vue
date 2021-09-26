<template>
	<view class="container">
		<title-nav bg-color="bg-white solid-bottom text-black">
			<block slot="custom">
				<view class="cu-bar search bg-white" style="width: 100%;">
					<view class="search-form padding-left-xs">
						<text class="icon icon-search text-grey"></text>
						<input class="margin-left-xs" type="text" disabled placeholder="搜索时尚商品"
							@tap="navToNative('/plugins/search/pages/search')" />
					</view>
				</view>
			</block>
		</title-nav>
		<refresh @onRefresh="onRefresh" :pullupLoadingType="loadingType">
			<!-- 头部轮播 -->
			<view class="carousel">
				<swiper indicator-dots circular=true duration="400">
					<swiper-item class="swiper-item" v-for="(item,index) in carouselList" :key="index"
						@click="navTo(item)">
						<view class="image-wrapper">
							<image :src="item.image" class="loaded" mode="aspectFill"></image>
						</view>
					</swiper-item>
				</swiper>
			</view>

			<!-- <view class="ad-1" @click="navToWebView('http://szjx.top/index.php?r=a/t&i=322a9b4d6cc839e9&banner=3')">
				<image src="https://img.alicdn.com/imgextra/i1/2053469401/O1CN01Cs3hlT2JJi1SVQsV6_!!2053469401.gif" mode="scaleToFill"></image>
			</view> -->
			<!-- 分类 -->
			<view class="cate-section" style="padding-top: 30upx;">
				<view class="cate-item" :style="'width:'+200/menus.length+'%'" v-for="menu in menus" :key="menu.id"
					@click="navTo(menu)">
					<image :src="menu.icon"></image>
					<text>{{menu.label}}</text>
				</view>
			</view>

			<!-- 猜你喜欢 -->
			<view class="nine_h3bt h3bt_bg margin-top-sm">
				<view class="title">
					<text class="left"></text>
					<text class="colout">精选推荐</text>
					<text class="right"></text>
				</view>
			</view>
			<product-list :list="goodsList"></product-list>
		</refresh>
	</view>
</template>

<script>
	import api from '../api/api.js'
	import LoadMore from '@/components/LoadMore'
	import ProductList from '@/components/ProductList/BoxLayout'
	import Refresh from '@/components/Refresh'

	export default {
		components: {
			ProductList,
			LoadMore,
			Refresh
		},
		data() {
			return {
				swiperCurrent: 0,
				swiperLength: 0,
				carouselList: [],
				menus: [],
				loadingType: 'more',
				page: 1,
				goodsList: []
			};
		},
		onLoad() {
			this.loadData();
		},
		onReachBottom() {
			this.loadData();
		},
		onShareAppMessage(res) {
			return {
				title: '有人@我，快来免费领取购物优惠券',
				path: '/pages/index/index',
				imageUrl: 'http://file.xiaoyizhiku.net/170/share01.png'
			};
		},
		methods: {
			onRefresh() {
				this.carouselList = []
				this.menus = []
				this.goodsList = []
				this.loadingType = 'more'
				this.swiperCurrent = 0
				this.swiperLength = 0
				this.page = 1
				this.loadData()
			},
			async loadData() {
				let resultdata = await api.listCarousel();
				let carouselList = resultdata.data
				this.swiperLength = carouselList.length;
				this.carouselList = carouselList;

				let menusResult = await api.listMenus();
				this.menus = menusResult.data;

				if (this.loadingType === 'nomore') {
					return;
				}
				this.loadingType = 'loading';
				let result = await api.recommendGoods(this.page++);
				let list = result.data.list || [];
				this.goodsList = this.goodsList.concat(list);
				this.loadingType = list.length === 0 || list.length < 50 ? 'nomore' : 'more';
			},
			//轮播图切换修改背景色
			swiperChange(e) {
				const index = e.detail.current;
				this.swiperCurrent = index;
			},
			toProductList(subcid) {
				uni.navigateTo({
					url: `/pages/product/list?subcid=${subcid}`
				});
			},
			navTo(item) {
				if (item.urlType === 'H5') {
					this.navToWebView(item.title, item.top, item.url);
				} else {
					this.navToNative(item.url)
				}
			},
			navToNative(url) {
				uni.navigateTo({
					url
				});
			},
			navToWebView(title, top, url) {
				if (url.indexOf('taobao.com') > -1 || url.indexOf('tmall.com') > -1) {
					this.openTaobao(url);
					return;
				}
				uni.navigateTo({
					url: '/pages/webview/index?top=' + top + '&title=' + title + '&url=' + encodeURIComponent(url)
				});
			},
			openTaobao(url) {
				// #ifdef H5
				window.open(url)
				// #endif
				// #ifdef APP-PLUS
				plus.runtime.openURL(url.replace('https:', 'taobao:'), res => {
					plus.runtime.openURL(url);
				});
				// #endif
			}
		}
	};
</script>

<style scoped>
	@charset "UTF-8";

	/* 页面左右间距 */
	/* 文字尺寸 */
	/*文字颜色*/
	/* 边框颜色 */
	/* 图片加载中颜色 */
	/* 行为相关颜色 */
	page,
	.container {
		background: #f8f8f8;
	}

	/* 头部 轮播图 */
	.carousel {
		height: 300upx;
		position: relative;
	}

	.carousel swiper {
		height: 100%;
	}

	.carousel .image-wrapper {
		width: 100%;
		height: 100%;
	}

	.carousel .swiper-item {
		display: flex;
		justify-content: center;
		align-content: center;
		height: 400upx;
		overflow: hidden;
	}

	.carousel .swiper-item image {
		width: 100%;
		height: 100%;
	}

	.cate-section {
		display: flex;
		justify-content: space-around;
		align-items: center;
		flex-wrap: wrap;
		padding: 10upx 22upx;
		background: #fff;
	}

	.cate-section .cate-item {
		display: flex;
		flex-direction: column;
		align-items: center;
		font-size: 26upx;
		color: #303133;
	}

	.cate-section image {
		width: 88upx;
		height: 88upx;
		margin-bottom: 14upx;
	}

	.ad-1 {
		width: 100%;
		height: 210upx;
		padding: 10upx 0;
		background: #fff;
	}

	.ad-1 image {
		width: 100%;
		height: 100%;
	}

	.nine_h3bt {
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.nine_h3bt.h3bt_bg {
		height: 40px;
		background: url(https://cmsstatic.ffquan.cn/web/nine_special/images/nine_tab_bg.svg?v=20201295417226) center center no-repeat #fff;
		background-size: auto 100%;
	}

	.nine_h3bt .title {
		display: flex;
		align-items: center;
	}

	.nine_h3bt .title .left {
		height: 10px;
		width: 30px;
		display: inline-block;
		background-size: 100% auto;
		background: url(https://cmsstatic.ffquan.cn/web/nine_special/images/nine_tab_left_tow.svg?v=20201295417226) no-repeat;
		margin-right: 25upx;
	}

	.nine_h3bt .title .colout {
		font-size: 15px;
		color: #c043fc;
		background-image: -webkit-gradient(linear, left 20, right 0, from(#873cfe), to(#fd295e));
		-webkit-background-clip: text;
		-webkit-text-fill-color: transparent;
	}

	.nine_h3bt .title .right {
		height: 10px;
		width: 30px;
		display: inline-block;
		background-size: 100% auto;
		background: url(https://cmsstatic.ffquan.cn/web/nine_special/images/nine_tab_right_tow.svg?v=20201295417226) no-repeat;
		margin-left: 25upx;
	}
</style>
