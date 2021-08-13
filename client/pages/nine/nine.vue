<template>
  <view class="container">
    <title-nav bg-color="bg-white solid-bottom" :isBack="true">
      <block slot="content">9.9包邮</block>
    </title-nav>
    <tab class="nav fixed" :items="items" @tabselect="tabSelect" :style="'top:'+CustomBar+'px'"></tab>
    <view style="height: 45px"></view>
    <view class="main-content">
      <refresh @onRefresh="onRefresh" :pullupLoadingType="loadingType">
        <product-list :list="goodsList"></product-list>
      </refresh>
    </view>
  </view>
</template>

<script>
  import {mapState} from 'vuex';
  import ProductList from "@/components/ProductList/BoxLayout"
  import Refresh from '@/components/Refresh'
  import Tab from '@/components/Tab'

  export default {
    components: {ProductList, Tab, Refresh},
    data() {
      return {
        CustomBar: this.CustomBar,
        goodsList: [],
        loadingType: 'loading', //加载更多状态
        page: 1,
        size: 20,
        nineCid: -1,
        items: [{
          text: '精选',
          value: '-1'
        }, {
          text: '5.9元区',
          value: '1'
        }, {
          text: '9.9元区',
          value: '2'
        }, {
          text: '19.9元区',
          value: '3'
        }]
      };
    },
    onLoad() {
      this.loadData()
    },
    onReachBottom() {
      this.loadData();
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
      onRefresh() {
        this.loadData('refresh');
        this.loadingType = 'loading'
      },
      tabSelect(index, item) {
        uni.showLoading()
        this.nineCid = item.value
        this.loadData('refresh');
        this.loadingType = 'loading'
        uni.pageScrollTo({
          duration: 300,
          scrollTop: 0
        })
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
