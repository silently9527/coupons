<template>
  <view class="product-list">
    <view class="product">
      <view class="image">
        <image mode="widthFix" src="https://gju2.alicdn.com/tps/i3/2206474208919/O1CN01NlKcu12Fkx8eYx8pU_!!0-item_pic.jpg_310x310.jpg_.webp" class="loaded"></image>
      </view>
      <view class="info">
        <view class="title"></view>
        <view class="tag"></view>
        <view class="desc"></view>
        <view class="toolbar">

        </view>
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
      }
    }
  }
</script>


<style lang="scss" scoped>

</style>
