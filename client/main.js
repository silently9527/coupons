import Vue from 'vue'
import store from './store'
import App from './App.vue'

import backend from './api/backend'
import cache from './utils/cache'
import mcUtils from './utils/mcUtils'

import TitleNav from './colorui/components/cu-custom.vue'

Vue.component('title-nav',TitleNav)

const message = (title, duration=1500, mask=false, icon='none')=>{
	//统一提示方便全局修改
	if(Boolean(title) === false){
		return;
	}
	uni.showToast({
		title,
		duration,
		mask,
		icon
	});
}

Vue.config.productionTip = false
Vue.prototype.$store = store;
Vue.prototype.$cache = cache;
Vue.prototype.$utils = mcUtils;
Vue.prototype.$message = message;
Vue.prototype.$api = { backend };

App.mpType = 'app'

const app = new Vue({
    ...App
})
app.$mount()
