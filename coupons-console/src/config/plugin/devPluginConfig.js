// 开发环境下配置插件菜单和app信息

const systemToolsRootRoute = '/system-tools'
const pluginCenterRootRoute = '/plugin-center'

const pluginWebAppInfos = [
  {
    appName: 'system-tools',
    appPath: 'http://127.0.0.1:8082/plugin-web/system-tools/',
    rootRouting: systemToolsRootRoute
  },
  {
    appName: 'plugin-center',
    appPath: 'http://127.0.0.1:8083/plugin-web/plugin-center/',
    rootRouting: pluginCenterRootRoute
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
  },
  {
    id: '1002',
    parentId: '1000',
    key: 'plugin-center',
    path: `${pluginCenterRootRoute}/plugin-list`,
    redirect: null,
    component: 'PluginExtensionWeb',
    meta: {
      title: '插件中心管理',
      show: true,
      icon: 'pie-chart'
    }
  }
]

export const getDevApp = () => {
  return pluginWebAppInfos
}

export const getDevMenu = () => {
  return navigationInfos
}
