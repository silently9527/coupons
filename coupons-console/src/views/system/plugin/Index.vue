<template>
  <page-header-wrapper>
    <a-card :bordered="true">
      <a-tabs default-active-key="installedPlugin">
        <a-tab-pane key="installedPlugin" tab="已装插件">
          <installed-plugin></installed-plugin>
        </a-tab-pane>
        <a-tab-pane key="pluginCenter" tab="插件中心">
          <plugin-center></plugin-center>
        </a-tab-pane>
      </a-tabs>
    </a-card>
  </page-header-wrapper>
</template>

<script>
import InstalledPlugin from '@/views/system/plugin/InstalledPlugin'
import PluginCenter from '@/views/system/plugin/PluginCenter'

  export default {
    name: 'PluginManage',
    components: {
      InstalledPlugin,
      PluginCenter
    },
    data () {
      return {
      }
    },
    created () {
    },
    methods: {

    }
  }
</script>

<style lang="less" scoped>
  .permission-form {
    /deep/ .permission-group {
      margin-top: 0;
      margin-bottom: 0;
    }
  }
</style>
