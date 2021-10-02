<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <div class="table-operator" >
        <a-form-model ref="searchFromRef" layout="inline">
          <a-form-model-item>
            <a-button type="primary" @click="showMode('add')">新增TabBar</a-button>
          </a-form-model-item>
          <a-form-model-item>
            <a-tag color="red">新增或修改Tab需要重新打包客户端！</a-tag>
          </a-form-model-item>

        </a-form-model>

      </div>
      <div class="table-page-search-wrapper">
        <a-table
          bordered
          :columns="columns"
          :row-key="record => record.userId"
          :data-source="data"
          :pagination="pagination"
          :loading="loading"
          @change="handleTableChange"
          size="middle"
          :transformCellText="transformCellText"
        >
          <span slot="status" slot-scope="status">
            <a-tag color="cyan" v-if="status === 1">启用</a-tag>
            <a-tag color="pink" v-if="status === 0">禁用</a-tag>
          </span>
          <span slot="action" slot-scope="record">
            <span >
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
      </div>
    </a-card>
    <edit-model
      ref="model"
      :visible="model.visible"
      :loading="model.confirmLoading"
      :from-data="model.form"
      :opType="model.opType"
      @cancel="modelHandleCancel"
      @ok="modelHandleOk"
    />
  </page-header-wrapper>
</template>

<script>
  import { STable } from '@/components'
  import EditModel from '@/views/client/tabbar/EditModel'
  import { getPageList, updateStatus, deleteById } from '@/api/tabbar'
  import { fetchResult } from '@/utils/fetchUtil'
  import { transformCellText } from '@/utils/tableUtils'

  const pageSize = 10

  const columns = [
    {
      title: 'Tab名称',
      dataIndex: 'text',
      width: 120
    },
    {
      title: '页面路径',
      dataIndex: 'pagePath'
    },
    {
      title: 'Tab图标',
      dataIndex: 'iconPath'
    },
    {
      title: '选中时Tab图标',
      dataIndex: 'selectedIconPath'
    },
    {
      title: '状态',
      dataIndex: 'status',
      scopedSlots: { customRender: 'status' },
      width: 80
    },
    {
      title: '排序',
      dataIndex: 'sorted',
      scopedSlots: { customRender: 'locked' },
      width: 50
    },
    {
      title: '创建时间',
      dataIndex: 'gmtCreated',
      ellipsis: true,
      width: 170
    },
    {
      title: '备注',
      dataIndex: 'remark',
      width: 120,
      ellipsis: true
    },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
      width: 180
    }
  ]
  export default {
    name: 'TabBarManage',
    components: {
      STable,
      EditModel
    },
    data () {
      return {
        loading: false,
        transformCellText: transformCellText,
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
            if (fetchResult(res, false, true)) {
              const pagination = { ...this.pagination }
              pagination.total = res.data.total
              this.data = res.data.records
              this.pagination = pagination
            }
            this.loading = false
          })
      },
      handleTableChange (pagination, filters, sorter) {
        this.pagination.current = pagination.current
        this.fetchList()
      },
      updateStatus (record, status) {
        const message = status === 1 ? '启用' : '禁用'
        const tag = this
        this.$confirm({
          title: '提示',
          content: `确认${message}该Tab?`,
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
      },
      removeById (record) {
        const tag = this
        this.$confirm({
          title: '提示',
          content: `确认删除该Tab?`,
          confirmLoading: true,
          okText: '确认',
          cancelText: '取消',
          onOk () {
            return new Promise((resolve, reject) => {
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
          this.model.form = { ...record }
          this.model.opType = 'update'
          this.model.visible = true
        } else {
          // 新增
          this.model.opType = 'add'
          this.model.visible = true
        }
      },
      modelHandleOk (result) {
        if (result) {
          this.fetchList()
        }
        this.model.visible = false
      },
      modelHandleCancel () {
        this.model.visible = false
      },
      resetPasswordModelOpen (record) {
        this.resetPasswordMode = {
          visible: true,
          userId: record.userId,
          userName: record.name
        }
      },
      resetPasswordModelClose () {
        this.resetPasswordMode.visible = false
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
