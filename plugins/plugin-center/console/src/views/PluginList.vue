<template>
  <div>
    <a-form-model ref="searchFromRef" layout="inline">
      <a-form-model-item>
        <a-button type="primary" @click="showMode('add')">新增插件</a-button>
      </a-form-model-item>
    </a-form-model>
    <br/>
    <a-table
      :columns="columns"
      :row-key="record => record.id"
      :data-source="data"
      size="middle"
      :loading="loading"
    >
      <p slot="expandedRowRender" slot-scope="record" style="margin: 0">
        <span>二维码: {{ record.qrcode }}</span><br/>
        <span>提示: {{ record.remark }}</span><br/>
        <span>创建用户: {{ record.qrcode }}</span><br/>
        <span>文档地址: {{ record.docUrl }}</span><br/>
        <span>下载地址: {{ record.downloadUrl }}</span><br/>
        <span>描述: {{ record.description }}</span><br/>
      </p>
      <span slot="status" slot-scope="status">
        <a-tag color="cyan" v-if="status === 1">启用</a-tag>
        <a-tag color="pink" v-if="status === 0">禁用</a-tag>
      </span>
      <span slot="action" slot-scope="record">
        <span>
          <a-tag color="blue" v-if="record.status === 0" @click="updateStatus(record, 1)" style="cursor:pointer">
            启用
          </a-tag>
          <a-tag color="blue" v-if="record.status === 1" @click="updateStatus(record, 0)" style="cursor:pointer">
            禁用
          </a-tag>
        </span>
        <a-tag color="purple" @click="showMode('update', record)" style="cursor:pointer">
          修改
        </a-tag>
        <a-tag color="red" @click="removeById(record)" style="cursor:pointer">
          删除
        </a-tag>
      </span>
    </a-table>
    <edit-model
        ref="model"
        :visible="model.visible"
        :loading="model.confirmLoading"
        :from-data="model.form"
        :opType="model.opType"
        @cancel="modelHandleCancel"
        @ok="modelHandleOk"/>
  </div>
</template>

<script>
import EditModel from './EditModel'
import { add, getPageList, updateStatus, update, deleteById } from '@/api/plugin'
import { fetchResult } from '@/utils/fetchUtil'

const pageSize = 10

const columns = [
  {
    title: '插件名称',
    dataIndex: 'pluginName',
    width: 120
  },
  {
    title: '图标',
    dataIndex: 'icon',
    width: 200
  },
  {
    title: '作者',
    dataIndex: 'author',
    width: 80
  },
  {
    title: '版本',
    dataIndex: 'version',
    width: 80
  },
  {
    title: '价格',
    dataIndex: 'price',
    width: 80
  },
  {
    title: '状态',
    dataIndex: 'status',
    scopedSlots: { customRender: 'status' },
    width: 80
  },
  {
    title: '操作',
    key: 'action',
    scopedSlots: { customRender: 'action' },
    width: 180
  }
]

export default {
  name: 'PluginCenter',
  components: {
    EditModel
  },
  data () {
    return {
      loading: false,
      // 表头
      columns: columns,
      // 加载数据方法 必须为 Promise 对象
      data: [],
      pagination: {
          current: 1,
          pageSize: pageSize,
          showQuickJumper: true
      },
      model: {
        visible: false,
        confirmLoading: false,
        form: {},
        opType: null
      }
    }
  },
  created () {
    this.fetchList()
  },
  methods: {
    fetchList (params = {}) {
      params.pageSize = pageSize
      params.currentPage = this.pagination.current
      this.loading = true
      getPageList(params)
        .then(res => {
          if (!res && !res.data) {
            return
          }
          const pagination = { ...this.pagination }
          pagination.total = res.data.total
          this.data = res.data.records
          this.pagination = pagination
          this.loading = false
        })
    },
    removeById (record) {
      const tag = this
      this.$confirm({
        title: '提示',
        content: `确认删除插件 [${record.pluginName}]?`,
        confirmLoading: true,
        okText: '确认',
        cancelText: '取消',
        onOk () {
          return new Promise((resolve) => {
            deleteById(record.id)
              .then(res => {
                resolve()
                if (fetchResult(res)) {
                  tag.fetchList()
                }
              })
          })
        },
        onCancel () {}
      })
    },
    showMode (type, record) {
      if (type === 'update') {
        // 修改
        this.model.form = record
        this.model.opType = 'update'
      } else {
        // 新增
        this.model.opType = 'add'
      }
      this.model.visible = true
    },
    modelHandleOk (opType, fromData) {
      if (opType === 'add') {
        // 新增
        this.model.confirmLoading = true
        add(fromData)
          .then(res => {
            if (fetchResult(res)) {
              this.fetchList()
              this.model.visible = false
            }
            this.model.confirmLoading = false
          })
      } else if (opType === 'update') {
        // 修改
        this.model.confirmLoading = true
        update(fromData)
            .then(res => {
              if (fetchResult(res)) {
                this.fetchList()
                this.model.visible = false
              }
              this.model.confirmLoading = false
            })
      }
    },
    modelHandleCancel () {
      this.model.visible = false
    },
    updateStatus (record, status) {
      const message = status === 1 ? '启用' : '禁用'
      const tag = this
      this.$confirm({
        title: '提示',
        content: `确认${message}插件[${record.pluginName}]?`,
        confirmLoading: true,
        okText: '确认',
        cancelText: '取消',
        onOk () {
          return new Promise((resolve, reject) => {
            updateStatus(record.id, status)
                .then(res => {
                  resolve()
                  if (fetchResult(res)) {
                    tag.fetchList()
                  }
                })
          })
        },
        onCancel () {}
      })
    }
  }
}
</script>
