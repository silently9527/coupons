import request from '@/utils/request'
import config from '@/utils/config'
const api = {
  commonApi: config.getPluginApiUrl('/code-generator'),
  gen: config.getPluginApiUrl('/code-generator/gen')
}

export default api

export function getPageList (params) {
  return request('form', false)({
    url: api.commonApi,
    method: 'get',
    params: params
  })
}

export function add (params) {
  return request('json', false)({
    url: api.commonApi,
    method: 'post',
    data: params
  })
}

export function update (params) {
  return request('json', false)({
    url: api.commonApi,
    method: 'put',
    data: params
  })
}

export function deleteById (id) {
  return request('json', false)({
    url: `${api.commonApi}/${id}`,
    method: 'delete'
  })
}

export function gen (id) {
  return request('json', false, true)({
    url: `${api.gen}/${id}`,
    method: 'post',
    responseType: 'blob'
  })
}
