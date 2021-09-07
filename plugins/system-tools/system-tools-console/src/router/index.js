const router = [
  {
    path: '/system-tools',
    name: 'Home',
    component: () => import(/* webpackChunkName: "home" */ '../views/Home.vue'),
    children: [
      {
        path: 'code-generator',
        name: 'CodeGenerator',
        component: () => import(/* webpackChunkName: "codeGenerator" */ '../views/CodeGenerator.vue')
      }
    ]
  }
]

export default router
