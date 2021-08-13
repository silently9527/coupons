<template>
	<view class="container">
		<title-nav bg-color="bg-white solid-bottom" :isBack="true">
			<block slot="content">搜索结果</block>
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
  import SortNavbar from "@/components/SortNavbar2"
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
        page: 1,
        sort: 'tk_total_sales_des',
        hasCoupon: false,
        keyword: null
      };
    },
    onLoad(options) {
      this.keyword = options.keyword;
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
				let result = await this.$api.backend.search2(this.keyword, this.page++, this.sort, this.hasCoupon);
				if(!result.data || result.data.length === 0) {
					this.loadingType = 'nomore'
					return;
				}
				if (type === 'refresh') {
					this.goodsList = [];
				}
				this.goodsList = this.goodsList.concat(result.data);
        this.loadingType = this.goodsList.length === 0 ? 'nomore' : 'more';
        // uni.hideLoading()
      },
      onRefresh() {
        this.loadData('refresh');
        this.loadingType = 'loading'
      },
      sortClick(eventData) {
        this.sort = eventData.sort;
        this.hasCoupon = eventData.hasCoupon;
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
