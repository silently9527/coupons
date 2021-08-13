<template>
    <view class="container">
        <title-nav bg-color="bg-white solid-bottom" :isBack="true">
            <block slot="content">实时疯抢榜</block>
        </title-nav>
        <tab class="nav fixed" :items="items" :textProp="'cname'" @tabselect="tabSelect" :style="'top:'+CustomBar+'px'"></tab>
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
    import ProductList from "@/components/ProductList/HorizontalLayout"
    import Refresh from '@/components/Refresh'
    import Tab from '@/components/Tab'

    export default {
        components: { ProductList, Tab, Refresh },
        data() {
            return {
                CustomBar: this.CustomBar,
                goodsList: [],
                loadingType: 'loading', //加载更多状态
                cid: 1,
                items: []
            };
        },
        async onLoad() {
            this.items = this.$cache.get('category_cached', []);
            if (this.items.length === 0) {
                let list = await this.$api.backend.getCate();
                this.items = list.data;
                this.$cache.put('category_cached', this.items, 60*60*7)
            }
            this.cid = this.items[0].cid
            this.loadData()
        },
        onReachBottom(){
        },
        computed: {},
        methods: {
            //请求数据
            async loadData(type='add'){
                //没有更多直接返回
                if(type === 'add'){
                    this.loadingType = 'loading';
                }else{
                    this.loadingType = 'more'
                }
                let result = await this.$api.backend.getRankGoods(this.cid);
                if(type === 'refresh'){
                    this.goodsList = [];
                }
                let items = result.data;
                this.goodsList = this.goodsList.concat(items);
                this.loadingType  = 'nomore'
                uni.hideLoading()
            },
            onRefresh() {
                this.loadData('refresh');
                this.loadingType = 'loading'
            },
            tabSelect(index, item) {
				uni.showLoading()
                this.cid = item.cid
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
page, .main-content {
    background: $page-color-base;
    height: 100%;
}
</style>
