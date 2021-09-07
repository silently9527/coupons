<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <div class="table-operator" v-if="$auth('menu:add')">
        <a-button type="primary" @click="showMode('add')">新增菜单</a-button>
      </div>
      <div class="table-page-search-wrapper">
        <a-table
          :columns="columns"
          :data-source="data"
          :row-key="record => record.menuId"
          :loading="loading"
          :rowSelection="rowSelection"
          size="middle"
        >
          <span slot="type" slot-scope="type">
            <a-tag color="#108ee9" v-if="type === 1">目录</a-tag>
            <a-tag color="#87d068" v-if="type === 2">菜单</a-tag>
            <a-tag color="purple" v-if="type === 3">权限</a-tag>
          </span>
          <span slot="enable" slot-scope="enable">
            <a-tag color="cyan" v-if="enable === 1">启用</a-tag>
            <a-tag color="pink" v-if="enable === 0">禁用</a-tag>
          </span>
          <span slot="menuType" slot-scope="menuType">
            <a-tag color="#01AAED" v-if="menuType === 0">系统</a-tag>
            <a-tag color="#009688" v-if="menuType === 1">插件</a-tag>
          </span>
          <span slot="action" slot-scope="scope" v-if="scope.pluginMenu === 0">
            <span v-if="$auth('menu:updateStatus')">
              <a-tag color="blue" v-if="scope.enable === 0" @click="updateStatus(scope, 1)" style="cursor:pointer">
                启用
              </a-tag>
              <a-tag color="blue" v-if="scope.enable === 1" @click="updateStatus(scope, 0)" style="cursor:pointer">
                禁用
              </a-tag>
            </span>
            <a-tag color="purple" v-if="$auth('menu:update')" @click="showMode('update', scope)" style="cursor:pointer">
              修改
            </a-tag>
            <a-tag color="red" v-if="$auth('menu:delete')" style="cursor:pointer" @click="removeById(scope)">
              删除
            </a-tag>
          </span>
        </a-table>
      </div>
      <edit
        v-if="$auth('menu:add') || $auth('menu:update')"
        ref="model"
        :visible="model.visible"
        :loading="model.confirmLoading"
        :from-data="model.form"
        :menu-tree="data"
        :opType="model.opType"
        @cancel="modelHandleCancel"
        @ok="modelHandleOk"
      />

    </a-card>
  </page-header-wrapper>
</template>

<script>
  import { STable } from '@/components'
  import { getNavTree, updateStatus, deleteById } from '@/api/menu'
  import Edit from '@/views/system/menu/component/Edit'
  import { fetchResult } from '@/utils/fetchUtil'
  const rowSelection = {
    onChange: (selectedRowKeys, selectedRows) => {
      console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows)
    },
    onSelect: (record, selected, selectedRows) => {
      console.log(record, selected, selectedRows)
    },
    onSelectAll: (selected, selectedRows, changeRows) => {
      console.log(selected, selectedRows, changeRows)
    }
  }
  const columns = [
    {
      title: '名称',
      dataIndex: 'title',
      key: 'title'
    },
    {
      title: '排序',
      dataIndex: 'orderNum',
      key: 'orderNum'
    },
    {
      title: '权限标志',
      dataIndex: 'permissions',
      key: 'permissions'
    },
    {
      title: '地址',
      dataIndex: 'path',
      key: 'path'
    },
    {
      title: '类型',
      dataIndex: 'type',
      key: 'type',
      scopedSlots: { customRender: 'type' }
    },
    {
      title: '状态',
      dataIndex: 'enable',
      key: 'enable',
      scopedSlots: { customRender: 'enable' }
    },
    {
      title: '类型',
      dataIndex: 'pluginMenu',
      key: 'pluginMenu',
      scopedSlots: { customRender: 'menuType' }
    },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' }
    }
  ]

  export default {
    name: 'MenuManage',
    components: {
      STable,
      Edit
    },
    data () {
      return {
        loading: false,
        columns: columns,
        data: [],
        rowSelection: rowSelection,
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
      fetchList () {
        getNavTree(true)
          .then(res => {
            if (fetchResult(res, false, true)) {
              this.data = res.data
            }
            this.loading = false
          })
      },
      updateStatus (record, enable) {
        const message = enable === 1 ? '启用' : '禁用'
        const tag = this
        let appendMsg = ''
        if (record.children && record.children.length > 0) {
          appendMsg = '下属菜单也会被' + message
        }
        this.$confirm({
          title: '提示',
          content: `确认${message}菜单 [${record.title}]? ${appendMsg}`,
          confirmLoading: true,
          okText: '确认',
          cancelText: '取消',
          onOk () {
            return new Promise((resolve, reject) => {
              updateStatus(record.menuId, enable)
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
        let message = ''
        if (record.children && record.children.length > 0) {
          message = '下属菜单也会被删除'
        }
        const tag = this
        this.$confirm({
          title: '提示',
          content: `确认删除菜单 [${record.title}]? ${message}`,
          confirmLoading: true,
          okText: '确认',
          cancelText: '取消',
          onOk () {
            return new Promise((resolve, reject) => {
              deleteById(record.menuId)
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
      handleTableChange (pagination, filters, sorter) {
        this.fetchList()
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
      modelHandleOk (result) {
        if (result) {
          this.fetchList()
        }
        this.model.visible = false
      },
      modelHandleCancel () {
        this.model.visible = false
      }
    }
  }
</script>
