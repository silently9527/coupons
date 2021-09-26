<template>
    <view>
        <view class="cu-custom" :style="[{height:CustomBar + 'px'}]">
            <view class="cu-bar fixed" :style="style" :class="[bgImage!==''?'none-bg text-white bg-img':'',bgColor]">
                <slot name="left"></slot>
                <view class="action padding-top-xs padding-bottom-xs padding-right-xs" @tap="BackPage" v-if="isBack">
                    <text class="icon icon-back"></text>
                    <slot name="backText"></slot>
                </view>
                <view class="content" :style="[{top:StatusBar + 'px'}]">
                    <slot name="content"></slot>
                </view>
                <slot name="right"></slot>
                <slot name="custom"></slot>
            </view>
        </view>
    </view>
</template>

<script>
    export default {
        data() {
            return {
                StatusBar: this.StatusBar,
                CustomBar: this.CustomBar
            };
        },
        name: 'cu-custom',
        computed: {
            style() {
                var StatusBar = this.StatusBar;
                var CustomBar = this.CustomBar;
                var bgImage = this.bgImage;
                var style = `height:${CustomBar}px;padding-top:${StatusBar}px;`;
                if (this.bgImage) {
                    style = `${style}background-image:url(${bgImage});`;
                }
                return style
            }
        },
        props: {
            bgColor: {
                type: String,
                default: ''
            },
            url: {
                type: String,
                default: ''
            },
            tab: {
                type: Boolean,
                default: false
            },
            isBack: {
                type: [Boolean, String],
                default: false
            },
            bgImage: {
                type: String,
                default: ''
            }
        },
        methods: {
            BackPage() {
                if (this.url) {
                    if (this.tab) {
                        return uni.switchTab({
                            url: this.url
                        });
                    } else {
                        return uni.redirectTo({
                            url: this.url
                        })
                    }
                }
                if (getCurrentPages().length < 2) {
                    let url = '/pages/collocation/index'
                    return uni.switchTab({
                        url
                    })
                }
                uni.navigateBack({
                    delta: 1
                });
            }
        }
    }
</script>

<style>

</style>
