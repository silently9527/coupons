import request from '@/utils/request'

const api = {
  navTree: '/current-user/menu-tree',
  commonApi: '/menu'
}

export default api

export function getNavTree (havePluginMenu = false) {
  return request('json', true)({
    url: api.navTree,
    method: 'get',
    params: {
      havePluginMenu: havePluginMenu
    }
  })
}

export function updateStatus (menuId, enable) {
  return request('json', true)({
    url: `${api.commonApi}/${menuId}/${enable}`,
    method: 'put'
  })
}

export function deleteById (id) {
  return request('json', true)({
    url: `${api.commonApi}/${id}`,
    method: 'delete'
  })
}

export function add (params) {
  return request('json', true)({
    url: api.commonApi,
    method: 'post',
    data: params
  })
}

export function update (params) {
  return request('json', true)({
    url: api.commonApi,
    method: 'put',
    data: params
  })
}
