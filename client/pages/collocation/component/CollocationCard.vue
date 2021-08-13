<template>
  <view class="cu-card case" @tap="itemTap(item)">
    <view class="cu-item shadow">
      <view class="image">
        <image mode="widthFix" :src="item.mainImage"></image>
        <view class="favor-btn" @tap.stop="doAppreciate(item)">
          <text class="icon icon-appreciatefill text-xxl" :class="item.appreciate?'text-red':'text-gray'"></text>
        </view>
      </view>
      <view class="solid-top flex justify-between align-center padding-top padding-left padding-right">
        <view>
          <text class="icon icon-attentionfill text-gray text-sm margin-right-xs"> {{item.viewCount}} 浏览</text>
          <text class="padding-left icon icon-appreciatefill text-gray text-sm"> {{item.appreciateCount}} 赞</text>
        </view>
        <view class="text-sm">
          <text class="text-grey ">{{item.formatCreatedDate}}</text>
        </view>
      </view>
      <view class="cu-list padding-sm">
        <view class="cu-item">
          <view v-for="tag in item.tags" :key="tag.tagId" class='cu-tag line-gray radius padding-sm'>{{tag.name}}</view>
        </view>
        <view class="cu-item padding-top">
          <text class="text-grey">{{item.description}}</text>
        </view>
      </view>
    </view>
  </view>
</template>

<script>

  export default {
    components: {},
    props: {
      item: {
        type: Object,
        default: null
      },
    },
    data() {
      return {};
    },
    methods: {
      itemTap(item) {
        this.$emit('detail', item);
      },
      doAppreciate(item) {
        this.$emit('appreciate', item);
      }
    }
  }
</script>

<style lang="scss" scoped>
  .favor-btn {
    width: 40px;
    height: 40px;
    text-align: center;
    line-height: 40px;
    background-color: $page-color-base;
    border-radius: 20px;
    position: absolute;
    right: 10px;
    top: 10px;
  }
  .cu-tag {
    margin-left: 0!important;
    margin-right: 5px;
    margin-top: 5px;
  }
</style>
