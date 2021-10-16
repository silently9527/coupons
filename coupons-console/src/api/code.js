import request from '@/utils/request'

const api = {
  commonApi: '/code'
}

export default api

export function getFiles() {
  return request('form', true)({
    url: api.commonApi + '/files',
    method: 'get'
  })
}

export function readFileContext(path) {
  return request('form', true)({
    url: api.commonApi + '/file/content',
    method: 'get',
    params: {
      path
    }
  })
}

export function fileSave(path, content) {
  return request('form', true)({
    url: api.commonApi + '/file/save',
    method: 'post',
    data: {
      path,
      content
    }
  })
}

export function downloadCode() {
  return request('blob', true, true,'blob')({
    url: api.commonApi + '/download',
    method: 'get'
  })
}
