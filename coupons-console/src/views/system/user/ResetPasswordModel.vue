<template>
  <a-modal
    :visible="visible"
    :title="`重置${userName}密码`"
    :maskClosable="false"
    :width="640"
    :confirmLoading="confirmLoading"
    @ok="ok"
    @cancel="cancel"
    okText="确认"
    cancelText="取消"
  >
    <a-form-model
      ref="ruleForm"
      :model="form"
      :rules="rules"
      :label-col="formLayout.labelCol"
      :wrapper-col="formLayout.wrapperCol"
    >
      <a-form-model-item label="密码" prop="password">
        <a-input type="password" v-model="form.password" />
      </a-form-model-item>
      <a-form-model-item label="确认密码" prop="confirmPassword">
        <a-input type="password" v-model="form.confirmPassword" />
      </a-form-model-item>
    </a-form-model>
  </a-modal>
</template>

<script>
  import { FormModel } from 'ant-design-vue'
  import { fetchResult } from '@/utils/fetchUtil'
  import { password } from '@/utils/password'
  import { resetPassword } from '@/api/user'
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
      userId: {
        type: String,
        required: true,
        default: () => ''
      },
      userName: {
        type: String,
        required: true,
        default: () => ''
      }
    },
    created () {
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
        form: {
          password: '',
          confirmPassword: ''
        },
        rules: {
          password: [
            { required: true, message: '密码不能为空', trigger: 'blur' },
            { min: 6, message: '密码长度不能小于6位', trigger: 'blur' }
          ],
          confirmPassword: [
            { required: true, message: '确认密码不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      ok () {
        this.$refs.ruleForm.validate(valid => {
          if (valid) {
            const { password, confirmPassword } = this.form
            if (password !== confirmPassword) {
              this.$notification['error']({
                message: '提示',
                description: '两次密码输入不一致',
                duration: 2
              })
              return false
            }
            this.fetchResetPassword(password)
          }
        })
      },
      cancel () {
        this.$refs.ruleForm.resetFields()
        this.$emit('close')
      },
      fetchResetPassword (pw) {
        const fromData = {
          userId: this.userId,
          password: password.encryption(pw)
        }
        this.confirmLoading = true
        resetPassword(fromData)
          .then(res => {
            if (fetchResult(res)) {
              this.$refs.ruleForm.resetFields()
              this.$emit('close')
            }
            this.confirmLoading = false
          })
      }
    }
  }
</script>
