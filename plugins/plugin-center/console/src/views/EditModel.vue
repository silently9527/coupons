<template>
  <a-modal
    :visible="visible"
    :title="opType==='add'? '新增插件' : '修改插件'"
    :maskClosable="false"
    :width="640"
    :confirmLoading="loading"
    @ok="ok"
    @cancel="cancel"
    okText="确认"
    cancelText="取消"
  >
    <a-spin :spinning="loading">
      <a-form-model
        ref="ruleForm"
        :model="form"
        :rules="rules"
        :label-col="formLayout.labelCol"
        :wrapper-col="formLayout.wrapperCol"
      >
        <a-form-model-item label="插件名称" prop="pluginName">
          <a-input v-model="form.pluginName" />
        </a-form-model-item>
        <a-form-model-item label="图标" prop="icon">
          <a-input v-model="form.icon" />
        </a-form-model-item>
        <a-form-model-item label="作者" prop="author">
          <a-input v-model="form.author" />
        </a-form-model-item>
        <a-form-model-item label="版本" prop="version">
          <a-input v-model="form.version" />
        </a-form-model-item>
        <a-form-model-item label="密码" prop="password">
          <a-input type="password" v-model="form.password" />
        </a-form-model-item>
        <a-form-model-item label="价格" prop="author">
          <a-input v-model="form.price"/>
        </a-form-model-item>
        <a-form-model-item label="下载地址" prop="downloadUrl">
          <a-input v-model="form.downloadUrl" />
        </a-form-model-item>
        <a-form-model-item label="文档地址" prop="docUrl">
          <a-input v-model="form.docUrl"/>
        </a-form-model-item>
        <a-form-model-item label="描述" prop="description">
          <a-input type="textarea" v-model="form.description"/>
        </a-form-model-item>

        <a-form-model-item label="二维码地址" prop="qrcode">
          <a-input v-model="form.qrcode"/>
        </a-form-model-item>
        <a-form-model-item label="提示" prop="remark">
          <a-input type="textarea" v-model="form.remark"/>
        </a-form-model-item>
      </a-form-model>
    </a-spin>
  </a-modal>
</template>

<script>
  export default {
    name: 'EditModel',
    components: {},
    props: {
      visible: {
        type: Boolean,
        required: true,
        default: () => false
      },
      fromData: {
        type: Object,
        required: false,
        default: () => {}
      },
      opType: {
        type: String,
        required: false,
        default: () => null
      },
      loading: {
        type: Boolean,
        default: () => false
      }
    },
    created () {},
    data () {
      return {
        formLayout: {
          labelCol: {
            span: 6
          },
          wrapperCol: {
            span: 14
          }
        },
        form: {},
        rules: {
          pluginName: [
            { required: true, message: '插件名称不能为空', trigger: 'blur' }
          ],
          author: [
            { required: true, message: '作者不能为空', trigger: 'blur' }
          ],
          version: [
            { required: true, message: '版本不能为空', trigger: 'blur' }
          ],
          price: [
            { required: true, message: '价格不能为空', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '密码不能为空', trigger: 'blur' }
          ],
          downloadUrl: [
            { required: true, message: '下载地址不能为空', trigger: 'blur' }
          ],
          docUrl: [
            { required: true, message: '文档地址不能为空', trigger: 'blur' }
          ],
          description: [
            { required: true, message: '描述不能为空', trigger: 'blur' }
          ],
          qrcode: [
            { required: true, message: '二维码不能为空', trigger: 'blur' }
          ],
          remark: [
            { required: true, message: '二维码不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      ok () {
        this.$refs.ruleForm.validate(valid => {
          if (valid) {
            this.$emit('ok', this.opType, { ...this.form })
            this.$refs.ruleForm.resetFields()
          } else {
            return false
          }
        })
      },
      cancel () {
        this.form = {}
        this.$emit('cancel')
      }
    },
    watch: {
      fromData () {
        if (this.opType === 'update') {
          this.form = { ...this.fromData }
        }
      }
    }
  }
</script>
