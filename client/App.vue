<script>
  /**
   * vuex管理登陆状态，具体可以参考官方登陆模板示例
   */
  import app from 'app.js'
  import {mapMutations} from 'vuex'

  export default {
    onLaunch: function () {
      app.initTitleNav();
      app.initShareMenu();

      this.$api.backend.getLoginUser().then(resp => {
        if (resp.responseCode === 7002) {
          this.logout();
        } else {
          this.storeUser(resp.data)
        }
      })

    },
    onShow: function (res) {
      if (res.shareTicket) {
        this.storeShareTicket(res.shareTicket)
      }
    },
    onHide: function () {
    },
    methods: {
      ...mapMutations(['storeUser', 'logout', 'storeShareTicket'])
    }
  }
</script>

<style lang="scss">
  @import "colorui/icon.css";
  @import "colorui/main.css";
  @import "style/app.scss";

  .container {
    background: $page-color-base;
  }
</style>
