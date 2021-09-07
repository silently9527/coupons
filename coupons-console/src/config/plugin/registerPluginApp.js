import store from '@/store'
import { registerMicroApps } from 'qiankun'
import config from '@/utils/config'
import { getDevApp, getDevMenu } from './devPluginConfig'

const genActiveRule = hash => location => location.hash.startsWith(hash)

export const registerPluginApp = (pluginWebInfos) => {
  if (config.isDev()) {
    // 在开发环境下获取开发环境配置的microApp
    pluginWebInfos = getDevApp()
  }
  if (pluginWebInfos) {
    const registerMicroApp = []
    const props = getProps()
    pluginWebInfos.forEach(pluginWebInfo => {
      registerMicroApp.push({
        name: pluginWebInfo.appName, // app name registered
        entry: pluginWebInfo.appPath,
        container: '#micro-view',
        activeRule: genActiveRule(`#${pluginWebInfo.rootRouting}`),
        props: props
      })
    })
    registerMicroApps(registerMicroApp)
  }
}

export const genDevMenu = () => {
  if (config.isDev()) {
    return getDevMenu()
  } else {
    return []
  }
}

const getProps = () => {
  const props = {}
  props.token = store.getters.token
  return props
}
