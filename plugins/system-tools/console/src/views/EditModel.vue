<template>
  <a-modal
    :visible="visible"
    :title="opType==='add'? '新增配置' : '修改配置'"
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
        <a-form-model-item label="名称" prop="name">
          <a-input v-model="form.name" />
        </a-form-model-item>
        <a-form-model-item label="驱动名称" prop="driverName">
          <a-input v-model="form.driverName" />
        </a-form-model-item>
        <a-form-model-item label="url" prop="url">
          <a-input v-model="form.url" />
        </a-form-model-item>
        <a-form-model-item label="用户名" prop="username">
          <a-input v-model="form.username" />
        </a-form-model-item>
        <a-form-model-item label="密码" prop="password">
          <a-input type="password" v-model="form.password" />
        </a-form-model-item>
        <a-form-model-item label="包名" prop="basePackageName">
          <a-input v-model="form.basePackageName" />
        </a-form-model-item>
        <a-form-model-item label="表名" prop="tableNames">
          <a-input v-model="form.tableNames" placeholder="(全部生成则为*, 多个用逗号分隔)"/>
        </a-form-model-item>
        <a-form-model-item label="作者" prop="author">
          <a-input v-model="form.author"/>
        </a-form-model-item>
        <a-form-model-item label="描述" prop="description">
          <a-input type="textarea" v-model="form.description"/>
        </a-form-model-item>
      </a-form-model>
    </a-spin>
  </a-modal>
</template>

<script>
  const defaultForm = {
    driverName: 'com.mysql.cj.jdbc.Driver',
    tableNames: '*'
  }
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
            span: 4
          },
          wrapperCol: {
            span: 14
          }
        },
        form: defaultForm,
        rules: {
          name: [
            { required: true, message: '名称不能为空', trigger: 'blur' }
          ],
          driverName: [
            { required: true, message: '驱动名称不能为空', trigger: 'blur' }
          ],
          url: [
            { required: true, message: 'url不能为空', trigger: 'blur' }
          ],
          username: [
            { required: true, message: '用户名不能为空', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '密码不能为空', trigger: 'blur' }
          ],
          basePackageName: [
            { required: true, message: '生成的包名不能为空', trigger: 'blur' }
          ],
          tableNames: [
            { required: true, message: '生成的表名不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      handleChange (value) {
        this.roleIds = value
        this.form.roleIds = value
      },
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
        this.form = defaultForm
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
