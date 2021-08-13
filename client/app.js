import Vue from 'vue'

export default {
  initTitleNav() {
    uni.getSystemInfo({
      success: function (e) {
        // #ifndef MP
        Vue.prototype.StatusBar = e.statusBarHeight;
        if (e.platform === 'android') {
          Vue.prototype.CustomBar = e.statusBarHeight + 50;
        } else {
          Vue.prototype.CustomBar = e.statusBarHeight + 45;
        }
        // #endif
        // #ifdef MP-WEIXIN
        Vue.prototype.StatusBar = e.statusBarHeight;
        let custom = wx.getMenuButtonBoundingClientRect();
        Vue.prototype.Custom = custom;
        Vue.prototype.CustomBar = custom.bottom + custom.top - e.statusBarHeight;
        // #endif
        // #ifdef MP-ALIPAY
        // Vue.prototype.StatusBar = e.statusBarHeight;
        // Vue.prototype.CustomBar = e.statusBarHeight + e.titleBarHeight;
        // #endif
        // #ifdef MP-QQ
        Vue.prototype.StatusBar = e.statusBarHeight;
        let customQQ = qq.getMenuButtonBoundingClientRect();
        Vue.prototype.Custom = customQQ;
        Vue.prototype.CustomBar = customQQ.bottom + customQQ.top - e.statusBarHeight;
        if (e.model.indexOf("iPhone X") > -1) {
          Vue.prototype.CustomBar = 82;
        } else {
          Vue.prototype.CustomBar = 60;
        }
        // #endif
        // #ifdef MP-TOUTIAO
        // Vue.prototype.StatusBar = e.statusBarHeight;
        // let customTT = tt.getMenuButtonBoundingClientRect();
        // Vue.prototype.Custom = customTT;
        // Vue.prototype.CustomBar = customTT.bottom + customTT.top - e.statusBarHeight
        // #endif
      }
    })
  },
  initShareMenu() {
    // #ifdef MP-WEIXIN
    wx.showShareMenu({
      withShareTicket: true,
      menus: ['shareAppMessage', 'shareTimeline']
    })
    // #endif
    // #ifdef MP-QQ
    uni.showShareMenu({
      withShareTicket: true
    })
    // #endif
  }

};
