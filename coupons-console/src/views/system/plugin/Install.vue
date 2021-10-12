<template>
  <div>
    <a-modal
      :visible="visible"
      title="安装插件"
      :maskClosable="false"
      :width="640"
      :confirmLoading="loading"
      @ok="handleUpload"
      @cancel="handleCancel"
      okText="安装"
      cancelText="取消"
    >
      <a-spin :spinning="loading">
        <div class="clearfix">
          <a-upload-dragger
            :file-list="zipFile"
            :remove="handleRemove"
            :before-upload="beforeUpload"
            :multiple="false"
            accept=".zip">
            <a-icon type="upload" /> 选择zip插件包
          </a-upload-dragger>
          <br/>
        </div>
      </a-spin>
    </a-modal>
  </div>
</template>

<script>
  import { install } from '@/api/plugin'
  import { fetchResult } from '@/utils/fetchUtil'
  export default {
    name: 'Install',
    props: {
      visible: {
        type: Boolean,
        required: true,
        default: () => false
      },
      loading: {
        type: Boolean,
        default: () => false
      }
    },
    data () {
      return {
        form: {},
        formLayout: {
          labelCol: {
            xs: { span: 24 },
            sm: { span: 7 }
          },
          wrapperCol: {
            xs: { span: 24 },
            sm: { span: 13 }
          }
        },
        zipFile: [],
        uploading: false
      }
    },
    methods: {
      handleRemove (file) {
        const index = this.zipFile.indexOf(file)
        const newFileList = this.zipFile.slice()
        newFileList.splice(index, 1)
        this.zipFile = newFileList
      },
      beforeUpload (file) {
        const fileType = file.name.split('.').pop().toLowerCase()
        const check = fileType && fileType === 'zip'
        if (!check) {
          this.$notification['error']({
            message: '错误',
            description: 'zip插件包格式错误: ' + file.name,
            duration: 2
          })
          return false
        }
        this.zipFile = [ file ]
        return false
      },
      handleUpload () {
        const { zipFile } = this
        if (zipFile.length === 0) {
          this.$notification['error']({
            message: '错误',
            description: 'zip插件包不能为空',
            duration: 2
          })
          return
        }

        const formData = new FormData()
        formData.append('pluginZipFile', zipFile[0])

        this.uploading = true

        install(formData)
          .then(res => {
            fetchResult(res)
            this.zipFile = []
            this.uploading = false
            this.$emit('ok')
          })
      },
      handleCancel () {
        this.zipFile = []
        this.$emit('cancel')
      }
    }
  }
</script>
