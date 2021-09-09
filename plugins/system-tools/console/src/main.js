import Vue from 'vue'
import App from './App.vue'
import routes from './router'
import VueRouter from 'vue-router'
import store from './store'
import antVue from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'
import './public-path'

Vue.use(antVue)

Vue.use(VueRouter)
let router = null
let instance = null
function render (props = {}) {
  const { container, token } = props
  router = new VueRouter({
    routes
  })
  if (token && typeof (token) === 'string') {
    store.commit('SET_TOKEN', token)
  }
  instance = new Vue({
    router,
    store,
    render: h => h(App)
  }).$mount(container ? container.querySelector('#app') : '#app')
}
// 独立运行时
if (!window.__POWERED_BY_QIANKUN__) {
  render()
}
export async function bootstrap () {
}
export async function mount (props) {
  render(props)
}
export async function unmount () {
  instance.$destroy()
  instance.$el.innerHTML = ''
  instance = null
  router = null
}
