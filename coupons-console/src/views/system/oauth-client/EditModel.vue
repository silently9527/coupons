<template>
  <a-modal
    :visible="visible"
    :title="opType==='add'? '新增授权客户端' : '修改授权客户端'"
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
        :rules="opType === 'add'? rulesAdd:rulesUpdate"
        :label-col="formLayout.labelCol"
        :wrapper-col="formLayout.wrapperCol"
      >
        <a-form-model-item label="客户端id" prop="clientId">
          <a-input v-model="form.clientId" :disabled="opType!=='add'"/>
        </a-form-model-item>
        <a-form-model-item label="名称" prop="name">
          <a-input v-model="form.name" />
        </a-form-model-item>
        <a-form-model-item label="秘钥" prop="clientSecret">
          <a-input v-model="form.clientSecret" />
        </a-form-model-item>
        <a-form-model-item label="访问Token时效" prop="accessTokenValidity">
          <a-input-number v-model="form.accessTokenValidity" :min="60"/> 秒
        </a-form-model-item>
        <a-form-model-item label="刷新Token时效" prop="refreshTokenValidity">
          <a-input-number v-model="form.refreshTokenValidity" :min="60"/> 秒
        </a-form-model-item>
        <a-form-model-item label="授权模式" prop="authorizedGrantTypes">
          <a-checkbox-group v-model="form.authorizedGrantTypes">
            <a-checkbox value="authorization_code" name="authorizedGrantTypes">
              授权码
            </a-checkbox>
            <a-checkbox value="password" name="authorizedGrantTypes">
              密码
            </a-checkbox>
            <a-checkbox value="client_credentials" name="authorizedGrantTypes">
              客户端
            </a-checkbox>
            <a-checkbox value="refresh_token" name="authorizedGrantTypes">
              刷新Token
            </a-checkbox>
          </a-checkbox-group>
        </a-form-model-item>
        <a-form-model-item label="权限范围" prop="scope">
          <a-input v-model="form.scope" />
        </a-form-model-item>
        <a-form-model-item label="资源标识" prop="resourceIds">
          <a-input v-model="form.resourceIds" type="textarea" placeholder="多个逗号分隔"/>
        </a-form-model-item>
        <a-form-model-item label="权限标识" prop="authorities">
          <a-input v-model="form.authorities" type="textarea" placeholder="多个逗号分隔"/>
        </a-form-model-item>
        <a-form-model-item label="描述" prop="description">
          <a-input v-model="form.description" type="textarea" />
        </a-form-model-item>
      </a-form-model>
    </a-spin>
  </a-modal>
</template>

<script>
  import { FormModel } from 'ant-design-vue'
  import { add, update } from '@/api/oauth-client'
  import { fetchResult } from '@/utils/fetchUtil'
  const commonRules = {
    clientId: [
      { required: true, message: '客户端id不能为空', trigger: 'blur' }
    ],
    name: [
      { required: true, message: '名称不能为空', trigger: 'blur' }
    ],
    accessTokenValidity: [
      { required: true, message: '访问Token时效不能为空', trigger: 'blur' }
    ],
    refreshTokenValidity: [
      { required: true, message: '刷新Token时效不能为空', trigger: 'blur' }
    ],
    authorizedGrantTypes: [
      { required: true, message: '授权模式不能为空', trigger: 'blur' }
    ],
    scope: [
      { required: true, message: '权限范围不能为空', trigger: 'blur' }
    ]
  }
  const DEFAULT_FROM = {
    clientSecret: '',
    accessTokenValidity: 60,
    refreshTokenValidity: 60,
    scope: 'all'
  }
  export default {
    name: 'EditModel',
    components: {
      FormModel
    },
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
    data () {
      return {
        formLayout: {
          labelCol: {
            span: 5
          },
          wrapperCol: {
            span: 18
          }
        },
        form: { ...DEFAULT_FROM },
        rulesAdd: {
          ...commonRules,
          clientSecret: [
            { required: true, message: '秘钥不能为空', trigger: 'blur' }
          ]
        },
        rulesUpdate: commonRules
      }
    },
    methods: {
      ok () {
        this.$refs.ruleForm.validate(valid => {
          if (valid) {
            if (this.opType === 'add') {
              this.fetchAdd()
            } else if (this.opType === 'update') {
              this.fetchUpdate()
            }
          } else {
            return false
          }
        })
      },
      cancel () {
        this.form = { ...DEFAULT_FROM }
        this.$emit('cancel')
      },
      fetchAdd () {
        const fromData = { ...this.form }
        this.confirmLoading = true
        add(fromData)
          .then(res => {
            if (fetchResult(res)) {
              this.form = { ...DEFAULT_FROM }
              this.$emit('ok', true)
            }
            this.confirmLoading = false
          })
      },
      fetchUpdate () {
        const fromData = { ...this.form }
        this.confirmLoading = true
        update(fromData)
          .then(res => {
            if (fetchResult(res)) {
              this.form = { ...DEFAULT_FROM }
              this.$emit('ok', true)
            }
            this.confirmLoading = false
          })
      }
    },
    watch: {
      fromData () {
        if (this.opType === 'update') {
          if (this.fromData) {
            this.fromData.clientSecret = ''
            const authorizedGrantTypes = this.fromData.authorizedGrantTypes
            if (typeof authorizedGrantTypes === 'string') {
              this.fromData.authorizedGrantTypes = authorizedGrantTypes.split(',')
            }
            this.form = { ...this.fromData }
          }
        }
      }
    }
  }
</script>
