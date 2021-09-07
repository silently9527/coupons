import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'
Vue.config.productionTip = false
Vue.use(Antd)

window.addEventListener('message',  (e) => {
  try {
    new Vue({
      router,
      store,
      render: h => h(App)
    }).$mount('#app')
    const token = e.data
    if (token && typeof(token) === 'string') {
      store.commit('SET_TOKEN', token )
    }
  } catch (err) {
    console.error(err)
  }
});

