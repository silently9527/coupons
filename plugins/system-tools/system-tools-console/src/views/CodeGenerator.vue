<template>
  <div>
    <a-form-model ref="searchFromRef" layout="inline" :model="searchFrom" @submit="fetchList" @submit.native.prevent>
      <a-form-model-item prop="name">
        <a-input v-model="searchFrom.name" placeholder="名称"/>
      </a-form-model-item>
      <a-form-model-item>
        <a-button type="primary" html-type="submit">查询</a-button>
      </a-form-model-item>
      <a-form-model-item>
        <a-button type="primary" @click="()=> $refs.searchFromRef.resetFields()">重置</a-button>
      </a-form-model-item>
      <a-form-model-item>
        <a-button type="primary" @click="showMode('add')">新增用户</a-button>
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
        驱动名称: {{ record.driverName }} <br/>
        url: {{ record.url }} <br/>
        username: {{ record.username }} <br/>
        password: {{ record.password }} <br/>
        生成包名: {{ record.basePackageName }} <br/>
        表名: {{ record.tableNames }} <br/>
      </p>
      <span slot="action" slot-scope="record">
        <a-tag color="blue" @click="handleGen(record.id)" style="cursor:pointer">
          生成代码
        </a-tag>
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
// @ is an alias to /src
import { getPageList, add, update, deleteById, gen } from '@/api/codeGenerator'
import EditModel from './EditModel'
import { fetchResult } from '@/utils/fetchUtil'

const pageSize = 10

const columns = [
  {
    title: '名称',
    dataIndex: 'name',
    width: 80
  },
  {
    title: '作者',
    dataIndex: 'author',
    width: 80
  },
  {
    title: '描述',
    dataIndex: 'description',
    width: 80
  },
  {
    title: '创建时间',
    dataIndex: 'gmtCreated',
    width: 80
  },
  {
    title: '操作',
    key: 'action',
    scopedSlots: { customRender: 'action' },
    width: 260
  }
]

export default {
  name: 'CodeGenerator',
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
      searchFrom: {
        name: ''
      },
      model: {
        visible: false,
        confirmLoading: false,
        form: {},
        opType: null
      },
      isGen: false
    }
  },
  created () {
    this.fetchList()
  },
  methods: {
    fetchList (params = {}) {
      params = { ...this.searchFrom }
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
    handleGen (id) {
      if (this.isGen) {
        this.$notification['error']({
          message: '提示',
          description: '正在生成中, 不能重复生成',
          duration: 2
        })
        return
      }
      this.isGen = true

      gen(id)
        .then(response => {
          console.log(response)
          // 提取文件名
          this.isGen = false
          const contentDisposition = response.headers['content-disposition']
          let fileName = null
          console.log(contentDisposition)
          if (contentDisposition) {
            fileName = contentDisposition.match(/filename=(.*)/)[1]
          } else {
            fileName = 'code.zip'
          }
          // 将二进制流转为blob
          const blob = new Blob([response.data], { type: 'application/octet-stream' })
          if (typeof window.navigator.msSaveBlob !== 'undefined') {
            // 兼容IE，window.navigator.msSaveBlob：以本地方式保存文件
            window.navigator.msSaveBlob(blob, decodeURI(fileName))
          } else {
            // 创建新的URL并指向File对象或者Blob对象的地址
            const blobURL = window.URL.createObjectURL(blob)
            // 创建a标签，用于跳转至下载链接
            const tempLink = document.createElement('a')
            tempLink.style.display = 'none'
            tempLink.href = blobURL
            tempLink.setAttribute('download', decodeURI(fileName))
            // 兼容：某些浏览器不支持HTML5的download属性
            if (typeof tempLink.download === 'undefined') {
              tempLink.setAttribute('target', '_blank')
            }
            // 挂载a标签
            document.body.appendChild(tempLink)
            tempLink.click()
            document.body.removeChild(tempLink)
            // 释放blob URL地址
            window.URL.revokeObjectURL(blobURL)
          }
        })
    },
    removeById (record) {
      const tag = this
      this.$confirm({
        title: '提示',
        content: `确认删除配置 [${record.name}]?`,
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
    }
  }
}
</script>
