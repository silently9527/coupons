import request from '@/utils/request'

const api = {
  commonApi: '/role',
  roleMenuList: '/role/role-menu',
  updateRoleMenu: '/role/role-menu'
}

export default api

export function getPageList (params) {
  return request('form', true)({
    url: api.commonApi,
    method: 'get',
    params: params
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

export function updateStatus (id, status) {
  return request('json', true)({
    url: `${api.commonApi}/${id}/${status}`,
    method: 'put'
  })
}

export function deleteById (id) {
  return request('json', true)({
    url: `${api.commonApi}/${id}`,
    method: 'delete'
  })
}

export function getRoleMenuList (params) {
  return request('json', true)({
    url: api.roleMenuList,
    method: 'get',
    params: params
  })
}

export function updateRoleMenu (params) {
  return request('json', true)({
    url: api.updateRoleMenu,
    method: 'put',
    data: params
  })
}
