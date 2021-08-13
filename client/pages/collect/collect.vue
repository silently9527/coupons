<template>
    <view class="container">
        <title-nav bg-color="bg-white solid-bottom" :isBack="true">
            <block slot="content">我的收藏</block>
        </title-nav>
        <!-- 空白页 -->
        <view v-if="empty===true" class="empty">
            <view class="collect-img"></view>
            <view class="empty-tips">
                空空如也
                <navigator class="navigator" url="../index/index" open-type="switchTab">
                    随便逛逛>
                </navigator>
            </view>
        </view>
        <view v-else>
            <refresh @onRefresh="onRefresh" :pullupLoadingType="loadingType">
                <collect-list :list="list" @deleteitem="removeCollection"></collect-list>
            </refresh>
        </view>

    </view>
</template>

<script>
    import {mapGetters} from 'vuex';
    import CollectList from "./components/CollectList"
    import Refresh from '@/components/Refresh'

    export default {
        components: { CollectList, Refresh },
        data() {
            return {
                loadingType: 'more',
                empty: false,
                hasLogin: true,
                page: 0,
                size: 20,
                list: []
            };
        },
        watch:{
            //显示空白页
            list(e){
                let empty = e.length === 0;
                if(this.empty !== empty){
                    this.empty = empty;
                }
            }
        },
        onLoad() {
            this.init()
        },
        onShow() {
            if(this.empty) {
                this.init()
            }
        },
        onReachBottom(){
            this.loadData();
        },
        computed: {},
        methods: {
            init(){
                this.page = 0
                this.loadingType = 'loading'
                this.list = []
                this.loadData();
            },
            //请求数据
            async loadData(type='add'){
                if(type === 'add'){
                    if(this.loadingType === 'nomore'){
                        return;
                    }
                    this.loadingType = 'loading';
                }else{
                    this.loadingType = 'more'
                }
                if(type === 'refresh'){
                    this.page = 0;
                }
                this.loadingType = 'loading'
                uni.showLoading()
                let result = await this.$api.backend.favoriteList(this.page++, this.size)
                if(type === 'refresh'){
                    this.list = [];
                }
                let list = result.data.content || []
                this.list = this.list.concat(list)
                this.loadingType = list.length === 0 || list.length < this.size? 'nomore':'more'
                uni.hideLoading()
            },
            removeCollection(index, item){
                this.list.splice(index, 1);
                this.$api.backend.unFavoriteGoods(item.data.goodsId)
            },
            onRefresh() {
                this.loadData('refresh');
                this.loadingType = 'loading'
            }
        }
    }
</script>

<style lang='scss' scoped>
    page, .container{
        background: $page-color-base;
        height: 100%;
        // 加上以下代码下拉刷新的体验会好一点
        /* #ifdef MP-WEIXIN || APP-PLUS */
        position: fixed;
        left: 0;
        width: 100%;
        top: 0;
        /* #endif */
    }

    .user-collect {
        text-align: center;
        height: 100vh;
        background-color: #fff;

        .cu-btn {
            padding:15px 30px;
        }
    }

    .collect-img {
        background: url(https://cmsstatic.ffquan.cn/wap_new/user/images/user_sc.svg) center bottom no-repeat;
        width: 100%;
        height: 35vh;
        margin-bottom: 60upx;
    }

    .empty{
        position:fixed;
        left: 0;
        top:0;
        width: 100%;
        height: 100vh;
        padding-bottom: 300upx;
        display:flex;
        justify-content: center;
        flex-direction: column;
        align-items:center;
        background: #fff;
        image{
            height: 35vh;
            margin-bottom: 80upx;
        }
        .empty-tips{
            display:flex;
            font-size: $font-sm+2upx;
            color: $font-color-disabled;
            .navigator{
                color: $uni-color-primary;
                margin-left: 16upx;
            }
        }
    }
</style>
