<template>
  <view class="list">
    <view class="column" style="margin-right: 4%">

      <view class="item" v-for="item in list1" :key="item.collocationId" @tap="navToCollocation(item)">
        <view class="image-wrapper">
          <image :src="item.mainImage" lazy-load mode="widthFix"></image>
        </view>
        <view class="title margin-top-xs">
          <text class="title-text text-black text-sm">{{item.description}}</text>
        </view>
        <view class="bar margin-top-xs flex justify-between">
          <view><text class="icon icon-attentionfill text-gray text-sm margin-right-xs">{{item.viewCount}}</text></view>
          <view><text class="padding-left icon icon-appreciatefill text-sm" :class="item.appreciate?'text-red':'text-gray'">{{item.appreciateCount}}</text></view>
        </view>
      </view>

    </view>

    <view class="column">
      <view class="item" v-for="item in list2" :key="item.collocationId" @tap="navToCollocation(item)">
        <view class="image-wrapper">
          <image :src="item.mainImage" lazy-load mode="widthFix"></image>
        </view>
        <view class="title margin-top-xs">
          <text class="title-text text-black text-sm">{{item.description}}</text>
        </view>
        <view class="bar margin-top-xs flex justify-between">
          <view><text class="icon icon-attentionfill text-gray text-sm margin-right-xs">{{item.viewCount}}</text></view>
          <view><text class="padding-left icon icon-appreciatefill text-sm" :class="item.appreciate?'text-red':'text-gray'">{{item.appreciateCount}}</text></view>
        </view>
      </view>
    </view>

  </view>
</template>

<script>
  export default {
    name: "CollocationList",
    components: {},
    props: {
      list1: {
        type: Array,
        default: ()=>[]
      },
      list2: {
        type: Array,
        default: ()=>[]
      }
    },
    data() {
      return {};
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
  padding: 0 30upx;

  .column {
    width: 48%;
  }

  .item {
    width: 100%;
    margin-bottom: 40upx;

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
