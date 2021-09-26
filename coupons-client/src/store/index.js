import Vue from 'vue'
import Vuex from 'vuex'
import api from "../api/backend";

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    accessToken: undefined,
    hasLogin: false,
    userInfo: undefined,
    shareTicket: undefined
  },
  getters: {
    accessToken: state => {
      if (state.accessToken) {
        return state.accessToken;
      }
      return uni.getStorageSync('mall_coupons_access_token');
    },
    hasLogin: state => {
      if (state.hasLogin) {
        return state.hasLogin;
      }
      return uni.getStorageSync('mall_coupons_has_login');
    },
    userInfo: state => {
      if (state.userInfo) {
        return state.userInfo;
      }
      return uni.getStorageSync('mall_coupons_user_info')
    },
    avatar: state => {
      if (state.userInfo && state.userInfo.avatarUrl) {
        return state.userInfo.avatarUrl;
      }
      return 'http://file.szjx.top/fashion/missing-face.png';
    }
  },
  mutations: {
    login(state, param) {
      uni.login({
        provider: param.provider,
        success: (response) => {
          api.login(param.provider, response.code).then(resp => {
            let token = resp.data.xAuthToken
            state.accessToken = token;
            uni.setStorage({key: 'mall_coupons_access_token', data: token});

            api.getLoginUser().then(resp => {
              state.hasLogin = true;
              state.userInfo = resp.data
              if (!resp.data.nick) {
                api.initUserInfo({
                  nick: param.userInfo.nickName,
                  avatarUrl: param.userInfo.avatarUrl,
                  city: param.userInfo.city,
                  country: param.userInfo.country,
                  province: param.userInfo.province,
                  sex: param.userInfo.gender
                }).then((user) => {
                  let userinfo = user.data
                  state.hasLogin = true;
                  state.userInfo = userinfo;
                  uni.setStorage({key: 'mall_coupons_has_login', data: true});
                  uni.setStorage({key: 'mall_coupons_user_info', data: userinfo});
                });
              }
            })
          })
        }
      });
    },
    storeShareTicket(state, shareTicket) {
      state.shareTicket = shareTicket;
    },
    storeToken(state, token) {
      state.accessToken = token;
      uni.setStorage({key: 'mall_coupons_access_token', data: token});
    },
    storeUser(state, user) {
      state.hasLogin = true;
      state.userInfo = user;
      uni.setStorage({key: 'mall_coupons_has_login', data: true});
      uni.setStorage({key: 'mall_coupons_user_info', data: user});
    },
    logout(state) {
      api.logout();
      state.hasLogin = false;
      state.userInfo = undefined;
      uni.removeStorage({
        key: 'mall_coupons_access_token'
      });
      uni.removeStorage({
        key: 'mall_coupons_has_login'
      });
      uni.removeStorage({
        key: 'mall_coupons_user_info'
      });
    }
  },
  actions: {}
})

export default store
