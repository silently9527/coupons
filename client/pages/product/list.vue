<template>
	<view class="container">
		<title-nav bg-color="bg-white solid-bottom" :isBack="true">
			<block slot="content">商品列表</block>
		</title-nav>
		<sort-navbar :headerTop="CustomBar+'px'" @sort="sortClick"></sort-navbar>
		<view style="height: 40px"></view>
		<view class="main-content">
			<refresh @onRefresh="onRefresh" :pullupLoadingType="loadingType">
				<product-list :list="goodsList"></product-list>
			</refresh>
		</view>
	</view>
</template>

<script>
  import SortNavbar from "@/components/SortNavbar"
  import ProductList from "@/components/ProductList/BoxLayout"
  import Refresh from '@/components/Refresh'

  export default {
    components: {
      ProductList, SortNavbar, Refresh
    },
    data() {
      return {
        CustomBar: this.CustomBar,
        loadingType: 'loading', //加载更多状态
        goodsList: [],
        subcid: undefined,
        page: 1,
        sort: 4,
      };
    },
    onLoad(options) {
      this.subcid = options.subcid;
      this.loadData();
    },
    onReachBottom() {
      this.loadData();
    },
    methods: {
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
        // uni.showLoading()
        let result = await this.$api.backend.getGoodsByCate(this.subcid, this.page++, this.sort);
        if (type === 'refresh') {
          this.goodsList = [];
        }
        this.goodsList = this.goodsList.concat(result.data.list);
        this.loadingType = this.goodsList.length === 0 ? 'nomore' : 'more';
        // uni.hideLoading()
      },
      onRefresh() {
        this.loadData('refresh');
        this.loadingType = 'loading'
      },
      sortClick(sort) {
        this.sort = sort;
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

<style lang="scss" scoped>
	page, .main-content{
		background: $page-color-base;
		height: 100%;
	}
</style>
