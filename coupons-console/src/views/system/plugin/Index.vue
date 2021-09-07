<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <div class="table-operator" v-if="$auth('menu:operate')">
        <a-button type="primary" @click="installPlugin">安装插件</a-button>
      </div>
      <a-table
        :columns="columns"
        :row-key="record => record.pluginDescriptor.pluginId"
        :data-source="data"
        :loading="loading"
        size="middle"
      >
        <span slot="pluginState" slot-scope="pluginState">
          <a-tag color="cyan" v-if="pluginState === 'CREATED'">创建</a-tag>
          <a-tag color="red" v-if="pluginState === 'DISABLED'">禁用</a-tag>
          <a-tag color="purple" v-if="pluginState === 'RESOLVED'">已加载</a-tag>
          <a-tag color="green" v-if="pluginState === 'STARTED'">启动</a-tag>
          <a-tag color="pink" v-if="pluginState === 'STOPPED'">停止</a-tag>
        </span>
        <span slot="action" slot-scope="record" v-if="$auth('menu:operate')">
          <a-tag color="blue" v-if="record.pluginState === 'STOPPED'" @click="startPlugin(record)" style="cursor:pointer">
            启动
          </a-tag>
          <a-tag color="blue" v-if="record.pluginState === 'STARTED'" @click="stopPlugin(record)" style="cursor:pointer">
            停止
          </a-tag>
          <a-tag color="red" @click="uninstallPlugin(record)" style="cursor:pointer">
            卸载
          </a-tag>
        </span>
      </a-table>
    </a-card>
    <install
      ref="modal"
      :visible="modelVisible"
      :loading="modelConfirmLoading"
      @cancel="modelHandleCancel"
      @ok="modelHandleOk"
      v-if="$auth('menu:operate')"
    />
  </page-header-wrapper>
</template>

<script>
  import { STable } from '@/components'
  import { getPluginList, start, stop, uninstall } from '@/api/plugin'
  import { fetchResult } from '@/utils/fetchUtil'
  import Install from '@/views/system/plugin/Install'

  const columns = [
    {
      title: 'id',
      dataIndex: 'pluginDescriptor.pluginId'
    },
    {
      title: '版本',
      dataIndex: 'pluginDescriptor.version'
    },
    {
      title: '开发者',
      dataIndex: 'pluginDescriptor.provider'
    },
    {
      title: '状态',
      dataIndex: 'pluginState',
      scopedSlots: { customRender: 'pluginState' }
    },
    {
      title: '描述',
      dataIndex: 'pluginDescriptor.pluginDescription',
      ellipsis: true
    },
    {
      title: '安装路径',
      dataIndex: 'path',
      ellipsis: true
    },
    {
      title: '操作',
      scopedSlots: { customRender: 'action' }
    }
  ]

  export default {
    name: 'PluginManage',
    components: {
      STable,
      Install
    },
    data () {
      return {
        loading: false,
        // 表头
        columns: columns,
        // 加载数据方法 必须为 Promise 对象
        data: [],
        modelVisible: false,
        modelConfirmLoading: false
      }
    },
    created () {
      this.fetchList({
        currentPage: 1
      })
    },
    methods: {
      fetchList (params) {
        if (!this.$auth('plugin:query')) {
          return
        }
        this.loading = true
        getPluginList(params)
          .then(res => {
            if (fetchResult(res, false, true)) {
              this.data = res.data
            }
            this.loading = false
          })
      },
      startPlugin (record) {
        const id = record.pluginDescriptor.pluginId
        const tag = this
        this.$confirm({
          title: '提示',
          content: `确认启动插件：[${id}]?`,
          confirmLoading: true,
          okText: '确认',
          cancelText: '取消',
          onOk () {
            return new Promise((resolve, reject) => {
              start(id).then(res => {
                  if (fetchResult(res)) {
                    tag.fetchList()
                  }
                  resolve()
                })
            })
          },
          onCancel () {}
        })
      },
      stopPlugin (record) {
        const id = record.pluginDescriptor.pluginId
        const tag = this
        this.$confirm({
          title: '提示',
          content: `确认停止插件：[${id}]?`,
          confirmLoading: true,
          okText: '确认',
          cancelText: '取消',
          onOk () {
            return new Promise((resolve, reject) => {
              stop(id).then(res => {
                  if (fetchResult(res)) {
                    tag.fetchList()
                  }
                  resolve()
                })
            })
          },
          onCancel () {}
        })
      },
      uninstallPlugin (record) {
        const id = record.pluginDescriptor.pluginId
        const tag = this
        this.$confirm({
          title: '提示',
          content: `确认卸载插件：[${id}]?`,
          confirmLoading: true,
          okText: '确认',
          cancelText: '取消',
          onOk () {
            return new Promise((resolve, reject) => {
              uninstall(id).then(res => {
                  if (fetchResult(res)) {
                    tag.fetchList()
                  }
                  resolve()
                })
            })
          },
          onCancel () {}
        })
      },
      installPlugin () {
        this.modelVisible = true
      },
      modelHandleOk () {
        this.modelVisible = false
        this.fetchList()
      },
      modelHandleCancel () {
        this.modelVisible = false
      }
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
