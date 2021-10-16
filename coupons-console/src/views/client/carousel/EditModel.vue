<template>
  <a-modal
    :visible="visible"
    :title="opType==='add'? '新增' : '修改'"
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
        <a-form-model-item label="标题" prop="title">
          <a-input v-model="form.title"/>
        </a-form-model-item>
        <a-form-model-item label="图片地址" prop="image">
          <a-input v-model="form.image"/>
        </a-form-model-item>
        <a-form-model-item label="url" prop="url">
          <a-input v-model="form.url"/>
        </a-form-model-item>
        <a-form-model-item label="url类型" prop="urlType">
          <a-radio-group v-model="form.urlType">
            <a-radio value="H5">
              H5
            </a-radio>
            <a-radio value="Native">
              Native
            </a-radio>
          </a-radio-group>
        </a-form-model-item>
        <a-form-model-item label="Tab排序" prop="sorted">
          <a-input-number v-model="form.sorted" />
        </a-form-model-item>
      </a-form-model>
    </a-spin>
  </a-modal>
</template>

<script>
import {FormModel} from 'ant-design-vue'
import {fetchResult} from '@/utils/fetchUtil'
import {add, update} from '@/api/carousel'

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
          span: 7
        },
        wrapperCol: {
          span: 14
        }
      },
      confirmLoading: false,
      form: {},
      rules: {
        title: [
          {required: true, message: '标题不能为空', trigger: 'blur'},
          {max: 50, message: '最大长度50字符', trigger: 'blur'}
        ],
        image: [
          {required: true, message: '图片不能为空', trigger: 'blur'},
          {max: 1000, message: '最大长度1000字符', trigger: 'blur'}
        ],
        url: [
          {required: true, message: 'url不能为空', trigger: 'blur'},
          {max: 1000, message: '最大长度1000字符', trigger: 'blur'}
        ],
        urlType: [
          {required: true, message: 'url类型不能为空', trigger: 'blur'}
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
