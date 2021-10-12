import request from '@/utils/request'

const api = {
  pluginList: '/plugin/center/list'
}

export default api

export function getPluginList(params) {
  return request('json', true)({
    url: api.pluginList,
    method: 'get',
    params: params
  })
}

export function onlineInstall(pluginId, pluginCode, password) {
  return request('json', true)({
    url: '/plugin/online-install/' + pluginId + '/' + pluginCode + '/' + password,
    method: 'post'
  })
}


