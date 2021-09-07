<template>
  <a-modal
    :visible="visible"
    title="修改密码"
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
        <a-form-model-item label="旧密码" prop="oldPassword">
          <a-input type="password" v-model="form.oldPassword" />
        </a-form-model-item>
        <a-form-model-item label="新密码" prop="newPassword">
          <a-input type="password" v-model="form.newPassword" />
        </a-form-model-item>
        <a-form-model-item label="确认新密码" prop="confirmNewPassword">
          <a-input type="password" v-model="form.confirmNewPassword" />
        </a-form-model-item>
      </a-form-model>
    </a-spin>
  </a-modal>
</template>

<script>
  import { FormModel } from 'ant-design-vue'
  import { updatePassword } from '@/api/user'
  import { fetchResult } from '@/utils/fetchUtil'
  import { password } from '@/utils/password'
  const TYPE = 'password'
  const defaultForm = {}
  export default {
    name: 'UpdatePasswordModel',
    components: {
      FormModel
    },
    props: {
      visible: {
        type: Boolean,
        required: true,
        default: () => false
      }
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
        loading: false,
        form: defaultForm,
        rules: {
          oldPassword: [
            { required: true, message: '旧密码不能为空', trigger: 'blur' }
          ],
          newPassword: [
            { required: true, message: '新密码不能为空', trigger: 'blur' },
            { min: 6, message: '密码长度不能小于6位', trigger: 'blur' }
          ],
          confirmNewPassword: [
            { required: true, message: '确认新密码不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      ok () {
        this.$refs.ruleForm.validate(valid => {
          if (valid) {
            const { oldPassword, newPassword, confirmNewPassword } = this.form
            if (newPassword !== confirmNewPassword) {
              this.$notification['error']({
                message: '提示',
                description: '新密码输入不一致',
                duration: 2
              })
              return
            }
            const from = {}
            from.oldPassword = password.encryption(oldPassword)
            from.newPassword = password.encryption(newPassword)
            from.confirmNewPassword = password.encryption(confirmNewPassword)
            updatePassword(from)
              .then(res => {
                if (fetchResult(res)) {
                  this.$refs.ruleForm.resetFields()
                  this.$emit('ok', TYPE)
                }
              })
          } else {
            return false
          }
        })
      },
      cancel () {
        this.$refs.ruleForm.resetFields()
        this.$emit('cancel', TYPE)
      }
    }
  }
</script>
