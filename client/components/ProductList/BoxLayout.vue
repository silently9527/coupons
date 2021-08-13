<template>
    <view class="good-list">
        <view v-for="(item, index) in list" :key="index" @click="navToDetailPage(item)" class="good-item">
            <view class="image-wrapper">
                <image :src="item.mainPic" lazy-load mode="aspectFill"></image>
            </view>
            <view class="product-title">
                <!--#ifndef MP-WEIXIN || MP-QQ-->
                <text :class="{'tm':item.shopType===1,'tb':item.shopType===0}"></text>
                <!-- #endif -->
                <text class="title-text clamp">{{item.title}}</text>
            </view>
            <view class="product-price">
                <text style="font-size: 20upx;">券后</text>
                <text style="font-size: 20upx;color:red">￥</text>
                <text class="price-text">{{item.actualPrice}}</text>
            </view>
            <view class="label-box">
                <text class="label" v-if="item.brand === 1">{{item.brandName}}</text>
                <!-- <text class="label" v-if="item.yunfeixian === 1">包运费</text> -->
                <text class="label" v-if="item.activityType === 2">淘抢购</text>
                <text class="label" v-if="item.activityType === 3">聚划算</text>
                <text class="juan" v-if="item.couponPrice>0"><text class="text">劵</text>{{item.couponPrice}}元</text>
            </view>
            <view class="salse">
                <view class="text">30天销量:{{item.monthSales}}</view>
                <view class="text" v-if="item.couponReceiveNum>0">领券量:{{item.couponReceiveNum}}</view>
            </view>
        </view>
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
                uni.navigateTo({
                    url: `/pages/product/product?goodsId=${item.goodsId}`
                })
            }
        }
    }
</script>

<style lang="scss" scoped>
    .good-list{
        display:flex;
        flex-wrap:wrap;
        padding: 0 30upx;
        background: #fff;
        padding-top: 5px;
        .good-item{
            display:flex;
            flex-direction: column;
            width: 48%;
            padding-bottom: 40upx;
            &:nth-child(2n+1){
                margin-right: 4%;
            }
        }
        .image-wrapper{
            width: 100%;
            height: 330upx;
            border-radius: 3px;
            overflow: hidden;
            image{
                width: 100%;
                height: 100%;
                opacity: 1;
            }
        }

        .product-title {
            color: #666;
            display: flex;
            align-items: center;

            .label-top {
                position: relative;
                z-index: 1;
                zoom: 1;
                top: 2px;
            }

            .title-text {
                font-size: $font-sm;
                color: $font-color-dark;
                line-height: 60upx;
            }

        }

        .product-price {
            color: #666;
            .price-text {
                font-size: $font-lg;
                font-weight: 500;
                color: $uni-color-primary;
                line-height: 1;
            }
        }

        .label-box {
            .label {
                height: 14px;
                border-radius: 2px;
                font-size: 9.5px;
                font-weight: 400;
                padding: 0 3px;
                margin-right: 5px;
                display: inline-block;
                line-height: 14px;
                vertical-align: middle;
                border: 1px solid rgba(255, 1, 55, .55);
                color: #FF0137;
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

    }
</style>
