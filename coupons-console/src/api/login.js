import request from '@/utils/request'
import config from '@/utils/config'
import { Base64 } from 'js-base64'

const userApi = {
  Login: config.getServerUrl() + '/oauth/token',
  Logout: config.getApiUrl('/user/logout'),
  UserInfo: '/user/info',
  UserMenu: '/user/nav'
}

export function login (parameter) {
  // 生成Authorization
  const code = `${config.getClientId()}:${config.getClientSecret()}`
  const authorization = Base64.encode(code)

  return request('form', false)({
    url: userApi.Login,
    method: 'post',
    headers: {
      'Authorization': `Basic ${authorization}`
    },
    data: parameter
  })
}

export function getInfo () {
  return request('json', true)({
    url: userApi.UserInfo,
    method: 'get'
  })
}

export function getCurrentUserNav () {
  return request('json', true)({
    url: userApi.UserMenu,
    method: 'get'
  })
}

export function logout () {
  return request('json', true)({
    url: userApi.Logout,
    method: 'post'
  })
}
