<template>
    <view class="cart-list">
        <block v-for="(item, index) in list" :key="item.collectionId">
            <view class="cart-item solid-bottom" @tap="navToDetailPage(item)">
                <view class="image-wrapper2">
                    <!--<text class="quan-msg" v-if="item.invalid === 1">券已失效</text>-->
                    <image :src="item.data.mainPic || item.data.marketingMainPic"
                           :class="[item.loaded]"
                           mode="aspectFill"
                           lazy-load
                           @load="onImageLoad('list', index)"
                           @error="onImageError('list', index)">
                    </image>
                </view>
                <view class="item-right">
                    <text class="clamp title">{{item.data.title}}</text>
                    <view>
                        <view class="flex align-center justify-between">
                            <view>
                                <text class="text-grey text-xs">券后：</text>
                                <text class="price text-price text-red">{{item.data.actualPrice}}</text>
                            </view>
                            <text class="juan" v-if="(item.data.couponPrice-0)>0"><text class="text">劵</text>{{item.data.couponPrice - 0}}元</text>
                        </view>
                        <view class="salse">
                            <view class="text">30天销量:{{item.data.monthSales}}</view>
                            <view class="text" v-if="item.data.couponReceiveNum>0">领券量:{{item.data.couponReceiveNum}}</view>
                        </view>
                        <view class="flex align-center justify-end">
                          <view class="cu-btn sm margin-top-sm">
                            <text class="icon text-lg text-black icon-delete_light" @tap.stop="deleteItem(index, item)"></text>
                          </view>
                        </view>
                       <!-- <button class="cu-btn sm bg-gradual-red margin-top-sm">
                            <text class="text-white">找相似</text>
                            <text class="icon text-sm text-white icon-delete_light" @tap.stop="deleteItem(index, item)">删除</text>
                        </button> -->
                    </view>
                </view>

            </view>
        </block>
    </view>
</template>

<script>
export default {
    props: {
        list: {
            type: Array,
            default: []
        },
    },
    data() {
        return {}
    },
    methods: {
        //详情页
        navToDetailPage(item) {
            // if(item.invalid === 1) {
            //     return
            // }
            uni.navigateTo({
                url: `/pages/product/product?goodsId=${item.data.goodsId}`
            })
        },
        onImageLoad(key, index) {
            this.$set(this[key][index], 'loaded', 'loaded');
        },
        onImageError(key, index) {
            this[key][index].image = '/static/errorImage.jpg';
        },
        deleteItem(index, item) {
            this.$emit('deleteitem', index, item);
        }
    }
}
</script>

<style lang="scss" scoped>
.cart-item{
    display: flex;
    position: relative;
    padding: 20upx 40upx;
    background-color: white;
    .image-wrapper2{
        width: 200upx;
        height: 200upx;
        flex-shrink: 0;
        position:relative;
        image{
            width: 100%;
            height: 100%;
            border-radius: 8upx;
        }
    }
    .item-right{
        display:flex;
        flex-direction: column;
        flex: 1;
        justify-content: space-between;
        overflow: hidden;
        position:relative;
        padding-left: 30upx;
        .title,.price{
            font-size:$font-base + 2upx;
            height: 40upx;
            line-height: 40upx;
        }
        .attr{
            font-size: $font-sm + 2upx;
            color: $font-color-light;
            height: 50upx;
            line-height: 50upx;
        }
        .price{
            height: 50upx;
            line-height:50upx;
        }
    }

    .juan {
        height: 14px;
        background: linear-gradient(90deg,#ff8873 0,#ff4f4f 100%);
        border-radius: 2px;
        padding: 0 5px 0 1px;
        font-size: 9.5px;
        font-weight: 400;
        color: #fff;
        line-height: 14px;
        display: inline-block;
        position: relative;
        z-index: 1;
        zoom: 1;
        top: 0;
        vertical-align: middle;

        .text {
            background: #fff;
            border-radius: 1px 0 0 1px;
            color: #FE3A33;
            margin: auto 5px auto 0;
            min-width: 12px;
            max-width: 12px;
            min-height: 12px;
            max-height: 12px;
            display: inline-block;
            line-height: 12px;
            text-align: center;
        }
    }

    .salse {
        display: flex;
        justify-content: space-between;
        font-size: 14px;
        margin: 0!important;
        .text {
            font-size: 10px;
            font-weight: 400;
            color: #888;
        }
    }

    .quan-msg {
        line-height: 200upx;
        top: 0;
        height: 100%;
        position: absolute;
        zoom: 1;
        z-index: 5;
        left: 0;
        width: 100%;
        background: rgba(0,0,0,.5);
        color: #fff;
        text-align: center;
    }
}
</style>
