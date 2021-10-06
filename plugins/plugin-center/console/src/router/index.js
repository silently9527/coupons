const router = [
  {
    path: '/plugin-center',
    name: 'PluginCenter',
    component: () => import(/* webpackChunkName: "home" */ '../views/Home.vue'),
    children: [
      {
        path: 'plugin-list',
        name: 'PluginList',
        component: () => import(/* webpackChunkName: "codeGenerator" */ '../views/PluginList.vue')
      }
    ]
  }
]

export default router
