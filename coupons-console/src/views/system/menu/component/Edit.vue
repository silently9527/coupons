<template>
  <div>
    <a-modal
      :visible="visible"
      :title="opType==='add'? '新增菜单' : '修改菜单'"
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
          <a-form-model-item label="父菜单" prop="parentId">
            <a-tree-select
              v-model="form.parentId"
              style="width: 100%"
              :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
              :tree-data="menuTree"
              :replaceFields="menuTreeReplaceFields"
              placeholder="选择父菜单, 如果为顶级菜单, 则可不选"
              tree-default-expand-all
              allowClear
            >
            </a-tree-select>
          </a-form-model-item>
          <a-form-model-item label="类型" prop="type">
            <a-radio-group v-model="form.type" name="type" :defaultValue="1">
              <a-radio :value="1">
                目录
              </a-radio>
              <a-radio :value="2">
                菜单
              </a-radio>
              <a-radio :value="3">
                权限
              </a-radio>
            </a-radio-group>
          </a-form-model-item>
          <a-form-model-item label="名称" prop="title">
            <a-input v-model="form.title" />
          </a-form-model-item>
          <a-form-model-item label="权限" prop="permissions">
            <a-input v-model="form.permissions" />
          </a-form-model-item>
          <a-form-model-item label="排序" prop="orderNum">
            <a-input-number v-model="form.orderNum" />
          </a-form-model-item>
          <a-form-model-item label="Vue组件" prop="component" v-if="form.type!==3">
            <a-input v-model="form.component" />
          </a-form-model-item>
          <a-form-model-item label="路径" prop="path" v-if="form.type!==3">
            <a-input v-model="form.path" />
          </a-form-model-item>
          <a-form-model-item label="图标" prop="icon">
            <a-input v-model="form.icon" />
          </a-form-model-item>
        </a-form-model>
      </a-spin>
    </a-modal>
  </div>
</template>

<script>
  import { TreeSelect } from 'ant-design-vue'
  import { add, getNavTree, update } from '@/api/menu'
  import { fetchResult } from '@/utils/fetchUtil'
  const DEFAULT_FROM = {
    orderNum: 1000,
    type: 1
  }
  export default {
    name: 'Edit',
    components: {
      ATreeSelect: TreeSelect
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
            span: 4
          },
          wrapperCol: {
            span: 18
          }
        },
        confirmLoading: false,
        form: { ...DEFAULT_FROM },
        rules: {
          title: [
            { required: true, message: '名称不能为空', trigger: 'blur' },
            { max: 12, message: '名称不能超过12位', trigger: 'blur' }
          ],
          permissions: [
            { required: true, message: '权限不能为空', trigger: 'blur' },
            { max: 256, message: '权限不能超过256位', trigger: 'blur' }
          ],
          orderNum: [
            { required: true, message: '排序不能为空', trigger: 'blur' }
          ],
          type: [
            { required: true, message: '权限类型不能为空', trigger: 'blur' }
          ]
        },
        menuTree: undefined,
        menuTreeReplaceFields: {
          title: 'title',
          key: 'menuId',
          value: 'menuId'
        }
      }
    },
    created () {
      this.fetchMenuTree()
    },
    methods: {
      fetchMenuTree () {
        getNavTree(false)
          .then(res => {
            this.menuTree = fetchResult(res, false)
          })
      },
      ok () {
        this.$refs.ruleForm.validate(valid => {
          if (valid) {
            const { opType } = this
            if (opType === 'add') {
              this.fetchAdd()
            } else if (opType === 'update') {
              this.fetchUpdate()
            }
          } else {
            return false
          }
        })
      },
      fetchAdd () {
        const fromData = { ...this.form }
        this.confirmLoading = true
        add(fromData)
          .then(res => {
            if (fetchResult(res)) {
              this.form = { ...DEFAULT_FROM }
              this.fetchMenuTree()
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
              this.fetchMenuTree()
              this.$emit('ok', true)
            }
            this.confirmLoading = false
          })
      },
      cancel () {
        this.form = { ...DEFAULT_FROM }
        this.$emit('cancel')
      }
    },
    watch: {
      fromData () {
        this.form.type = 1
        if (this.opType === 'update') {
          this.form = { ...this.fromData }
          const parentId = this.fromData.parentId
          if (parentId === '0') {
            this.form.parentId = undefined
          }
        }
      }
    }
  }
</script>
