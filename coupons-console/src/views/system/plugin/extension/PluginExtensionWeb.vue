<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <div ref="" id="micro-view"></div>
    </a-card>
  </page-header-wrapper>
</template>

<script>
  import { start } from 'qiankun'
  export default {
    name: 'PluginIndex',
    data: () => {
      return {
        loading: true,
        show: false,
        url: '',
        searchTableHeight: 0
      }
    },
    mounted () {
      if (!window.qiankunStarted) {
        window.qiankunStarted = true
        start({ sandbox: { strictStyleIsolation: true } })
      }
    },
    methods: {
    }
  }
</script>

<style lang="less" scoped>

</style>
