<template>
  <scroll-view scroll-y="true">
    <view class="list">
      <view class="item" v-for="item in list" :key="item.collocationId" @tap="navToCollocation(item)">
        <image mode="widthFix" :lazy-load="true" :src="item.mainImage"></image>
        <view class="margin-top-xs text-center">
          <text class="icon icon-hotfill text-sm margin-right-xs"></text>
          <text class="text-black text-sm">{{item.appreciateCount}}</text>
        </view>
      </view>
    </view>
  </scroll-view>
</template>

<script>
  export default {
    name: "CollocationBox",
    components: {},
    props: {
      list: {
        type: Array,
        default: ()=>[]
      }
    },
    data() {
      return {
      };
    },
    methods: {
      navToCollocation(item){
        uni.navigateTo({ url: `/pages/collocation/detail?collocationId=${item.collocationId}` });
      }
    }
  }
</script>

<style lang="scss" scoped>
.list {
  display: flex;
  flex-wrap: wrap;
  padding: 20upx;
  justify-content: space-around;

  .item {
    width: 32%;
    margin-bottom: 20upx;

    .image-wrapper{
      border-radius: 3px;
      overflow: hidden;
      image{
        opacity: 1;
      }
    }

  }
}
</style>
