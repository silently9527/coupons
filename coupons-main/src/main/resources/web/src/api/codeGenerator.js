import request from '@/utils/request'
import config from '@/utils/config'
const api = {
  commonApi: config.getApiUrl('/blocks-plugin-system-tools/code-generator', false)
}

export default api

export function getPageList (params) {
  return request('form', false)({
    url: api.commonApi,
    method: 'get',
    params: params
  })
}
