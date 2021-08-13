<template>
  <scroll-view scroll-x class="bg-white nav solid-bottom" scroll-with-animation :scroll-left="scrollLeft">
    <view class="cu-item"
          :class="index===tabCur?'text-red cur':''"
          v-for="(item,index) in items"
          :key="index"
          @tap="tabSelect(index, item)">
      {{item[textProp]}}
    </view>
  </scroll-view>
</template>

<script>
  export default {
    data() {
      return {
        tabCur: 0,
        scrollLeft: 0
      }
    },
    props: {
      items: {
        type: Array,
        default: []
      },
      textProp: {
        type: String,
        default: 'text'
      }
    },
    methods: {
      tabSelect(index, item) {
        this.tabCur = index;
        this.scrollLeft = (index - 1) * 60
        this.$emit('tabselect', index, item);
      }
    }
  }
</script>

<style scoped>
</style>
