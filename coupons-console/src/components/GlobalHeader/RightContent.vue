<template>
  <div :class="wrpCls" style="margin-right: 24px">
<!--    <a href="http://silently9527.cn" target="_blank" style="color:orangered;margin-right:50px;">新版本5.2.3已发布!</a>-->
    <avatar-dropdown :menu="showMenu" :current-user="currentUser" :class="prefixCls"/>
    <!--    <select-lang :class="prefixCls" />-->
  </div>
</template>

<script>
import AvatarDropdown from './AvatarDropdown'
// import SelectLang from '@/components/SelectLang'

export default {
  name: 'RightContent',
  components: {
    AvatarDropdown
    // SelectLang
  },
  props: {
    prefixCls: {
      type: String,
      default: 'ant-pro-global-header-index-action'
    },
    isMobile: {
      type: Boolean,
      default: () => false
    },
    topMenu: {
      type: Boolean,
      required: true
    },
    theme: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      showMenu: true,
      currentUser: {}
    }
  },
  computed: {
    wrpCls() {
      return {
        'ant-pro-global-header-index-right': true,
        [`ant-pro-global-header-index-${(this.isMobile || !this.topMenu) ? 'light' : this.theme}`]: true
      }
    }
  },
  mounted() {
    this.currentUser = this.userInfo()
  },
  methods: {
    userInfo() {
      return this.$store.getters.userInfo
    }
  }
}
</script>
