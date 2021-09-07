import axios from 'axios'
import store from '@/store'
import qs from 'qs'
import notification from 'ant-design-vue/es/notification'
import { VueAxios } from './axios'
import config from '@/utils/config'

const contentType = {
  json: 'application/json;charset=utf-8',
  form: 'application/x-www-form-urlencoded;charset=utf-8',
  file: 'multipart/form-data;charset=utf-8'
}

// 创建 axios 实例
const request = function (requestDataType, useBaseUrl) {
  let headerContentType = null
  if (requestDataType === 'json') {
    headerContentType = contentType.json
  } else if (requestDataType === 'file') {
    headerContentType = contentType.file
  } else {
    headerContentType = contentType.form
  }
  const params = {
    baseURL: useBaseUrl ? config.getServerUrl() + '/api' : '',
    timeout: 6000
  }
  params.headers = {
    'Content-Type': headerContentType
  }
  params.transformRequest = [function (data, config) {
    const contentType = config['Content-Type']
    if (!contentType) {
      return qs.stringify(data)
    }
    if (contentType.indexOf('application/json') > -1) {
      return JSON.stringify(data)
    } else if (contentType.indexOf('multipart/form-data') > -1) {
      return data
    } else {
      return qs.stringify(data)
    }
  }]
  const request = axios.create(params)
  setRequestInterceptors(request)
  setResponseInterceptors(request)
  return request
}

// 异常拦截处理器
const errorHandler = (error) => {
  if (error.response) {
    const data = error.response.data
    // 从 localstorage 获取 token
    if (error.response.status === 403) {
      notification.error({
        message: 'Forbidden',
        description: data.message
      })
    }
    if (error.response.status === 401) {
      notification.error({
        message: '授权失败',
        description: data.message
      })
      if (data.code === 4) {
        // token 过期
        notification.error({
          message: '授权失败',
          description: 'token过期'
        })
      }
    }
    return data
  }
  notification.error({
    message: '错误',
    description: '网络异常'
  })
  return Promise.reject(error)
}

// request interceptor
function setRequestInterceptors (request) {
  request.interceptors.request.use(config => {
    const token = store.getters.token
    console.log('token=', token)
    // 如果 token 存在
    // 让每个请求携带自定义 token 请根据实际情况自行修改
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`
    }
    return config
  }, errorHandler)
}

// response interceptor
function setResponseInterceptors (request) {
  request.interceptors.response.use((response) => {
    let message = null
    if (response.status === 200) {
      return response.data
    } else if (response.status === 500) {
      message = '服务器错误[500]'
    } else {
      message = '请求失败[' + response.status + ']'
    }
    if (message) {
      notification.error({
        message: message,
        description: response.data.message
      })
    }
  }, errorHandler)
}

const installer = {
  vm: {},
  install (Vue) {
    Vue.use(VueAxios, request)
  }
}

export default request

export {
  installer as VueAxios,
  request as axios,
  contentType
}
