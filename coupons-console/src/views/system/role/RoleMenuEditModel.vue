<template>
  <a-modal
    :visible="visible"
    title="角色权限"
    maskClosable
    :width="640"
    :confirmLoading="loading"
    @ok="ok"
    @cancel="cancel"
    okText="确认"
    cancelText="取消"
  >
    <a-spin :spinning="loadingTree">
      <a-tree
        v-model="menuIds"
        style="width: 100%"
        :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
        :tree-data="menuTree"
        checkable
        :replaceFields="menuTreeReplaceFields"
        :defaultExpandAll="true"
        @check="selectMenu"
      >
      </a-tree>
    </a-spin>
  </a-modal>
</template>

<script>
  import { getRoleMenuList, updateRoleMenu } from '@/api/role'
  import { Tree } from 'ant-design-vue'
  import { fetchResult } from '@/utils/fetchUtil'
  export default {
    name: 'RoleMenuEditModel',
    components: {
      ATree: Tree
    },
    props: {
      visible: {
        type: Boolean,
        required: true,
        default: () => false
      },
      roleId: {
        type: String,
        required: false,
        default: () => ''
      },
      loading: {
        type: Boolean,
        default: () => false
      }
    },
    data () {
      return {
        loadingTree: false,
        menuIds: [],
        menuTree: [],
        menuTreeReplaceFields: {
          title: 'title',
          key: 'menuId'
        },
        parentMenuIds: []
      }
    },
    mounted () {
      this.fetchRoleMenu()
    },
    methods: {
      fetchRoleMenu () {
        this.loadingTree = true
        getRoleMenuList({
          roleId: this.roleId
        }).then(res => {
          const result = fetchResult(res, false, true)
          if (result) {
            this.menuTree = result.menuTree
            this.menuIds = result.menuIds
          }
          this.loadingTree = false
        })
      },
      ok () {
        updateRoleMenu({
          roleId: this.roleId,
          menus: this.menuIds,
          parentMenus: this.parentMenuIds
        }).then(res => {
          if (fetchResult(res)) {
            this.$emit('cancel')
          }
        })
      },
      cancel () {
        this.$emit('cancel')
      },
      selectMenu (checkedKeys, info) {
        this.parentMenuIds = info.halfCheckedKeys
      }
    }
  }
</script>
