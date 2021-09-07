<template>
  <a-list
    itemLayout="horizontal"
  >
    <a-list-item>
      <a-list-item-meta>
        <a slot="title">修改密码</a>
        <span slot="description">
          <span class="security-list-description">修改当前密码</span>
        </span>
      </a-list-item-meta>
      <template>
        <a @click="showMode('password')">修改</a>
      </template>

    </a-list-item>
    <update-password-model
      ref="passwordModel"
      :visible="passwordModel.visible"
      @cancel="modelHandleCancel"
      @ok="modelHandleOk"
    />
  </a-list>
</template>

<script>
  import UpdatePasswordModel from './model/UpdatePasswordModel'
  export default {
    components: {
      UpdatePasswordModel
    },
    data () {
      return {
        passwordModel: {
          visible: false
        }
      }
    },
    methods: {
      showMode (type) {
        console.log(type)
        if (type === 'password') {
          this.passwordModel.visible = true
        }
      },
      modelHandleOk (type) {
        if (type === 'password') {
          this.passwordModel.visible = false
        }
      },
      modelHandleCancel (type) {
        if (type === 'password') {
          this.passwordModel.visible = false
        }
      }
    }
  }
</script>

<style scoped>

</style>
