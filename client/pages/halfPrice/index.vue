<template>
  <view class="container">
    <title-nav bg-color="bg-white solid-bottom" :isBack="true">
      <block slot="content">每日半价</block>
    </title-nav>
    <tab2 class="nav fixed" :items="items" @tabselect="tabSelect" :style="'top:'+CustomBar+'px'"></tab2>
    <view style="height: 51px"></view>
    <image src="https://img.alicdn.com/imgextra/i4/2053469401/O1CN01bxRFEY2JJhz5KNm0P_!!2053469401.png?v=662917" class="loaded" mode="widthFix"></image>
    <view class="main-content">
      <product-list :list="goodsList"></product-list>
    </view>
  </view>
</template>

<script>
  import {mapState} from 'vuex';
  import ProductList from "@/components/ProductList/HorizontalLayout2"
  import Refresh from '@/components/Refresh'
  import Tab2 from '@/components/Tab2'

  export default {
    components: {ProductList, Tab2, Refresh},
    data() {
      return {
        CustomBar: this.CustomBar,
        goodsList: [],
        loadingType: 'loading', //加载更多状态
        nineCid: -1,
        items: []
      };
    },
    onLoad() {
      this.initialItems()
      console.log(this.items)
      // this.loadData()
    },
    onShareAppMessage(res) {
      return {
        title: '有人@我，9块9还包邮，花小钱过小资生活',
        path: '/pages/nine/nine',
        imageUrl: 'http://file.szjx.top/fashion/nine-nine.jpeg'
      };
    },
    computed: {},
    methods: {
      initialItems() {
        uni.request({
          url: 'http://cmsjapi.ffquan.cn/api/goods/price/half-of-daily-session',
          success: res => {
            this.items = res.data.data;
          }
        });
      },
      //请求数据
      async loadData(type = 'add') {
        //没有更多直接返回
        if (type === 'add') {
          if (this.loadingType === 'nomore') {
            return;
          }
          this.loadingType = 'loading';
        } else {
          this.loadingType = 'more'
        }
        if (type === 'refresh') {
          this.page = 1;
        }

        let result = await this.$api.backend.getNineGoods(this.page++, this.size, this.nineCid);
        if (type === 'refresh') {
          this.goodsList = [];
        }
        let items = result.data.list;
        this.goodsList = this.goodsList.concat(items);
        this.loadingType = items.length === 0 || items.length < this.size ? 'nomore' : 'more';
        uni.hideLoading()
      },
      tabSelect(index, item) {
        // uni.showLoading()
        // this.nineCid = item.value
        // this.loadData('refresh');
        // this.loadingType = 'loading'
        // uni.pageScrollTo({
        //   duration: 300,
        //   scrollTop: 0
        // })
      }
    }
  }
</script>

<style lang='scss' scoped>
  page, .container {
    background: $page-color-base;
    height: 100%;
  }
</style>
