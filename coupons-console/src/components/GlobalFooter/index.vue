<template>
  <global-footer class="footer custom-render">
    <template v-slot:links>
      <a href="http://www.starblues.cn:8000" target="_blank">文档</a>
      <a href="https://gitee.com/starblues/grape" target="_blank">Gitee</a>
    </template>
    <template v-slot:copyright>
      <a href="javascript:void(0);">Copyright © 2019-2021 StarBlues</a>
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
