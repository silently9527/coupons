import request from '@/utils/request'

const api = {
  commonApi: '/user',
  enableRolesApi: '/user/enable-roles',
  uploadAvatarApi: '/current-user/avatar',
  updateUserInfo: '/current-user/basic-info',
  resetPasswordApi: '/user/resetPassword',
  updatePasswordApi: '/current-user/password',
  avatarUrl: '/file/avatar'
}

export function getPageList (params) {
  return request('form', true)({
    url: api.commonApi,
    method: 'get',
    params: params
  })
}

export function getEnableRoles () {
  return request('json', true)({
    url: api.enableRolesApi,
    method: 'get'
  })
}

export function getRoleByUser (userId) {
  return request('form', true)({
    url: `${api.commonApi}/${userId}/role`,
    method: 'get'
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

export function resetPassword (params) {
  return request('json', true)({
    url: api.resetPasswordApi,
    method: 'put',
    data: params
  })
}

export function deleteById (id) {
  return request('json', true)({
    url: `${api.commonApi}/${id}`,
    method: 'delete'
  })
}

export function uploadAvatar (formData) {
  return request('file', true)({
    url: api.uploadAvatarApi,
    method: 'post',
    data: formData
  })
}

export function updateUserInfo (formData) {
  return request('json', true)({
    url: api.updateUserInfo,
    method: 'put',
    data: formData
  })
}

export function updatePassword (formData) {
  return request('json', true)({
    url: api.updatePasswordApi,
    method: 'put',
    data: formData
  })
}
