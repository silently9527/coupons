<template>
	<view class="container">
		<title-nav bg-color="bg-white solid-bottom" :isBack="true">
			<block slot="content">商品详情</block>
		</title-nav>
		<view class="carousel">
			<swiper indicator-dots circular=true duration="400">
				<swiper-item class="swiper-item" v-for="(item,index) in imgList" :key="index">
					<view class="image-wrapper">
						<image :src="item" class="loaded" mode="aspectFill"></image>
					</view>
				</swiper-item>
			</swiper>
		</view>

		<view class="introduce-section">
			<view>
				<!--#ifndef MP-WEIXIN || MP-QQ-->
				<text class="tm"></text>
				<!-- #endif -->
				<text class="title">{{detail.title}}</text>
			</view>
			<view class="price-box">
				<text v-if="detail.couponPrice > 0" class="price-tip margin-right-xs">券后价</text>
				<text class="price-tip">¥</text>
				<text class="price">{{detail.actualPrice}}</text>
				<text class="m-price" v-if="detail.actualPrice !== detail.originalPrice">原价 ¥{{detail.originalPrice}}</text>
			</view>
			<view class="bot-row">
				<text v-if="detail.monthSales > 0">30天销量: {{detail.monthSales}}</text>
				<text v-if="detail.dailySales > 0">当天销量: {{detail.dailySales}}</text>
				<text v-if="detail.couponPrice > 0">已领券量: {{detail.couponReceiveNum}}</text>
			</view>
		</view>

		<!--  领取优惠券 -->
		<view v-if="detail.couponPrice > 0" style="background-color: white;padding-bottom:20upx">
			<view class="coupon">
				<view class="div-left">
					<view>
						<text style="font-size: 20px;font-weight: 500;">{{detail.couponPrice}}</text>
						<text style="font-size: 13px;font-weight: 500;">元优惠券</text>
					</view>
					<view style="font-size: 11px">{{detail.couponStartTime | formatDate}} ~ {{detail.couponEndTime | formatDate}}</view>
				</view>
				<!--#ifdef MP-QQ || MP-WEIXIN-->
				<view class="div-right" v-if="audit==='0'" @click="toggleTpwd">立即领券</view>
				<!-- #endif -->
				<!--#ifdef APP-PLUS || H5-->
				<view class="div-right" @click="toggleTpwd">立即领券</view>
				<!-- #endif -->
			</view>
		</view>

		<view class="good-desc">
			<text>{{detail.desc}}</text>
		</view>

		<view class="store-info">
			<view class="store-title">
				<view>
					<text>店铺: {{detail.shopName}}</text>
				</view>
			</view>
			<view class="store-evaluate">
				<text>宝贝描述：{{detail.descScore}}</text>
				<text>卖家服务：{{detail.serviceScore}}</text>
				<text>物流服务：{{detail.shipScore}}</text>
			</view>
		</view>

		<view class="detail-desc" v-if="similerGoods.length > 0">
			<view class="d-header">
				<text class="text-gray">相似推荐</text>
			</view>
			<scroll-view scroll-x scroll-with-animation>
				<HorizontalScrollLayout :list="similerGoods" @itemtap="navToDetailPage"></HorizontalScrollLayout>
			</scroll-view>
		</view>

		<view class="detail-desc">
			<view class="d-header">
				<text class="text-gray">图文详情</text>
			</view>
			<rich-text :nodes="descImgs"></rich-text>
		</view>

		<!-- 底部操作菜单 -->
		<view class="page-bottom">
			<navigator url="/pages/collocation/index" open-type="switchTab" class="p-b-btn">
				<text class="icon icon-home text-xxl"></text>
				<text>首页</text>
			</navigator>
			<view class="p-b-btn" @tap="toFavorite()">
				<text class="icon text-xxl"
                      :class="{'icon-favor': !favorite, 'icon-favorfill text-red': favorite}">
                </text>
				<text>收藏</text>
			</view>
			<!-- <view class="p-b-btn">
				<text class="icon icon-share text-xxl"></text>
				<text>分享</text>
			</view> -->

			<view class="action-btn-group">
				<!--#ifdef MP-QQ || MP-WEIXIN-->
				<button type="primary" open-type="share" class="action-btn no-border share-btn buy-now-btn">
					<text class="icon icon-share text-lg margin-right-xs"></text>
					<text>分享</text>
				</button>
				<!-- #endif -->

				<!--#ifdef MP-QQ || MP-WEIXIN-->
				<button type="primary" v-if="audit==='0'" class="action-btn no-border" @click="toggleTpwd">
					口令购买
				</button>
				<!-- #endif -->
				<!--#ifdef APP-PLUS || H5-->
				<button type="primary" class="action-btn no-border" @click="toggleTpwd">
					口令购买
				</button>
				<!-- #endif -->
				<!--#ifdef H5-->
				<button type="primary" class="action-btn no-border" @click="openWebLink(couponClickUrl)">领券链接</button>
				<!-- #endif -->

				<!--#ifdef APP-PLUS-->
				<button type="primary" class="action-btn no-border" @click="openTaobao">领券购买</button>
				<!-- #endif -->
			</view>
		</view>


		<!-- 规格-模态层弹窗 -->
		<view
			class="popup spec"
			:class="specClass"
			@touchmove.stop.prevent="stopPrevent"
			@click="toggleTpwd">
			<!-- 遮罩层 -->
			<view class="mask"></view>
			<view class="layer attr-content" @click.stop="stopPrevent">
				<view class="a-t">
					<image :src="detail.mainPic"></image>
					<view class="right margin-top-sm">
						<view>
							<text v-if="detail.couponPrice > 0" class="margin-right-xs" style="font-size: 22upx;color: red;">券后</text>
							<text class="text-price text-red text-xl">{{detail.actualPrice}}</text>
						</view>
						<text v-if="detail.couponPrice > 0" class="stock">已领优惠券：{{detail.couponReceiveNum}}</text>
					</view>
				</view>
				<view class="center-text margin-top-xl">
					<text :selectable="true">{{tpwdTxt}}</text>
				</view>
				<view style="text-align: center;">
					<text class="tip">长按文字区域手动复制</text>
				</view>
				<button class="btn margin-top-xl" :class="alreadyCopy?'bg-green':'bg-red'" @click="copy(tpwdValue)">
					{{alreadyCopy? tips : '一键复制'}}
				</button>
			</view>
		</view>
	</view>
