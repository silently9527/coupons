// eslint-disable-next-line
import * as loginService from '@/api/login'
// eslint-disable-next-line
import { BasicLayout, BlankLayout, PageView, RouteView } from '@/layouts'
import { registerPluginApp, genDevMenu } from '@/config/plugin/registerPluginApp'
import { account } from './account-routers'

// 前端路由表
const constantRouterComponents = {
  // 基础页面 layout 必须引入
  BasicLayout: BasicLayout,
  BlankLayout: BlankLayout,
  RouteView: RouteView,
  PageView: PageView,
  '403': () => import(/* webpackChunkName: "error" */ '@/views/exception/403'),
  '404': () => import(/* webpackChunkName: "error" */ '@/views/exception/404'),
  '500': () => import(/* webpackChunkName: "error" */ '@/views/exception/500'),

  // exception
  'Exception403': () => import(/* webpackChunkName: "fail" */ '@/views/exception/403'),
  'Exception404': () => import(/* webpackChunkName: "fail" */ '@/views/exception/404'),
  'Exception500': () => import(/* webpackChunkName: "fail" */ '@/views/exception/500'),
  // 用户管理
  'UserManage': () => import('@/views/system/user/Index'),
  // 角色管理
  'RoleManage': () => import('@/views/system/role/Index'),
  // 菜单管理
  'MenuManage': () => import('@/views/system/menu/Index'),
  // 客户端授权管理
  'OAuthClientManage': () => import('@/views/system/oauth-client/Index'),
  // 登录日志
  'LoginLogManage': () => import('@/views/system/login-log/Index'),
  // 插件管理
  'PluginManage': () => import('@/views/system/plugin/Index'),
  // 插件扩展界面
  'PluginExtensionWeb': () => import('@/views/system/plugin/extension/PluginExtensionWeb'),
  // 个人中心
  'AccountCenter': () => import('@/views/account/center/Index'),
  // 个人设置
  'AccountSettings': () => import('@/views/account/settings/Index'),
  'BasicSettings': () => import('@/views/account/settings/BasicSetting'),
  'SecuritySettings': () => import('@/views/account/settings/Security'),
  'NotificationSettings': () => import('@/views/account/settings/Notification')
}

// 前端未找到页面路由（固定不用改）
const notFoundRouter = {
  path: '*', redirect: '/404', hidden: true
}
// 根级菜单
const rootRouter = {
  key: '',
  name: 'index',
  path: '',
  component: 'BasicLayout',
  redirect: '/system',
  meta: {
    title: '首页'
  },
  children: []
}

/**
 * 动态生成菜单
 * @param response 认证后的响应体
 * @returns {Promise<Router>}
 */
export const generatorDynamicRouter = (response) => {
  return new Promise((resolve, reject) => {
    loginService.getCurrentUserNav().then(res => {
      const result = res.data
      if (!result) {
        return
      }
      let navs = result.navigationInfos
      if (!navs) {
        navs = []
      }
      navs = navs.concat(genDevMenu())
      navs = navs.concat(account)
      console.log(navs)
      // navs = navs.concat(staticRouters)
      const menuNav = []
      const childrenNav = []
      //      后端数据, 根级树数组,  根级 PID
      listToTree(navs, childrenNav, '0')
      rootRouter.children = childrenNav
      menuNav.push(rootRouter)
      const routers = generator(menuNav)
      console.log(routers)
      // 注册插件界面
      registerPluginApp(result.pluginWebInfos)
      routers.push(notFoundRouter)
      resolve(routers)
    }).catch(err => {
      reject(err)
    })
  })
}

/**
 * 格式化树形结构数据 生成 vue-router 层级路由表
 *
 * @param routerMap
 * @param parent
 * @returns {*}
 */
export const generator = (routerMap, parent) => {
  return routerMap.map(item => {
    const { title, show, hideChildren, hiddenHeaderContent, target, icon, pluginWebUrl } = item.meta || {}
    const currentRouter = {
      // 如果路由设置了 path，则作为默认 path，否则 路由地址 动态拼接生成如 /dashboard/workplace
      path: item.path || `${parent && parent.path || ''}/${item.key}`,
      // 路由名称，建议唯一
      name: item.name || item.key || '',
      // 该路由对应页面的 组件 :方案1
      // component: constantRouterComponents[item.component || item.key],
      // 该路由对应页面的 组件 :方案2 (动态加载)
      component: (constantRouterComponents[item.component || item.key]) || (() => import(`@/views/${item.component}`)),

      // meta: 页面标题, 菜单图标, 页面权限(供指令权限用，可去掉)
      meta: {
        title: title,
        icon: icon || undefined,
        hiddenHeaderContent: hiddenHeaderContent,
        target: target,
        permission: item.name,
        pluginWebUrl: pluginWebUrl
      }
    }
    // 是否设置了隐藏菜单
    if (show === false) {
      currentRouter.hidden = true
    }
    // 是否设置了隐藏子菜单
    if (hideChildren) {
      currentRouter.hideChildrenInMenu = true
    }
    // 为了防止出现后端返回结果不规范，处理有可能出现拼接出两个 反斜杠
    if (!currentRouter.path.startsWith('http')) {
      currentRouter.path = currentRouter.path.replace('//', '/')
    }
    // 重定向
    item.redirect && (currentRouter.redirect = item.redirect)
    // 是否有子菜单，并递归处理
    if (item.children && item.children.length > 0) {
      // Recursion
      currentRouter.children = generator(item.children, currentRouter)
    }
    return currentRouter
  })
}

/**
 * 数组转树形结构
 * @param list 源数组
 * @param tree 树
 * @param parentId 父ID
 */
const listToTree = (list, tree, parentId) => {
  if (list && list.length > 0) {
    list.forEach(item => {
      // 判断是否为父级菜单
      if (item.parentId === parentId) {
        const child = {
          ...item,
          key: item.key || item.name,
          children: []
        }

        // 迭代 list， 找到当前菜单相符合的所有子菜单
        listToTree(list, child.children, item.id)
        // 删掉不存在 children 值的属性
        if (child.children.length <= 0) {
          delete child.children
        }
        // 加入到树中
        tree.push(child)
      }
    })
  }
}
