<template>
  <a-modal
    :visible="visible"
    :title="opType==='add'? '新增Route' : '修改Route'"
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
        <a-form-model-item label="页面路径" prop="path">
          <a-input v-model="form.path" />
        </a-form-model-item>
        <a-form-model-item label="样式" prop="style">
          <a-input v-model="form.style" type="textarea" style="height: 150px" />
        </a-form-model-item>
      </a-form-model>
    </a-spin>
  </a-modal>
</template>

<script>
  import { FormModel } from 'ant-design-vue'
  import { fetchResult } from '@/utils/fetchUtil'
  import { add, update } from '@/api/route'

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
    },
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
        confirmLoading: false,
        form: {},
        rules: {
          path: [
            { required: true, message: '页面路径不能为空', trigger: 'blur' },
            { max: 200, message: '最大长度200字符', trigger: 'blur' }
          ],
          style: [
            { max: 2000, message: '最大长度2000字符', trigger: 'blur' }
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
        this.form = {}
        this.$emit('cancel')
      },
      fetchAdd () {
        const fromData = { ...this.form }
        this.confirmLoading = true
        add(fromData)
          .then(res => {
            if (fetchResult(res)) {
              this.form = {}
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
              this.form = {}
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
