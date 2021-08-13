import httpRequest from '../utils/http'

module.exports = {
  login: function (provider, code) {
    let url = '/auth/miniprogram';
    if (provider === 'qq') {
      url = '/auth/QQMiniProgram'
    }
    return httpRequest.get(url, {code: code})
  },
  htmlLogin: function (mobile, code) {
    return httpRequest.post('/login', {mobile: mobile, code: code})
  },
  logout: function () {
    return httpRequest.get('/logout')
  },
  getLoginUser: function () {
    return httpRequest.get('/user/current_user', {})
  },
  sendEmailCode: function (params) {
    return httpRequest.post('/mi/send_email_code', params)
  },
  updatePassword: function (params) {
    return httpRequest.post('/mi/update_password', params)
  },
  register: function (user) {
    return httpRequest.post('/mi/register', user)
  },
  updateUserInfo: function (user) {
    return httpRequest.post('/user/update', user)
  },
  initUserInfo: function (user) {
    return httpRequest.post('/user/init_current_user', user)
  },
  getCate: function () {
    return httpRequest.get('/mi/load_cate', {})
  },
  recommendGoods: function (page) {
    return httpRequest.get('/mi/load_recommend_goods', {page: page})
  },
  getGoodsDetail: function (id, goodsId) {
    let params = {};
    if (id) {
      params.id = id
    }
    if (goodsId) {
      params.goodsId = goodsId
    }
    return httpRequest.get('/mi/goods_detail', params)
  },
  getGoodsByCate: function (subcid, page, sort) {
    return httpRequest.get('/mi/load_goods_by_cate', {subcid: subcid, page: page, sort: sort})
  },
  getPrivilegeLink: function (goodsId) {
    return httpRequest.get('/mi/get_privilege_link', {goodsId: goodsId})
  },
  getHotSearch: function () {
    return httpRequest.get('/mi/get_hot_search', {})
  },
  search: function (keyword, page, sort) {
    return httpRequest.get('/mi/search', {keyword: keyword, page: page, sort: sort})
  },
  search2: function (keyword, page, sort, hasCoupon) {
    return httpRequest.get('/mi/search2', {keyword: keyword, page: page, sort: sort, hasCoupon: hasCoupon})
  },
  favoriteGoods: function (goodsId) {
    return httpRequest.get('/collection/product/add', {productId: goodsId})
  },
  unFavoriteGoods: function (goodsId) {
    return httpRequest.get('/collection/product/remove', {productId: goodsId})
  },
  favoriteList: function (page, size) {
    return httpRequest.get('/collection/product/list', {page: page, size: size})
  },
  getNineGoods: function (page, size, nineCid) {
    return httpRequest.get('/mi/load_nine_goods', {page: page, size: size, nineCid: nineCid})
  },
  getRankGoods: function (cid) {
    return httpRequest.get('/mi/load_rank_goods', {cid: cid})
  },
  getSimilarGoods: function (daTaoKeGoodsId) {
    return httpRequest.get('/mi/get_similar_goods', {daTaoKeGoodsId: daTaoKeGoodsId})
  },
  listCarousel: function () {
    return httpRequest.post('/mi/list_carousel', {})
  },
  listMenus: function () {
    return httpRequest.post('/mi/list_menus', {})
  },
  recommendCollocations: function (page) {
    return httpRequest.get('/mi/collocation/load_recommend', {page})
  },
  collocationsList: function (page, sex) {
    if (sex) {
      return httpRequest.get('/mi/collocation/list', {page: page, sex: sex})
    }
    return httpRequest.get('/mi/collocation/list', {page})
  },
  recommendCollocations2: function (page) {
    return httpRequest.get('/mi/collocation/load_recommend2', {page})
  },
  addCollocationAppreciate: function (collocationId) {
    return httpRequest.get('/collocation/add_appreciate', {collocationId})
  },
  cancelCollocationAppreciate: function (collocationId) {
    return httpRequest.get('/collocation/cancel_appreciate', {collocationId})
  },
  collocationSimpleDetail: function (collocationId) {
    return httpRequest.get('/mi/collocation/simple_detail', {collocationId})
  },
  collocationProduct: function (collocationId) {
    return httpRequest.get('/mi/collocation/product', {collocationId})
  }
}

