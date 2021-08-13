<template>
	<view class="container">
		<title-nav bg-color="bg-white solid-bottom" :isBack="true">
			<block slot="content">搭配详情</block>
		</title-nav>
		<view class="carousel">
			<swiper indicator-dots circular=true duration="400">
				<swiper-item class="swiper-item" v-for="(image,index) in detail.imagePaths" :key="index">
					<view class="image-wrapper">
						<image :src="image" class="loaded" mode="aspectFill"></image>
					</view>
				</swiper-item>
			</swiper>
		</view>

    <view class="introduce">
      <view class="flex justify-between align-center">
        <view>
          <text class="icon icon-attentionfill text-gray text-sm margin-right-xs"> {{detail.viewCount}} 浏览</text>
          <text class="padding-left icon icon-appreciatefill text-sm" :class="detail.appreciate?'text-red':'text-gray'"> {{detail.appreciateCount}} 赞</text>
        </view>
        <view class="text-sm">
          <text class="text-grey ">{{detail.formatCreatedDate}}</text>
        </view>
      </view>
      <view class="describe margin-top-sm">
        <text class="text-black">{{detail.description}}</text>
      </view>
      <view class="tags margin-top-sm">
        <view v-for="tag in detail.tags" :key="tag.tagId" class='cu-tag line-gray radius padding-sm'>{{tag.name}}</view>
      </view>
    </view>

    <view class="detail-desc" v-if="products.length>0">
      <view class="d-header">
        <text class="text-gray">同款商品传送</text>
      </view>
      <scroll-view scroll-x scroll-with-animation>
        <HorizontalScrollLayout class="padding-30" :list="products" @itemtap="navToDetailPage"/>
      </scroll-view>
    </view>


    <view class="detail-desc">
      <view class="d-header">
        <text class="text-gray">推荐搭配</text>
      </view>
      <CollocationList :list1="list1" :list2="list2"></CollocationList>
      <load-more :status="loadingType"></load-more>
    </view>


  </view>
</template>

<script>
  import {mapGetters} from 'vuex';
  import HorizontalScrollLayout from "@/components/ProductList/HorizontalScrollLayout"
  import CollocationList from "./component/CollocationList"
  import LoadMore from '@/components/LoadMore'

  export default {
    components: { HorizontalScrollLayout, CollocationList, LoadMore },
    filters: {
    },
    data() {
      return {
        loadingType: 'more',
        detail: {},
        products: [],
        page: 0,
        list1: [],
        list2: []
      };
    },
    computed: {
      ...mapGetters(['hasLogin'])
    },
    async onLoad(options) {
      this.loadCollocationDetail(options.collocationId)
      this.recommendCollocations2();
    },
    onReachBottom() {
      this.recommendCollocations2();
    },
    onShareAppMessage(res) {
      return {
        title: this.detail.description.substring(0, this.detail.description.length > 20 ? 20 : this.detail.description.length),
        path: `/pages/collocation/detail?collocationId=${this.detail.collocationId}`,
        imageUrl: this.detail.imagePaths[0]
      }
    },
    methods: {
      async loadCollocationDetail(collocationId){
        let result = await this.$api.backend.collocationSimpleDetail(collocationId);
        this.detail = result.data
        this.$api.backend.collocationProduct(collocationId).then((response)=>{
          this.products = response.data
        });
      },

      async recommendCollocations2(){
        this.$api.backend.recommendCollocations2(this.page++).then((response)=>{
          let recommend = response.data.content
          this.loadingType = recommend.length === 0 || recommend.length < 20 ? 'nomore' : 'more';
          let list2 = recommend.slice(0, recommend.length/2)
          let list1 = recommend.slice(recommend.length/2, recommend.length)
          this.list1 = this.list1.concat(list1);
          this.list2 = this.list2.concat(list2);
        })
      },

      navToDetailPage(item) {
        uni.navigateTo({
          url: `/pages/product/product?goodsId=${item.goodsId}`
        })
      }
    }

  }
</script>

<style lang='scss' scoped>
	page, .container{
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

	.introduce {
		background: #fff;
		padding: 50upx 30upx;

    .cu-tag {
      margin-left: 0!important;
      margin-right: 5px;
      margin-top: 5px;
    }
	}

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

</style>
