<template>
    <view class="cart-list">
        <block v-for="(item, index) in list" :key="item.id">
            <view class="cart-item solid-bottom" @tap="navToDetailPage(item)">
                <view class="image-wrapper2">
                    <view class="top___nr1M_">
                        <view style="font-size: 16upx">TOP</view>
                        <view style="font-size: 14upx">{{index+1}}</view>
                    </view>
                    <image :src="item.mainPic || item.marketingMainPic"
                           :class="[item.loaded]"
                           mode="aspectFill"
                           lazy-load
                           @load="onImageLoad('list', index)"
                           @error="onImageError('list', index)"
                    ></image>
                </view>
                <view class="item-right">
                    <text class="clamp title">{{item.dtitle}}</text>
                    <view>
                        <view class="flex align-center justify-between">
                            <view>
                                <text class="text-grey text-xs">券后：</text>
                                <text class="price text-price text-red">{{item.actualPrice}}</text>
                            </view>
                            <text class="juan"><text class="text">劵</text>{{item.couponPrice - 0}}元</text>
                        </view>
                        <view class="salse">
                            <view class="text">30天销量:{{item.monthSales}}</view>
                            <view class="text">领券量:{{item.couponReceiveNum}}</view>
                        </view>
                        <view class="margin-top-sm text-xs padding-top-xs padding-bottom-xs padding-left-xs two-hour">
                            近2小时疯抢
                            <text class="text-orange margin-left-xs margin-right-xs amount">{{item.twoHoursSales}}</text>件
                        </view>

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
        }
    },
    data() {
        return {}
    },
    methods: {
        //详情页
        navToDetailPage(item) {
            uni.navigateTo({
                url: `/pages/product/product?goodsId=${item.goodsId}`
            })
        },
        onImageLoad(key, index) {
            // this.$set(this[key][index], 'loaded', 'loaded');
        },
        onImageError(key, index) {
            // this[key][index].image = '/static/errorImage.jpg';
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

            .top___nr1M_ {
                z-index: 50;
                width: 40upx;
                height: 50upx;
                background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAC4AAAA0CAMAAADCKvr4AAABrVBMVEUAAAD/AAD/f3//Zmb/gGb/b2T/emT/aWH/eGH/f2n/Zmb/fGb/hmX/mmv/oWv/c2f/eWf/a2L/eWL/Z2P/gGf/aWX/fmX/cWT/d2T/e2X/amT/b2T/eGT/aWP/e2X/dmT/dmb/dGX/d2X/bWP/e2X/g2b/hmf/nGn/cGX/eGX/a2T/e2X/ZmT/f2X/dGX/d2X/bmT/eGX/Z2T/fWb/aGT/fGX/cmX/eGX/hGb/nGj/bGP/emb/gmb/hWf/nGj/aGT/gGb/cGT/eGX/Z2T/fmb/aWP/emX/ZmP/Z2P/Z2T/aGP/aGT/aWP/aWT/amT/a2T/bGT/bWT/bmT/b2T/cGT/cWT/cmT/c2T/c2X/dGT/dGX/dWT/dWX/dmT/dmX/d2T/d2X/eGX/eWX/emX/e2X/fGX/fWX/fmX/f2X/gGX/gGb/gWX/gWb/gmX/gmb/g2X/g2b/hGX/hGb/hWb/hmb/h2b/iGb/iWb/imb/i2b/jGb/jWb/jWf/jmb/jmf/j2b/j2f/kGb/kGf/kWb/kWf/kmf/k2f/lGf/lWf/lmf/l2f/mGf/mWf/mmf/m2fu0pAeAAAAR3RSTlMAAQQKChcXIiIiIyMmJiYqKjk5Pj5JSU9PXV51dYODioqLi5qara2tsbG/v8DAysrW1tzc4eHo6O7u8fHx8fH29/r6+/v+/tE6UUEAAALTSURBVEjHlZbLil1FFIa/ZUpyAW9tOoegImYmOgg+gW/gyKlv5GsFHCUDCQ5tpQ2dQ7c2KIJx/b+Dql2X3UG0zmhXrb1qrf+y9gnefnj4CmNs2k+RIQth0/e/u/jl93jr86+9i8YZiYRp0W37yfe3PvlmjTa2ERLs0vjDF+XQt+opCiOhrYot1mEd4tu6Nd2ZkQjtC5RtlXHVtqtQzE3WAyWhKLWGKUeQZG8Sg7FwOMky34exW93LpVuFLvOJbW8A9miwnWGEmbLXF8QNAJFqbjy3mqEp94asZdOiXUbmGFR6ajXDFqrPpXOkSk5DoxckktF8K0ZYe8rBtkIKxfbccW9IecdDZJCo3deQychI0TgZ0RlZC9zeLmzRcEPIGVlx7wIstk3WC9c2M8SaG5fWZo8e6sFbO6P9YoUqwSwmSUJzdMNdZCNhBRF10Xdd4lLrYzYfqvxq50rkNwiE7VkpkbJCU17b4HBZNNXulCLJOXejXpPeh6HUFTgJDlkULXpn09UCYM1t06ztQV2GquR2nk+EVcbrYNS0NmhvZrWI1Kb3gbs2Aa4zqfKw6Z1u1T3cXdxa9F7r3SbjMktUZTz0vk3BKrVVVRixXTnjLtaJRLPO5OHiLojVUH02RsZoZ8PdEhnchHtkNrQ5Q4YIcke7yUCRY+5U3FOxzMaGe6IQORmy1G6wbsKtWsxsp0LHdc5bVbLyUHEXiv5NZMhNkdFhHXOmz9f1RB6VDCaKpvk6GwonWjIYU3L+NnfcZdUiZ5OACnrNvHt67Xc+i6nEhnsUkV4+/Tw/Xv0GnJ2cfsw6CK0ilip+fPHrSwFwfX129t7Dk0nGyPHlp9N/hKfHl38xrzcfnD6q5wpLf5bLTtsPF1dX7Nar8/Ozk8MpdbBxGfcfP8L4p/Or49+8ft06vPvBHdl+9Sx48NEXPLu4/IN/W/feP9z38ecjEHfv8B/W7dv87/UPTd+XB6c88FYAAAAASUVORK5CYII=) no-repeat 50%;
                background-size: 60upx 50upx;
                position: absolute;
                top: 0;
                left: 0;
                display: -webkit-flex;
                display: -ms-flexbox;
                display: flex;
                -webkit-flex-direction: column;
                -ms-flex-direction: column;
                flex-direction: column;
                -webkit-align-items: center;
                -ms-flex-align: center;
                align-items: center;
                color: #fff;
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

        .two-hour{
            background: #ffeee6;
            border-radius: 5px;
            color: #893c11;

            .amount {
                font-size: 24upx;
            }
        }

    }


</style>
