<template>
  <a-card :bordered="false">
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
</template>

<script>
  import { STable } from '@/components'
  import { getPageListOfCurrent } from '@/api/login-log'
  import { fetchResult } from '@/utils/fetchUtil'
  import { transformCellText } from '@/utils/tableUtils'
  const pageSize = 10

  const columns = [
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
        params.pageSize = pageSize
        params.currentPage = this.pagination.current
        this.loading = true
        getPageListOfCurrent(params)
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
