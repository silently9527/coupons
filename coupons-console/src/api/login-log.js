import request from '@/utils/request'

const api = {
  commonApi: '/login-log',
  currentLoginLogApi: '/login-log/current'
}

export default api

export function getPageList (params) {
  return request('form', true)({
    url: api.commonApi,
    method: 'get',
    params: params
  })
}

export function getPageListOfCurrent (params) {
  return request('form', true)({
    url: api.currentLoginLogApi,
    method: 'get',
    params: params
  })
}