</template>

<script>
  import {mapGetters} from 'vuex';
  import HorizontalScrollLayout from "@/components/ProductList/HorizontalScrollLayout"

  export default {
    components: { HorizontalScrollLayout },
    filters: {
      formatDate(value) {
        if (value) {
          return value.substr(0, 10);
        }
      }
    },
    data() {
      return {
        specClass: 'none',
        favorite: false,
        detail: {},
        tpwdTxt: '',
        tpwdTip: '',
        tpwdValue: '',
        audit: '1',
        couponClickUrl: undefined,
        itemUrl: undefined,
        imgList: [],
        descImgs: undefined,
        alreadyCopy: false,
        tips: '',
        similerGoods: []
      };
    },
    computed: {
      ...mapGetters(['hasLogin', 'shareTicket'])
    },
    async onLoad(options) {
      uni.showLoading();
      let goodsId = options.goodsId;
      this.$api.backend.getPrivilegeLink(goodsId).then(response => {
        this.tpwdValue = response.data.tpwdValue;
        this.tpwdTxt = response.data.tpwdTxt;
        this.tpwdTip = response.data.tpwdTip;
        this.audit = response.data.audit;
        this.couponClickUrl = response.data.couponClickUrl;
        this.itemUrl = response.data.itemUrl;
      })

      let result = await this.$api.backend.getGoodsDetail(null, goodsId)
      if (!result.success) {
        uni.showToast({
          title: '该商品已下架', icon: 'none', duration: 2000
        });
        setTimeout(()=>{
          uni.navigateBack({delta: 1});
        }, 1000)
        return;
      }

      uni.hideLoading();
      this.detail = result.data
	  	goodsId = this.detail.goodsId
      this.imgList.push(this.detail.mainPic)
      this.favorite = this.detail.favorite

      let imgs = this.detail.imgs.split(",");
      if (imgs && imgs.length > 0) {
        imgs.forEach(img => {
          if (img) {
            this.imgList.push(img)
          }
        })
      }
      if(this.detail.id !== -1){
        this.$api.backend.getSimilarGoods(this.detail.id).then(response => {
          this.similerGoods = response.data || [];
        })
			}

      this.buildDescImgs(this.detail.imgs.split(','))
    },
    onShareAppMessage(res) {
      return {
        title: this.detail.title,
        path: `/pages/product/product?goodsId=${this.detail.goodsId}`,
        imageUrl: this.detail.marketingMainPic
      }
    },
    methods: {
      openWebLink(url) {
        window.open(url)
			},
      buildDescImgs(imgs) {
        let children = []
        imgs.forEach(item => {
          children.push({
            name: "img",
            attrs: {
              src: item,
              style: "width:100%;display:block;"
            }
          })
        })
        this.descImgs = [{
          name: "div",
          attrs: {
            style: "width:100%"
          },
          children: children
        }]
      },
      toggleTpwd() {
        if (this.specClass === 'show') {
          this.specClass = 'hide';
          setTimeout(() => {
            this.specClass = 'none';
            this.alreadyCopy = false;
          }, 250);
        } else if (this.specClass === 'none') {
          this.specClass = 'show';
        }
      },
      openTaobao() {
        let couponLink = this.couponClickUrl ? this.couponClickUrl : this.itemUrl;
        // #ifdef H5
				window.open(couponLink)
        // #endif
        // #ifdef APP-PLUS
        plus.runtime.openURL(couponLink.replace('https:', 'taobao:'), (res) => {
          plus.runtime.openURL(couponLink)
        });
        // #endif
      },
      toFavorite() {
        if (!this.hasLogin) {
          uni.showToast({
            title: '您还未登录，请先登录',
            icon: 'none',
            duration: 2000
          });
          return;
        }
        if (this.favorite) {
          this.$api.backend.unFavoriteGoods(this.detail.goodsId)
        } else {
          this.$api.backend.favoriteGoods(this.detail.goodsId)
        }
        this.favorite = !this.favorite
      },
      copy(data) {
        let me = this;
        me.alreadyCopy = true
        // #ifndef H5
        uni.setClipboardData({
          data: data,
          success() {
            me.tips = me.tpwdTip?me.tpwdTip:'复制成功'
          },
          fail(res) {
            me.tips = '复制失败,请您长按复制'
          }
        });
        // #endif
        // #ifdef H5
        this.copyToClipboard(data)
        me.tips = '口令复制成功，请打开手机淘宝'
        // #endif

				this.$utils.getShareInfo()

      },
      copyToClipboard(text) {
        let textArea = document.createElement('textarea');
        textArea.style.position = 'fixed';
        textArea.style.zIndex = -1;
        textArea.style.top = '0';
        textArea.style.left = '0';
        textArea.style.width = '1em';
        textArea.style.height = '1em';
        textArea.style.padding = '0';
        textArea.style.border = 'none';
        textArea.style.outline = 'none';
        textArea.style.boxShadow = 'none';
        textArea.style.background = 'transparent';
        textArea.value = text;
        document.body.appendChild(textArea);
        textArea.select();

        try {
          document.execCommand('copy');
        } catch (err) {
          console.log('该浏览器不支持点击复制到剪贴板');
        }

        document.body.removeChild(textArea);
      },
      stopPrevent() {
      },
      //详情页
      navToDetailPage(item) {
        uni.navigateTo({
          url: `/pages/product/product?goodsId=${item.goodsId}`
        })
      }
    },

  }
