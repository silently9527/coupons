import axios from 'axios'
import store from '@/store'
import qs from 'qs'
import notification from 'ant-design-vue/es/notification'
import { VueAxios } from './axios'
import config from '@/utils/config'

const CONTENT_TYPE = {
  json: 'application/json;charset=utf-8',
  form: 'application/x-www-form-urlencoded;charset=utf-8',
  file: 'multipart/form-data;charset=utf-8'
}

// 创建 axios 实例
const request = function (requestDataType, useBaseUrl, isGetOriginalData = false) {
  let headerContentType = null
  if (requestDataType === 'json') {
    headerContentType = CONTENT_TYPE.json
  } else if (requestDataType === 'file') {
    headerContentType = CONTENT_TYPE.file
  } else {
    headerContentType = CONTENT_TYPE.form
  }
  const params = {
    baseURL: useBaseUrl ? config.getServerUrl() + '/api' : '',
    timeout: 20000
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
  setResponseInterceptors(request, isGetOriginalData)
  return request
}

// 异常拦截处理器
const errorHandler = (error) => {
  let message = ''
  if (error.response) {
    const response = error.response
    const data = response.data
    // 从 localstorage 获取 token
    message = data.message ? data.message : '授权失败'
    notification.error({
      message: '授权失败',
      description: message
    })
  } else {
    message = '网络异常'
    notification.error({
      message: '错误',
      description: message
    })
  }
  return Promise.reject(new Error(message))
}

// request interceptor
function setRequestInterceptors (request) {
  request.interceptors.request.use(config => {
    const token = store.getters.token
    // 如果 token 存在
    // 让每个请求携带自定义 token 请根据实际情况自行修改
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`
    }
    return config
  }, errorHandler)
}

// response interceptor
function setResponseInterceptors (request, isGetOriginalData) {
  request.interceptors.response.use((response) => {
    if (response.status === 200) {
      if (isGetOriginalData) {
        return response
      } else {
        return response.data
      }
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
  CONTENT_TYPE
}
