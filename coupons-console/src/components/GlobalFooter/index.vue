<template>
  <global-footer class="footer custom-render">
    <template v-slot:links>
      <a href="https://silently9527.cn" target="_blank">文档</a>
      <a href="https://github.com/silently9527/coupons" target="_blank">Github</a>
      <a href="https://gitee.com/silently9527/mall-coupons-server" target="_blank">Gitee</a>
    </template>
    <template v-slot:copyright>
      <a href="javascript:void(0);">Copyright © 2020-2021 Silently9527</a>
    </template>
  </global-footer>
</template>

<script>
import { GlobalFooter } from '@ant-design-vue/pro-layout'

export default {
  name: 'ProGlobalFooter',
  components: {
    GlobalFooter
  }
}
</script>
