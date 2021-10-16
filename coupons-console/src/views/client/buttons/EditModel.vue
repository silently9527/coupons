<template>
  <a-modal
    :visible="visible"
    :title="opType==='add'? '新增按钮' : '修改按钮'"
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
        <a-form-model-item label="所属的页面名字" prop="page">
          <a-input v-model="form.page"/>
        </a-form-model-item>
        <a-form-model-item label="按钮编码" prop="buttonCode">
          <a-input v-model="form.buttonCode"/>
        </a-form-model-item>
        <a-form-model-item label="按钮文本" prop="text">
          <a-input v-model="form.text"/>
        </a-form-model-item>
        <a-form-model-item label="图标类型" prop="iconType">
          <a-radio-group v-model="form.iconType">
            <a-radio value="image">
              Image
            </a-radio>
            <a-radio value="icon">
              Icon
            </a-radio>
          </a-radio-group>
        </a-form-model-item>
        <a-form-model-item label="图标地址" prop="image">
          <a-input v-model="form.image"/>
        </a-form-model-item>
        <a-form-model-item label="按钮跳转地址" prop="url">
          <a-input v-model="form.url"/>
        </a-form-model-item>
        <a-form-model-item label="URL类型" prop="urlType">
          <a-radio-group v-model="form.urlType">
            <a-radio value="H5">
              H5
            </a-radio>
            <a-radio value="Native">
              Native
            </a-radio>
          </a-radio-group>
        </a-form-model-item>
        <a-form-model-item label="排序" prop="sorted">
          <a-input-number v-model="form.sorted"/>
        </a-form-model-item>
        <a-form-model-item label="备注" prop="remark">
          <a-input v-model="form.remark" type="textarea" style="height: 60px"/>
        </a-form-model-item>
      </a-form-model>
    </a-spin>
  </a-modal>
</template>

<script>
import {FormModel} from 'ant-design-vue'
import {fetchResult} from '@/utils/fetchUtil'
import {add, update} from '@/api/buttons'

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
      default: () => {
      }
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
  created() {
  },
  data() {
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
        page: [
          {required: true, message: '所属页面编码不能为空', trigger: 'blur'},
          {max: 50, message: '最大长度50字符', trigger: 'blur'}
        ],
        buttonCode: [
          {required: true, message: '按钮编码不能为空', trigger: 'blur'},
          {max: 50, message: '最大长度50字符', trigger: 'blur'}
        ],
        text: [
          {max: 20, message: '最大长度20字符', trigger: 'blur'}
        ],
        image: [
          {max: 500, message: '最大长度500字符', trigger: 'blur'}
        ],
        url: [
          {required: true, message: 'url不能为空', trigger: 'blur'},
          {max: 500, message: '最大长度500字符', trigger: 'blur'}
        ],
        iconType: [
          {required: true, message: '图标的类型不能为空', trigger: 'blur'}
        ],
        urlType: [
          {required: true, message: 'URL类型不能为空', trigger: 'blur'}
        ],
        sorted: [
          {required: true, message: '排序不能为空', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    ok() {
      this.$refs.ruleForm.validate(valid => {
        if (valid) {
          const {opType} = this
          if (opType === 'add') {
            this.fetchAdd()
          } else if (opType === 'update') {
            this.fetchUpdate()
          }
        }
        return false
      })
    },
    cancel() {
      this.form = {}
      this.$emit('cancel')
    },
    fetchAdd() {
      const fromData = {...this.form}
      this.confirmLoading = true
      console.log(1111)
      console.log(fromData)
      add(fromData)
        .then(res => {
          if (fetchResult(res)) {
            this.form = {}
            this.$emit('ok', true)
          }
          this.confirmLoading = false
        })
    },
    fetchUpdate() {
      const fromData = {...this.form}
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
    fromData() {
      if (this.opType === 'update') {
        this.form = {...this.fromData}
      }
    }
  }
}
</script>
