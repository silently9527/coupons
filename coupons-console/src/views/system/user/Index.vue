<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <div class="table-operator" >
        <a-form-model ref="searchFromRef" layout="inline" :model="searchFrom" @submit="fetchList" @submit.native.prevent>
          <span v-if="$auth('user:query')">
            <a-form-model-item prop="name">
              <a-input v-model="searchFrom.name" placeholder="用户名称"/>
            </a-form-model-item>
            <a-form-model-item prop="username">
              <a-input v-model="searchFrom.username" placeholder="用户名"/>
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
            <a-form-model-item label="账户状态" prop="locked">
              <a-select v-model="searchFrom.locked" placeholder="选择账户状态" style="width: 120px">
                <a-select-option value="">
                  全部
                </a-select-option>
                <a-select-option value="false">
                  正常
                </a-select-option>
                <a-select-option value="true">
                  锁定
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
          <a-form-model-item v-if="$auth('user:add')">
            <a-button type="primary" @click="showMode('add')">新增用户</a-button>
          </a-form-model-item>
        </a-form-model>

      </div>
      <div class="table-page-search-wrapper">
        <a-table
          :columns="columns"
          :row-key="record => record.userId"
          :data-source="data"
          :pagination="pagination"
          :loading="loading"
          @change="handleTableChange"
          size="middle"
          :transformCellText="transformCellText"
        >
          <p slot="expandedRowRender" slot-scope="record" style="margin: 0">
            <span>最近登录时间: {{ record.lastLoginTime }}</span><br/>
            <span>最近登录ip: {{ record.lastLoginIp }}</span><br/>
            <span>创建用户: {{ record.createUser }}</span><br/>
            <span>创建时间: {{ record.gmtCreated }}</span><br/>
            <span>修改用户: {{ record.modifiedUser }}</span><br/>
            <span>修改时间: {{ record.gmtModified }}</span><br/>
          </p>
          <span slot="status" slot-scope="status">
            <a-tag color="cyan" v-if="status === 1">启用</a-tag>
            <a-tag color="pink" v-if="status === 0">禁用</a-tag>
          </span>
          <span slot="locked" slot-scope="locked">
            <a-tag color="pink" v-if="locked > 5">被锁</a-tag>
            <a-tag color="cyan" v-if="locked <= 5">正常</a-tag>
          </span>
          <span slot="action" slot-scope="record" v-if="record.userId != '1'">
            <span v-if="$auth('user:updateStatus')">
              <a-tag color="blue" v-if="record.status === 0" @click="updateStatus(record, 1)" style="cursor:pointer">
                启用
              </a-tag>
              <a-tag color="blue" v-if="record.status === 1" @click="updateStatus(record, 0)" style="cursor:pointer">
                禁用
              </a-tag>
            </span>
            <a-tag v-if="$auth('user:update')" color="purple" @click="showMode('update', record)" style="cursor:pointer">
              修改
            </a-tag>
            <a-tag v-if="$auth('user:resetPassword')" color="cyan" @click="resetPasswordModelOpen(record)" style="cursor:pointer">
              重置密码
            </a-tag>
            <a-tag v-if="$auth('user:delete')" color="red" @click="removeById(record)" style="cursor:pointer">
              删除
            </a-tag>
          </span>
        </a-table>
      </div>
    </a-card>
    <edit-model
      v-if="$auth('user:update') || $auth('user:add')"
      ref="model"
      :visible="model.visible"
      :loading="model.confirmLoading"
      :from-data="model.form"
      :opType="model.opType"
      @cancel="modelHandleCancel"
      @ok="modelHandleOk"
    />
    <reset-password-model
      v-if="$auth('user:resetPassword')"
      ref="resetPasswordModel"
      :visible="resetPasswordMode.visible"
      :user-id="resetPasswordMode.userId"
      :user-name="resetPasswordMode.userName"
      @close="resetPasswordModelClose"
    />
  </page-header-wrapper>
</template>

<script>
  import { STable } from '@/components'
  import EditModel from '@/views/system/user/EditModel'
  import ResetPasswordModel from '@/views/system/user/ResetPasswordModel'
  import { getPageList, getRoleByUser, updateStatus, deleteById } from '@/api/user'
  import { fetchResult } from '@/utils/fetchUtil'
  import { transformCellText } from '@/utils/tableUtils'

  const pageSize = 10

  const columns = [
    {
      title: '姓名',
      dataIndex: 'name',
      width: 80
    },
    {
      title: '用户名',
      dataIndex: 'username',
      width: 80
    },
    {
      title: '所属角色',
      dataIndex: 'roleNames',
      ellipsis: true
    },
    {
      title: '状态',
      dataIndex: 'status',
      scopedSlots: { customRender: 'status' },
      width: 70
    },
    {
      title: '账户状态',
      dataIndex: 'locked',
      scopedSlots: { customRender: 'locked' },
      width: 80
    },
    {
      title: '创建时间',
      dataIndex: 'gmtCreated',
      ellipsis: true
    },
    {
      title: '登录ip',
      dataIndex: 'lastLoginIp',
      ellipsis: true
    },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
      width: 260
    }
  ]
  export default {
    name: 'TableManage',
    components: {
      STable,
      EditModel,
      ResetPasswordModel
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
          username: '',
          status: '',
          locked: ''
        },
        model: {
          visible: false,
          confirmLoading: false,
          form: {},
          opType: null
        },
        resetPasswordMode: {
          visible: false,
          userId: undefined,
          userName: undefined
        }
      }
    },
    created () {
      this.fetchList()
    },
    methods: {
      fetchList (params = {}) {
        if (!this.$auth('user:query')) {
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
          content: `确认${message}用户 [${record.name}]?`,
          confirmLoading: true,
          okText: '确认',
          cancelText: '取消',
          onOk () {
            return new Promise((resolve, reject) => {
              updateStatus(record.userId, status)
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
          content: `确认删除用户 [${record.name}]?`,
          confirmLoading: true,
          okText: '确认',
          cancelText: '取消',
          onOk () {
            return new Promise((resolve, reject) => {
              deleteById(record.userId)
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
          getRoleByUser(record.userId)
            .then(res => {
              const data = fetchResult(res, false)
              const roleIds = []
              if (data) {
                data.forEach(d => {
                  if (d && d.roleId) {
                    roleIds.push(d.roleId)
                  }
                })
              }
              const form = { ...record }
              form.roleIds = roleIds
              this.model.form = form
              this.model.opType = 'update'
              this.model.visible = true
            })
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
