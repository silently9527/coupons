<template>
  <scroll-view scroll-x class="bg-white nav solid-bottom" scroll-with-animation :scroll-left="scrollLeft">
    <view :class="index===tabCur?'text-red cur':''" class="cu-item"
          v-for="(item,index) in items"
          :key="index"
          @tap="tabSelect(index, item)">
      <view class="text-center text-xl">{{item[textProp]+':00'}}</view>
      <view class="text-center">{{item[textStatus] | formatStatus}}</view>
    </view>
  </scroll-view>
</template>

<script>
  export default {
    filters: {
      formatStatus(value) {
        if (value === 'before') {
          return '已开抢'
        } else if (value === 'current') {
          return '正在抢购'
        } else if (value === 'after') {
          return '即将开始'
        }
      }
    },
    data() {
      return {
        tabCur: 0,
        scrollLeft: 0
      }
    },
    watch: {
      tabCur: function (val, oldVal) {
        this.scrollLeft = (val - 1) * 60
      },
      items: function (val, oldVal) {
        console.log('=====>')
        this.tabCur = val.findIndex(o => {
          console.log(o[this.textStatus])
          return o[this.textStatus] === 'current'
        });
      }
    },
    props: {
      items: {
        type: Array,
        default: []
      },
      textProp: {
        type: String,
        default: 'time'
      },
      textStatus: {
        type: String,
        default: 'status'
      }
    },
    methods: {
      tabSelect(index, item) {
        this.tabCur = index;
        this.$emit('tabselect', index, item);
      }
    },
  }
</script>

<style scoped>
  .cu-item {
    height: 120upx;
    line-height: 60upx !important;
  }
</style>
