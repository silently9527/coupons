// 开发环境下配置插件菜单和app信息

const systemToolsRootRoute = '/grape-plugin-system-tools'

const pluginWebAppInfos = [
  {
    appName: 'system-tools-web',
    appPath: 'http://127.0.0.1:8082/plugin-web/grape-plugin-system-tools/',
    rootRouting: systemToolsRootRoute
  }
]

const navigationInfos = [
  {
    id: '1000',
    parentId: '0',
    key: 'plugin-dev-menus',
    path: '',
    redirect: `${systemToolsRootRoute}/code-generator`,
    component: 'RouteView',
    meta: {
      title: '插件菜单(dev)',
      show: true,
      icon: 'hdd'
    }
  },
  {
    id: '1001',
    parentId: '1000',
    key: 'code-generator',
    path: `${systemToolsRootRoute}/code-generator`,
    redirect: null,
    component: 'PluginExtensionWeb',
    meta: {
      title: '生成代码',
      show: true,
      icon: 'code'
    }
  }
]

export const getDevApp = () => {
  return pluginWebAppInfos
}

export const getDevMenu = () => {
  return navigationInfos
}
