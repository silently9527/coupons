<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <div class="table-operator" >
        <a-form-model ref="searchFromRef" layout="inline" :model="searchFrom" @submit="fetchList" @submit.native.prevent>
          <span v-if="$auth('role:query')">
            <a-form-model-item prop="name">
              <a-input v-model="searchFrom.name" placeholder="角色名称"/>
            </a-form-model-item>
            <a-form-model-item prop="roleCode">
              <a-input v-model="searchFrom.roleCode" placeholder="角色编号"/>
            </a-form-model-item>
            <a-form-model-item label="状态" prop="status">
              <a-select v-model="searchFrom.status" placeholder="选择角色状态" style="width: 120px">
                <a-select-option value="">
                  全部
                </a-select-option>
                <a-select-option value="1">
                  启用
                </a-select-option>
                <a-select-option value="0">
                  禁用
                </a-select-option>
              </a-select>
            </a-form-model-item>
            <a-form-model-item>
              <a-button type="primary" html-type="submit">查询</a-button>
            </a-form-model-item>
            <a-form-model-item>
              <a-button type="primary" @click="()=> $refs.searchFromRef.resetFields()">重置</a-button>
            </a-form-model-item>
          </span>
          <a-form-model-item v-if="$auth('role:add')">
            <a-button type="primary" @click="showMode('add')">新增角色</a-button>
          </a-form-model-item>
        </a-form-model>

      </div>
      <div class="table-page-search-wrapper">
        <a-table
          :columns="columns"
          :row-key="record => record.roleId"
          :data-source="data"
          :pagination="pagination"
          :loading="loading"
          @change="handleTableChange"
          :transformCellText="transformCellText"
          size="middle"
        >
          <span slot="status" slot-scope="status">
            <a-tag color="cyan" v-if="status === 1">启用</a-tag>
            <a-tag color="pink" v-if="status === 0">禁用</a-tag>
          </span>
          <span slot="action" slot-scope="record">
            <span v-if="$auth('role:updateStatus')">
              <a-tag color="blue" v-if="record.status === 0" @click="updateStatus(record, 1)" style="cursor:pointer">
                启用
              </a-tag>
              <a-tag color="blue" v-if="record.status === 1" @click="updateStatus(record, 0)" style="cursor:pointer">
                禁用
              </a-tag>
            </span>
            <a-tag v-if="$auth('role:update')" color="purple" @click="showMode('update', record)" style="cursor:pointer">
              修改
            </a-tag>
            <a-tag v-if="$auth('role:delete')" color="red" @click="removeById(record)" style="cursor:pointer">
              删除
            </a-tag>
            <a-tag v-if="$auth('role:update')" color="purple" @click="showModeRoleMenu(record)" style="cursor:pointer">
              分配权限
            </a-tag>
          </span>
        </a-table>
      </div>
    </a-card>
    <edit-model
      v-if="$auth('role:update') || $auth('role:add')"
      ref="model"
      :visible="model.visible"
      :loading="model.confirmLoading"
      :from-data="model.form"
      :opType="model.opType"
      @cancel="modelHandleCancel"
      @ok="modelHandleOk"
    />

    <role-menu-edit-model
      v-if="$auth('role:update') && roleMenuModel.visible"
      ref="roleMenuModel"
      :visible="roleMenuModel.visible"
      :loading="roleMenuModel.confirmLoading"
      :role-id="roleMenuModel.roleId"
      @cancel="roleMenuModelHandleCancel"
    />
  </page-header-wrapper>
</template>

<script>
  import { STable } from '@/components'
  import EditModel from '@/views/system/role/EditModel'
  import RoleMenuEditModel from '@/views/system/role/RoleMenuEditModel'
  import { getPageList, deleteById, updateStatus } from '@/api/role'
  import { fetchResult } from '@/utils/fetchUtil'
  import { transformCellText } from '@/utils/tableUtils'
  const pageSize = 10

  const columns = [
    {
      title: '名称',
      dataIndex: 'name'
    },
    {
      title: '编码',
      dataIndex: 'code'
    },
    {
      title: '状态',
      dataIndex: 'status',
      scopedSlots: { customRender: 'status' }
    },
    {
      title: '描述',
      dataIndex: 'description',
      scopedSlots: { customRender: 'name' },
      ellipsis: true
    },
    {
      title: '修改时间',
      dataIndex: 'gmtModified'
    },
    {
      title: '操作',
      key: 'action',
      width: 300,
      scopedSlots: { customRender: 'action' }
    }
  ]

  export default {
    name: 'RoleManage',
    components: {
      STable,
      EditModel,
      RoleMenuEditModel
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
        searchFrom: {
          name: '',
          roleCode: '',
          status: ''
        },
        model: {
          visible: false,
          confirmLoading: false,
          form: {},
          opType: null
        },
        roleMenuModel: {
          visible: false,
          confirmLoading: false,
          roleId: ''
        }
      }
    },
    created () {
      this.fetchList()
    },
    methods: {
      fetchList (params = {}) {
        if (!this.$auth('role:query')) {
          return
        }
        params = { ...this.searchFrom }
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
          content: `确认${message}角色 [${record.name}]?`,
          confirmLoading: true,
          okText: '确认',
          cancelText: '取消',
          onOk () {
            return new Promise((resolve, reject) => {
              updateStatus(record.roleId, status)
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
          content: `确认删除角色 [${record.name}]? 删除角色后, 下属用户关联将会消失`,
          confirmLoading: true,
          okText: '确认',
          cancelText: '取消',
          onOk () {
            return new Promise((resolve, reject) => {
              deleteById(record.roleId)
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
      modelHandleOk (result) {
        if (result) {
          this.fetchList()
        }
        this.model.visible = false
      },
      modelHandleCancel () {
        this.model.visible = false
      },
      showModeRoleMenu (record) {
        this.roleMenuModel.roleId = record.roleId
        this.roleMenuModel.visible = true
      },
      roleMenuModelHandleCancel () {
        this.roleMenuModel.visible = false
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
