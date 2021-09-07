import request from '@/utils/request'

const api = {
  pluginList: '/plugin',
  start: '/plugin/start',
  stop: '/plugin/stop',
  install: '/plugin/install',
  uninstall: '/plugin/uninstall'
}

export default api

export function getPluginList () {
  return request('json', true)({
    url: api.pluginList,
    method: 'get'
  })
}

export function start (id) {
  return request('json', true)({
    url: api.start + '/' + id,
    method: 'post'
  })
}

export function stop (id) {
  return request('json', true)({
    url: api.stop + '/' + id,
    method: 'post'
  })
}

export function install (formData) {
  return request('file', true)({
    url: api.install,
    method: 'post',
    data: formData
  })
}

export function uninstall (id) {
  return request('json', true)({
    url: api.uninstall + '/' + id,
    method: 'post'
  })
}