</script>

<style lang='scss' scoped>
	page, .container{
		padding-bottom: 160upx;
		background: $page-color-base;

	}
	.carousel {
		height: 722upx;
		position:relative;
		swiper{
			height: 100%;
		}
		.image-wrapper{
			width: 100%;
			height: 100%;
		}
		.swiper-item {
			display: flex;
			justify-content: center;
			align-content: center;
			height: 750upx;
			overflow: hidden;
			image {
				width: 100%;
				height: 100%;
			}
		}

	}

	.introduce-section{
		background: #fff;
		padding: 20upx 30upx;

		.title{
			font-size: 32upx;
			color: $font-color-dark;
			height: 50upx;
			line-height: 50upx;
		}
		.price-box{
			display:flex;
			align-items:baseline;
			height: 64upx;
			padding: 10upx 0;
			font-size: 26upx;
			color:$uni-color-primary;
		}

		.price-tip{
			font-size: $font-sm - 2upx;
		}

		.price{
			font-weight: 500;
			font-size: $font-lg + 2upx;
		}
		.m-price{
			font-size: $font-sm - 4upx;
			margin:0 12upx;
			color: $font-color-light;
			text-decoration: line-through;
		}
		.coupon-tip{
			align-items: center;
			padding: 4upx 10upx;
			background: $uni-color-primary;
			font-size: $font-sm;
			color: #fff;
			border-radius: 6upx;
			line-height: 1;
			transform: translateY(-4upx);
		}
		.bot-row{
			display:flex;
			align-items:center;
			height: 50upx;
			font-size: $font-sm - 2upx;
			color: $font-color-light;
			text{
				flex: 1;
			}
		}
	}

	/*  详情 */
	.detail-desc{
		background: #fff;
		margin-top: 16upx;
		.d-header{
			display: flex;
			justify-content: center;
			align-items: center;
			height: 80upx;
			font-size: $font-base + 2upx;
			color: $font-color-dark;
			position: relative;

			text{
				padding: 0 20upx;
				background: #fff;
				position: relative;
				z-index: 1;
			}
			&:after{
				position: absolute;
				left: 50%;
				top: 50%;
				transform: translateX(-50%);
				width: 300upx;
				height: 0;
				content: '';
				border-bottom: 1px solid #ccc;
			}
		}
	}

	/* 规格选择弹窗 */
	.attr-content{
		padding: 10upx 30upx;
		.a-t{
			display: flex;
			image{
				width: 170upx;
				height: 170upx;
				flex-shrink: 0;
				margin-top: -40upx;
				border-radius: 8upx;;
			}
			.right{
				display: flex;
				flex-direction: column;
				padding-left: 24upx;
				font-size: $font-sm + 2upx;
				color: $font-color-base;
				line-height: 42upx;
				.price{
					font-size: $font-lg;
					color: $uni-color-primary;
					margin-bottom: 10upx;
				}
				.selected-text{
					margin-right: 10upx;
				}
			}
		}
		.attr-list{
			display: flex;
			flex-direction: column;
			font-size: $font-base + 2upx;
			color: $font-color-base;
			padding-top: 30upx;
			padding-left: 10upx;
		}
		.item-list{
			padding: 20upx 0 0;
			display: flex;
			flex-wrap: wrap;
			text{
				display: flex;
				align-items: center;
				justify-content: center;
				background: #eee;
				margin-right: 20upx;
				margin-bottom: 20upx;
				border-radius: 100upx;
				min-width: 60upx;
				height: 60upx;
				padding: 0 20upx;
				font-size: $font-base;
				color: $font-color-dark;
			}
			.selected{
				background: #fbebee;
				color: $uni-color-primary;
			}
		}
	}

	/*  弹出层 */
	.popup {
		position: fixed;
		left: 0;
		top: 0;
		right: 0;
		bottom: 0;
		z-index: 99;

		&.show {
			display: block;
			.mask{
				animation: showPopup 0.2s linear both;
			}
			.layer {
				animation: showLayer 0.2s linear both;
			}
		}
		&.hide {
			.mask{
				animation: hidePopup 0.2s linear both;
			}
			.layer {
				animation: hideLayer 0.2s linear both;
			}
		}
		&.none {
			display: none;
		}
		.mask{
			position: fixed;
			top: 0;
			width: 100%;
			height: 100%;
			z-index: 1;
			background-color: rgba(0, 0, 0, 0.4);
		}
		.layer {
			position: fixed;
			z-index: 99;
			bottom: 0;
			width: 100%;
			min-height: 40vh;
			border-radius: 10upx 10upx 0 0;
			background-color: #fff;
			padding-bottom: 60upx;

			.btn{
				height: 66upx;
				line-height: 66upx;
				border-radius: 4px;
				font-size: $font-base + 2upx;
				color: #fff;
			}
		}
		@keyframes showPopup {
			0% {
				opacity: 0;
			}
			100% {
				opacity: 1;
			}
		}
		@keyframes hidePopup {
			0% {
				opacity: 1;
			}
			100% {
				opacity: 0;
			}
		}
		@keyframes showLayer {
			0% {
				transform: translateY(120%);
			}
			100% {
				transform: translateY(0%);
			}
		}
		@keyframes hideLayer {
			0% {
				transform: translateY(0);
			}
			100% {
				transform: translateY(120%);
			}
		}
	}

	/* 底部操作菜单 */
	.page-bottom{
		position:fixed;
		left: 30upx;
		bottom:30upx;
		z-index: 95;
		display: flex;
		justify-content: center;
		align-items: center;
		width: 690upx;
		height: 100upx;
		background: rgba(255,255,255,.9);
		box-shadow: 0 0 20upx 0 rgba(0,0,0,.5);
		border-radius: 16upx;

		.p-b-btn{
			display:flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;
			font-size: $font-sm;
			color: $font-color-base;
			width: 96upx;
			height: 80upx;
		}
		.action-btn-group{
			display: flex;
			height: 76upx;
			border-radius: 100px;
			overflow: hidden;
			box-shadow: 0 20upx 40upx -16upx #fa436a;
			box-shadow: 1px 2px 5px rgba(219, 63, 96, 0.4);
			background: linear-gradient(to right, #ffac30,#fa436a,#F56C6C);
			margin-left: 60upx;
			position:relative;
			&:after{
				content: '';
				position:absolute;
				top: 50%;
				right: 50%;
				transform: translateY(-50%);
				height: 28upx;
				width: 0;
				border-right: 1px solid rgba(255,255,255,.5);
			}
			.action-btn{
				display:flex;
				align-items: center;
				justify-content: center;
				width: 160upx;
				height: 100%;
				font-size: $font-base ;
				padding: 0;
				border-radius: 0;
				background: transparent;
			}
		}
	}

	.coupon {
		height: 70px;
		background: url(https://cmsstatic.ffquan.cn/wap_new/main/images/icon-bg-001.png?v=2020-02-29) no-repeat;
		background-size: 100% 100%;
		display: flex;
		color: #fff;
		align-content: center;
		margin: 0 20upx 10upx 20upx;

		.div-left {
			flex: 1;
			padding-left: 30upx;
			padding-top: 14px;
		}

		.div-right {
			height: 70px;
			line-height: 70px;
			background-size: auto 100%;
			width: 120px;
			text-align: center;
			color: #FE3738;
			font-size: 13px;
			padding-left: 30upx;

		}
	}

	.good-desc {
		padding: 0 30upx 20upx 30upx;
		font-size: $font-sm - 2upx;
		color: $font-color-light;
		background-color: white;
	}

	.store-info{
		background-color: white;
		margin-top: 16upx;
		padding: 20upx 30upx;

		.store-evaluate {
			margin-top: 10upx;
			text {
				display: inline-block;
				width: 33.3%;
				font-size: 12px;
				color: gray;
			}
		}
	}

	.spec{
		.center-text {
			border: 1px solid #FF3739;
			background: #FFF4F4;
			color: #333;
			border-radius: 4px;
			text-align: center;
			padding: 40upx 10upx;
			text {
				color: #333;
				font-size: 12px;
			}
		}

		.tip {
			font-size: 11px;
			color: gray;
		}

		.stock {
			font-size: 11px;
		}
	}


</style>
