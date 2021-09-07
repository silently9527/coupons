// 账户路由
export const account = [
  // account
  {
    'name': 'account',
    'parentId': '0',
    'id': '10028',
    'meta': {
      'title': '个人页',
      'icon': 'user',
      'show': false
    },
    'redirect': '/account/settings',
    'component': 'RouteView'
  },
  {
    'name': 'center',
    'parentId': '10028',
    'id': '10029',
    'meta': {
      'title': '个人中心',
      'show': false
    },
    'path': '/account/center',
    'component': 'AccountCenter'
  },
  // 特殊三级菜单
  {
    'name': 'settings',
    'parentId': '10028',
    'id': '10030',
    'meta': {
      'title': '个人设置',
      'hideHeader': true,
      'hideChildren': true,
      'show': false
    },
    'redirect': '/account/settings/basic',
    'component': 'AccountSettings'
  },
  {
    'name': 'BasicSettings',
    'path': '/account/settings/basic',
    'parentId': '10030',
    'id': '10031',
    'meta': {
      'title': '基本设置',
      'show': false
    },
    'component': 'BasicSettings'
  },
  {
    'name': 'SecuritySettings',
    'path': '/account/settings/security',
    'parentId': '10030',
    'id': '10032',
    'meta': {
      'title': '安全设置',
      'show': false
    },
    'component': 'SecuritySettings'
  },
  {
    'name': 'NotificationSettings',
    'path': '/account/settings/notification',
    'parentId': '10030',
    'id': '10034',
    'meta': {
      'title': '新消息通知',
      'show': false
    },
    'component': 'NotificationSettings'
  }
]
