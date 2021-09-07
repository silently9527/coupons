<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <div class="table-operator" >
        <a-form-model ref="searchFromRef" layout="inline" :model="searchFrom" @submit="fetchList" @submit.native.prevent>
          <span v-if="$auth('loginLog:query')">
            <a-form-model-item prop="username">
              <a-input v-model="searchFrom.username" placeholder="用户名"/>
            </a-form-model-item>
            <a-form-model-item label="登录结果" prop="loginResult">
              <a-select v-model="searchFrom.loginResult" placeholder="登录结果" style="width: 120px">
                <a-select-option value="">
                  全部
                </a-select-option>
                <a-select-option value="1">
                  成功
                </a-select-option>
                <a-select-option value="2">
                  失败
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
        </a-form-model>

      </div>
      <div class="table-page-search-wrapper">
        <a-table
          :columns="columns"
          :row-key="record => record.id"
          :data-source="data"
          :pagination="pagination"
          :loading="loading"
          @change="handleTableChange"
          :transformCellText="transformCellText"
          size="middle"
        >
          <span slot="loginResult" slot-scope="loginResult">
            <a-tag color="cyan" v-if="loginResult === 1">成功</a-tag>
            <a-tag color="red" v-if="loginResult === 2">失败</a-tag>
          </span>
        </a-table>
      </div>
    </a-card>
  </page-header-wrapper>
</template>

<script>
  import { STable } from '@/components'
  import { getPageList } from '@/api/login-log'
  import { fetchResult } from '@/utils/fetchUtil'
  import { transformCellText } from '@/utils/tableUtils'
  const pageSize = 10

  const columns = [
    {
      title: '用户名',
      dataIndex: 'username'
    },
    {
      title: '登录时间',
      dataIndex: 'gmtLoginTime'
    },
    {
      title: '登录ip',
      dataIndex: 'loginIp'
    },
    {
      title: '登录地址',
      dataIndex: 'loginAddress',
      ellipsis: true
    },
    {
      title: '登录结果',
      dataIndex: 'loginResult',
      ellipsis: true,
      scopedSlots: { customRender: 'loginResult' }
    },
    {
      title: '失败原因',
      dataIndex: 'loginFailureMsg',
      ellipsis: true
    }
  ]

  export default {
    name: 'LoginLogManage',
    components: {
      STable
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
          username: '',
          loginResult: null
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
        if (!this.$auth('loginLog:query')) {
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
