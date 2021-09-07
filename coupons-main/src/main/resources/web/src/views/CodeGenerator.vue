<template>
  <div class="home">
    <a-table
      :columns="columns"
      :row-key="record => record.id"
      :data-source="data"
      size="middle"
    >
    </a-table>
  </div>
</template>

<script>
// @ is an alias to /src
import { getPageList } from '@/api/codeGenerator'

const pageSize = 10

const columns = [
  {
    title: 'id',
    dataIndex: 'id',
    width: 80
  },
  {
    title: '名称',
    dataIndex: 'name',
    width: 80
  }
]

export default {
  name: 'CodeGenerator',
  components: {
  },
  data(){
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
      }
    }
  },
  created () {
    this.fetchList()
  },
  methods: {
    fetchList(params = {}) {
      params.pageSize = pageSize
      params.currentPage = this.pagination.current
      this.loading = true
      getPageList(params)
        .then(res => {
          const pagination = {...this.pagination}
          pagination.total = res.data.total
          this.data = res.data.records
          this.pagination = pagination
          this.loading = false
        })
    }
  }
}
</script>
