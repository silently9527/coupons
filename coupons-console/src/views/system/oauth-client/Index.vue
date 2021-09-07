<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <div class="table-operator" >
        <a-form-model ref="searchFromRef" layout="inline" :model="searchFrom" @submit="fetchList" @submit.native.prevent>
          <span v-if="$auth('oauthClient:query')">
            <a-form-model-item prop="name">
              <a-input v-model="searchFrom.name" placeholder="名称"/>
            </a-form-model-item>
            <a-form-model-item>
              <a-button type="primary" html-type="submit">查询</a-button>
            </a-form-model-item>
            <a-form-model-item>
              <a-button type="primary" @click="()=> $refs.searchFromRef.resetFields()">重置</a-button>
            </a-form-model-item>
          </span>
          <a-form-model-item v-if="$auth('oauthClient:add')">
            <a-button type="primary" @click="showMode('add')">新增授权客户端</a-button>
          </a-form-model-item>
        </a-form-model>

      </div>
      <div class="table-page-search-wrapper">
        <a-table
          :columns="columns"
          :row-key="record => record.clientId"
          :data-source="data"
          :pagination="pagination"
          :loading="loading"
          @change="handleTableChange"
          :transformCellText="transformCellText"
          size="middle"
        >
          <span slot="authorizedGrantTypes" slot-scope="authorizedGrantTypes">
            <a-tag color="cyan" v-if="authorizedGrantTypes.indexOf('authorization_code')>-1">
              授权码
            </a-tag>
            <a-tag color="cyan" v-if="authorizedGrantTypes.indexOf('password')>-1">
              密码
            </a-tag>
            <a-tag color="cyan" v-if="authorizedGrantTypes.indexOf('client_credentials')>-1">
              客户端
            </a-tag>
            <a-tag color="cyan" v-if="authorizedGrantTypes.indexOf('refresh_token')>-1">
              刷新Token
            </a-tag>
          </span>
          <span slot="accessTokenValidity" slot-scope="accessTokenValidity">
            {{ accessTokenValidity }}秒
          </span>
          <span slot="refreshTokenValidity" slot-scope="refreshTokenValidity">
            {{ refreshTokenValidity }}秒
          </span>
          <p slot="expandedRowRender" slot-scope="record" style="margin: 0">
            <span>权限标识: {{ record.authorities }}</span><br/>
            <span>资源标识: {{ record.resourceIds }}</span><br/>
            <span>scope: {{ record.scope }}</span><br/>
            <span>创建用户: {{ record.createUser }}</span><br/>
            <span>创建时间: {{ record.gmtCreated }}</span><br/>
            <span>修改用户: {{ record.modifiedUser }}</span><br/>
            <span>修改时间: {{ record.gmtModified }}</span><br/>
          </p>
          <span slot="action" slot-scope="record">
            <a-tag v-if="$auth('oauthClient:update')" color="purple" @click="showMode('update', record)" style="cursor:pointer">
              修改
            </a-tag>
            <a-tag v-if="$auth('oauthClient:delete')" color="red" @click="removeById(record)" style="cursor:pointer">
              删除
            </a-tag>
          </span>
        </a-table>
      </div>
    </a-card>
    <edit-model
      v-if="$auth('oauthClient:update') || $auth('oauthClient:add')"
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
  import EditModel from './EditModel'
  import { getPageList, deleteById } from '@/api/oauth-client'
  import { fetchResult } from '@/utils/fetchUtil'
  import { transformCellText } from '@/utils/tableUtils'
  const pageSize = 10

  const columns = [
    {
      title: '名称',
      dataIndex: 'name'
    },
    {
      title: '客户端id',
      dataIndex: 'clientId'
    },
    {
      title: '授权模式',
      dataIndex: 'authorizedGrantTypes',
      scopedSlots: { customRender: 'authorizedGrantTypes' },
      width: 265
    },
    {
      title: '访问时效',
      dataIndex: 'accessTokenValidity',
      scopedSlots: { customRender: 'accessTokenValidity' }
    },
    {
      title: '刷新时效',
      dataIndex: 'refreshTokenValidity',
      scopedSlots: { customRender: 'refreshTokenValidity' }
    },
    {
      title: '修改时间',
      dataIndex: 'gmtModified'
    },
    {
      title: '描述',
      dataIndex: 'description',
      ellipsis: true
    },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
      width: 120
    }
  ]

  export default {
    name: 'OauthClientManage',
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
        searchFrom: {
          name: ''
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
        if (!this.$auth('oauthClient:query')) {
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
      removeById (record) {
        const tag = this
        this.$confirm({
          title: '提示',
          content: `确认删除客户端授权 [${record.name}]?`,
          confirmLoading: true,
          okText: '确认',
          cancelText: '取消',
          onOk () {
            return new Promise((resolve, reject) => {
              deleteById(record.clientId)
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
