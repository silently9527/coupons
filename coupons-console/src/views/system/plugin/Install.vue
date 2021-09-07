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
            :file-list="jarList"
            :remove="jarHandleRemove"
            :before-upload="jarBeforeUpload"
            :multiple="false"
            accept=".jar">
            <a-icon type="upload" /> 选择Jar插件包
          </a-upload-dragger>
          <br/>
          <a-upload-dragger
            :file-list="configList"
            :remove="configHandleRemove"
            :before-upload="configBeforeUpload"
            :multiple="false"
            accept=".yml">
            <a-icon type="snippets" /> 选择当前插件的配置文件(可选)
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
        jarList: [],
        configList: [],
        uploading: false
      }
    },
    methods: {
      jarHandleRemove (file) {
        const index = this.jarList.indexOf(file)
        const newFileList = this.jarList.slice()
        newFileList.splice(index, 1)
        this.jarList = newFileList
      },
      jarBeforeUpload (file) {
        const fileType = file.name.split('.').pop().toLowerCase()
        const check = fileType && fileType === 'jar'
        if (!check) {
          this.$notification['error']({
            message: '错误',
            description: 'jar插件包格式错误: ' + file.name,
            duration: 2
          })
          return false
        }
        this.jarList = [ file ]
        return false
      },
      configHandleRemove (file) {
        const index = this.configList.indexOf(file)
        const newFileList = this.configList.slice()
        newFileList.splice(index, 1)
        this.configList = newFileList
      },
      configBeforeUpload (file) {
        const fileType = file.name.split('.').pop().toLowerCase()
        const check = fileType && fileType === 'yml'
        if (!check) {
          this.$notification['error']({
            message: '错误',
            description: '配置文件格式错误: ' + file.name,
            duration: 2
          })
          return false
        }
        this.configList = [ file ]
        return false
      },
      handleUpload () {
        const { jarList, configList } = this
        if (jarList.length === 0) {
          this.$notification['error']({
            message: '错误',
            description: 'jar插件包不能为空',
            duration: 2
          })
          return
        }

        const formData = new FormData()
        formData.append('pluginJarFile', jarList[0])
        if (configList && configList.length === 1) {
          formData.append('pluginConfigFile', jarList[0])
        }

        this.uploading = true

        install(formData)
          .then(res => {
            fetchResult(res)
            this.jarList = []
            this.configList = []
            this.uploading = false
            this.$emit('ok')
          })
      },
      handleCancel () {
        this.jarList = []
        this.configList = []
        this.$emit('cancel')
      }
    }
  }
</script>
