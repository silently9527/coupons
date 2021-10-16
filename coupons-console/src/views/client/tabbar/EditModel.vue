<template>
  <a-modal
    :visible="visible"
    :title="opType==='add'? '新增TabBar' : '修改TabBar'"
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
        <a-form-model-item label="Tab名称" prop="text">
          <a-input v-model="form.text" />
        </a-form-model-item>
        <a-form-model-item label="页面路径" prop="pagePath">
          <a-input v-model="form.pagePath" />
        </a-form-model-item>
        <a-form-model-item label="Tab图标" prop="iconPath">
          <a-input v-model="form.iconPath" />
        </a-form-model-item>
        <a-form-model-item label="选中时Tab图标" prop="selectedIconPath">
          <a-input v-model="form.selectedIconPath" />
        </a-form-model-item>
        <a-form-model-item label="Tab排序" prop="sorted">
          <a-input-number v-model="form.sorted" />
        </a-form-model-item>
        <a-form-model-item label="备注" prop="remark">
          <a-input v-model="form.remark" />
        </a-form-model-item>
      </a-form-model>
    </a-spin>
  </a-modal>
</template>

<script>
  import { FormModel } from 'ant-design-vue'
  import { fetchResult } from '@/utils/fetchUtil'
  import { add, update } from '@/api/tabbar'

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
            span: 7
          },
          wrapperCol: {
            span: 14
          }
        },
        confirmLoading: false,
        form: {},
        rules: {
          pagePath: [
            { required: true, message: '页面路径不能为空', trigger: 'blur' },
            { max: 200, message: '最大长度200字符', trigger: 'blur' }
          ],
          iconPath: [
            { required: true, message: '图标不能为空', trigger: 'blur' },
            { max: 200, message: '最大长度200字符', trigger: 'blur' }
          ],
          selectedIconPath: [
            { required: true, message: '选中图标不能为空', trigger: 'blur' },
            { max: 200, message: '最大长度200字符', trigger: 'blur' }
          ],
          sorted: [
            {
              trigger: 'blur',
              required: true,
              validator: function (rule, value, callback) {
                if (!value) {
                  callback(new Error('排序不能为空'))
                  return
                }
                if (value <= 0 || value > 99) {
                  callback(new Error('排序取值范围0～99'))
                  return
                }
                callback()
              }
            }
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
