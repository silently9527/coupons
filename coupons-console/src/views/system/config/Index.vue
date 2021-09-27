<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <a-tabs default-active-key="DaTaoKe" @change="callback" >
        <a-tab-pane key="DaTaoKe" tab="大淘客">
          <a-form-model ref="daTaoKeForm" layout="vertical" :model="form.DaTaoKe" :rules="rules">
            <a-form-model-item label="AppId:" prop="appId">
              <a-input v-model="form.DaTaoKe.appId" placeholder="请填写大淘客AppId" />
            </a-form-model-item>
            <a-form-model-item label="AppSecret:" prop="appSecret">
              <a-input v-model="form.DaTaoKe.appSecret" placeholder="请填写大淘客AppSecret" />
            </a-form-model-item>
            <a-form-model-item>
              <a-button type="primary" style="width:80px" @click="save('daTaoKeForm', 'DaTaoKe')">
                保存
              </a-button>
            </a-form-model-item>
          </a-form-model>
        </a-tab-pane>
        <a-tab-pane key="Wechat" tab="微信小程序" >
          <a-form-model layout="vertical" :model="form.Wechat" ref="wechatForm" :rules="rules">
            <a-form-model-item label="AppId:" prop="appId">
              <a-input v-model="form.Wechat.appId" placeholder="请填写微信小程序AppId" />
            </a-form-model-item>
            <a-form-model-item label="AppSecret:" prop="appSecret">
              <a-input v-model="form.Wechat.appSecret" placeholder="请填写微信小程序AppSecret" />
            </a-form-model-item>
            <a-form-model-item>
              <a-button type="primary" style="width:80px"  @click="save('wechatForm', 'Wechat')">
                保存
              </a-button>
            </a-form-model-item>
          </a-form-model>
        </a-tab-pane>
        <a-tab-pane key="QQ" tab="QQ小程序">
          <a-form-model layout="vertical" :model="form.QQ" ref="qqForm" :rules="rules">
            <a-form-model-item label="AppId:" prop="appId">
              <a-input v-model="form.QQ.appId" placeholder="请填写QQ小程序AppId" />
            </a-form-model-item>
            <a-form-model-item label="AppSecret:" prop="appSecret">
              <a-input v-model="form.QQ.appSecret" placeholder="请填写QQ小程序AppSecret" />
            </a-form-model-item>
            <a-form-model-item>
              <a-button type="primary" style="width:80px"  @click="save('qqForm', 'QQ')">
                保存
              </a-button>
            </a-form-model-item>
          </a-form-model>
        </a-tab-pane>
      </a-tabs>
    </a-card>
  </page-header-wrapper>
</template>

<script>
  import { queryByConfigType, update } from '@/api/config'
  import { fetchResult } from '@/utils/fetchUtil'

  export default {
    name: 'ConfigManage',
    components: {
    },
    data () {
      return {
        rules: {
          appId: [
            { required: true, message: 'AppId不能为空', trigger: 'blur' }
          ],
          appSecret: [
            { required: true, message: 'AppSecret不能为空', trigger: 'blur' }
          ]
        },
        form: {
          DaTaoKe: {
            appId: '',
            appSecret: ''
          },
          Wechat: {
            appId: '',
            appSecret: ''
          },
          QQ: {
            appId: '',
            appSecret: ''
          }
        }
      }
    },
    created () {
      this.getConfig('DaTaoKe')
    },
    methods: {
      getConfig (configType) {
        queryByConfigType(configType)
          .then(res => {
            if (fetchResult(res, false, true)) {
              if (res.data) {
                this.form[configType] = JSON.parse(res.data.textJson)
              }
            }
          })
      },
      callback (key) {
        this.getConfig(key)
      },
      save (form, configType) {
        this.$refs[form].validate(valid => {
          if (valid) {
            update({
              configType: configType,
              textJson: JSON.stringify(this.form[configType])
            }).then(res => {
              fetchResult(res)
            })
          }
          return false
        })
      }
    }
  }
</script>

<style lang="less" scoped>
</style>
