<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <div class="table-operator">
        <a-form-model ref="searchFromRef" layout="inline">
          <a-form-model-item>
            <a-button type="primary" icon="download" @click="download">下载源码</a-button>
          </a-form-model-item>
          <a-form-model-item>
            <a-tag color="red">修改源码需要重新打包客户端！</a-tag>
          </a-form-model-item>
        </a-form-model>
      </div>
      <div class="code-layout">
        <a-table
          :scroll="{ y: 650 }"
          ref="fileTree"
          :loading="loading"
          :row-key="record => record.path"
          class="file-tree"
          size="small"
          :pagination="false"
          :expandRowByClick="true"
          :columns="columns"
          :data-source="data"
          :customRow="click"
        />
        <div class="cm-container">
          <div class="file-content">
            <codemirror v-model="content" :options="editorOption" ref="myEditor"></codemirror>
          </div>
          <a-button type="primary" icon="save" @click="saveFile">保存</a-button>
        </div>
      </div>
    </a-card>
  </page-header-wrapper>
</template>

<script>
import {codemirror} from 'vue-codemirror-lite'
import {getFiles, readFileContext, fileSave, downloadCode} from '@/api/code'
import {fetchResult} from '@/utils/fetchUtil'
import {convertRes2Blob} from '@/utils/util'

const columns = [
  {
    title: '文件列表',
    dataIndex: 'name',
    key: 'name',
  }
];

export default {
  name: 'CodeManage',
  components: {
    codemirror
  },
  data() {
    return {
      loading: false,
      data: [],
      columns,
      editorOption: {
        mode: 'javascript',
        tabSize: 2,
        lineNumbers: true,
        lineWrapping: true,
        viewportMargin: Infinity,
      },
      selectFile: {},
      content: ''
    }
  },
  created() {
    this.fetchFiles()
  },
  mounted() {
    this.$refs.myEditor.editor.setSize('auto', 650)
  },
  methods: {
    download() {
      downloadCode().then(res => {
        convertRes2Blob(res, 'coupons-client.zip')
      })
    },
    click(record, index) {
      return {
        on: {
          click: () => {
            if (record.dir) {
              return;
            }
            this.selectFile = record
            readFileContext(record.path).then(res => {
              if (fetchResult(res, false, true)) {
                this.content = res.data
              }
            })
          }
        }
      }
    },
    fetchFiles() {
      this.loading = true
      getFiles().then(res => {
        if (fetchResult(res, false, true)) {
          this.data = res.data
        }
        this.loading = false
      })
    },
    saveFile() {
      fileSave(this.selectFile.path, this.content).then(res => {
        fetchResult(res, true, true)
      })
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

.code-layout {
  display: flex;

  .file-tree {
    width: 350px;
  }

  .cm-container {
    margin-left: 20px;
    flex: 1;

    .file-content {
      border: #e8e8e8 solid 1px;
      margin-bottom: 10px
    }
  }
}

</style>
