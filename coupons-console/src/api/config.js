import request from '@/utils/request'

const api = {
  commonApi: '/config'
}

export default api

export function queryByConfigType (configType) {
  return request('form', true)({
    url: api.commonApi,
    method: 'get',
    params: {
      configType: configType
    }
  })
}

export function update (params) {
  return request('form', true)({
    url: api.commonApi,
    method: 'post',
    data: params
  })
}
