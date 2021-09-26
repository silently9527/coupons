import httpRequest from '@/utils/http'

module.exports = {
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
  search: function (keyword, page, sort) {
    return httpRequest.get('/mi/search', {keyword: keyword, page: page, sort: sort})
  },
  login: function(provider, code) {
  	let url = '/auth/miniprogram';
  	if (provider === 'qq') {
  		url = '/auth/QQMiniProgram'
  	}
  	return httpRequest.get(url, {
  		code: code
  	})
  },
  htmlLogin: function(mobile, code) {
  	return httpRequest.post('/login', {
  		mobile: mobile,
  		code: code
  	})
  },
  logout: function() {
  	return httpRequest.get('/logout')
  },
  getLoginUser: function() {
  	return httpRequest.get('/user/current_user', {})
  },
  initUserInfo: function(user) {
  	return httpRequest.post('/user/init_current_user', user)
  }
}

