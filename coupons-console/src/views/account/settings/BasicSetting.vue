<template>
  <div class="account-settings-info-view">
    <a-row :gutter="16">
      <a-col :md="24" :lg="16">

        <a-form layout="vertical" :model="formData" @submit="fetch" @submit.native.prevent>
          <a-form-item
            :label="$t('account.settings.basic.nickname')"
          >
            <a-input v-model="formData.name" :placeholder="$t('account.settings.basic.nickname-message')" />
          </a-form-item>
          <a-form-item
            :label="$t('account.settings.basic.phone')"
          >
            <a-input v-model="formData.phone" :placeholder="$t('account.settings.basic.phone-message')"/>
          </a-form-item>

          <a-form-item
            :label="$t('account.settings.basic.email')"
            :required="false"
          >
            <a-input v-model="formData.email" placeholder="example@ant.design"/>
          </a-form-item>

          <a-form-item>
            <a-button type="primary" html-type="submit">{{ $t('account.settings.basic.update') }}</a-button>
          </a-form-item>
        </a-form>

      </a-col>
      <a-col :md="24" :lg="8" :style="{ minHeight: '180px' }">
        <div class="ant-upload-preview" @click="$refs.modal.edit(1)" >
          <a-icon type="cloud-upload-o" class="upload-icon"/>
          <div class="mask">
            <a-icon type="plus" />
          </div>
          <img :src="option.img"/>
        </div>
      </a-col>

    </a-row>

    <avatar-modal ref="modal" @ok="setavatar"/>

  </div>
</template>

<script>
import { getAvatarUrl } from '@/api/user-avatar'
import { updateUserInfo } from '@/api/user'
import { fetchResult } from '@/utils/fetchUtil'
import AvatarModal from './AvatarModal'

export default {
  components: {
    AvatarModal
  },
  data () {
    const img = getAvatarUrl(this.$store.getters.avatar)
    return {
      // cropper
      formData: {
        name: '',
        phone: '',
        email: ''
      },
      preview: {},
      option: {
        img: img,
        info: true,
        size: 1,
        outputType: 'jpeg',
        canScale: false,
        autoCrop: true,
        // 只有自动截图开启 宽度高度才生效
        autoCropWidth: 180,
        autoCropHeight: 180,
        fixedBox: true,
        // 开启宽度和高度比例
        fixed: true,
        fixedNumber: [1, 1]
      }
    }
  },
  created () {
    var info = this.$store.getters.userInfo
    this.formData.name = info.name
    this.formData.phone = info.phone
    this.formData.email = info.email
  },
  methods: {
    setavatar (imgName) {
      this.option.img = getAvatarUrl(imgName)
    },
    fetch (params = {}) {
      params = { ...this.formData }
      updateUserInfo(params).then(res => {
        if (fetchResult(res)) {
          var info = this.$store.getters.userInfo
          info.name = this.formData.name
          info.phone = this.formData.phone
          info.email = this.formData.email
          this.$store.commit('SET_INFO', info)
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>

  .avatar-upload-wrapper {
    height: 200px;
    width: 100%;
  }

  .ant-upload-preview {
    position: relative;
    margin: 0 auto;
    width: 100%;
    max-width: 180px;
    border-radius: 50%;
    box-shadow: 0 0 4px #ccc;

    .upload-icon {
      position: absolute;
      top: 0;
      right: 10px;
      font-size: 1.4rem;
      padding: 0.5rem;
      background: rgba(222, 221, 221, 0.7);
      border-radius: 50%;
      border: 1px solid rgba(0, 0, 0, 0.2);
    }
    .mask {
      opacity: 0;
      position: absolute;
      background: rgba(0,0,0,0.4);
      cursor: pointer;
      transition: opacity 0.4s;

      &:hover {
        opacity: 1;
      }

      i {
        font-size: 2rem;
        position: absolute;
        top: 50%;
        left: 50%;
        margin-left: -1rem;
        margin-top: -1rem;
        color: #d6d6d6;
      }
    }

    img, .mask {
      width: 100%;
      max-width: 180px;
      height: 100%;
      border-radius: 50%;
      overflow: hidden;
    }
  }
</style>
