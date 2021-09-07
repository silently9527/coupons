<template>
  <a-modal
    :visible="visible"
    :title="opType==='add'? '新增用户' : '修改用户'"
    :maskClosable="false"
    :width="640"
    :confirmLoading="confirmLoading"
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
        <a-form-model-item label="角色" prop="roleIds">
          <a-select
            v-model="form.roleIds"
            placeholder="请选择角色"
            mode="multiple"
            notFoundContent="没有可以的角色"
            :showSearch="true">
            <a-select-option v-for="role in roles" :key="role.roleId" :value="role.roleId">
              {{ role.name }}
            </a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item label="名称" prop="name">
          <a-input v-model="form.name" />
        </a-form-model-item>
        <a-form-model-item label="用户名" prop="username">
          <a-input v-model="form.username" />
        </a-form-model-item>
        <a-form-model-item label="密码" prop="password" v-if="opType==='add'">
          <a-input type="password" v-model="form.password" />
        </a-form-model-item>
        <a-form-model-item label="状态" prop="status" v-if="opType==='add'">
          <a-radio-group v-model="form.status" name="status" defaultValue="1">
            <a-radio :value="1">
              启用
            </a-radio>
            <a-radio :value="0">
              禁用
            </a-radio>
          </a-radio-group>
        </a-form-model-item>
        <a-form-model-item label="手机号" prop="phone">
          <a-input v-model="form.phone" />
        </a-form-model-item>
        <a-form-model-item label="邮箱" prop="email">
          <a-input v-model="form.email" />
        </a-form-model-item>
      </a-form-model>
    </a-spin>
  </a-modal>
</template>

<script>
  import { FormModel } from 'ant-design-vue'
  import { fetchResult } from '@/utils/fetchUtil'
  import { password } from '@/utils/password'
  import { add, update, getEnableRoles } from '@/api/user'
  const DEFAULT_FROM = {
    roleIds: [],
    status: 1
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
    created () {
      getEnableRoles()
        .then(res => {
          this.roles = fetchResult(res, false)
        })
    },
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
        confirmLoading: false,
        roles: {},
        form: { ...DEFAULT_FROM },
        rules: {
          roleIds: [
            { required: true, message: '角色不能为空', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '名称不能为空', trigger: 'blur' },
            { max: 12, message: '名称不能超过12位', trigger: 'blur' }
          ],
          username: [
            { required: true, message: '用户名不能为空', trigger: 'blur' },
            { max: 16, message: '用户名不能超过16位', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '密码不能为空', trigger: 'blur' },
            { min: 6, message: '密码长度不能小于6位', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      ok () {
        this.$refs.ruleForm.validate(valid => {
          if (valid) {
            const { opType } = this
            if (opType === 'add') {
              this.fetchAdd()
            } else if (opType === 'update') {
              this.fetchUpdate()
            }
          }
          return false
        })
      },
      cancel () {
        this.form = { ...DEFAULT_FROM }
        this.$emit('cancel')
      },
      fetchAdd () {
        const fromData = { ...this.form }
        const sourcePassword = fromData.password
        if (sourcePassword) {
          fromData.password = password.encryption(sourcePassword)
        }
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
          this.form = { ...this.fromData }
        }
      }
    }
  }
</script>
