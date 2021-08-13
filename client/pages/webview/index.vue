<template>
	<view></view>
</template>

<script>
let wv;
export default {
	onLoad(option) {
		if (!option.url) {
			return;
		}
		this.url = option.url;
		if(option.title){
			this.title = option.title
		}
		if(option.top){
			this.top = option.top
		}
		// #ifdef APP-PLUS
		let currentWebview = this.$scope.$getAppWebview();
		wv = plus.webview.create(this.url, 'webview', {
			plusrequire: 'none',
			'uni-app': 'none',
			top: uni.getSystemInfoSync().statusBarHeight + (this.top-0)
		});
		wv.addEventListener(
			'loaded',
			() => {
				currentWebview.setStyle({
					titleNView: {
						titleText: this.title ? this.title : wv.getTitle()
					}
				});
			},
			false
		);
		wv.overrideUrlLoading({
			mode: 'reject',
			match:'.*taobao\.com.*|.*tmall\.com.*|.*index\.php.*p/d&id=.*'
		 }, e => {
			console.log('reject url: ' + e.url);
			this.handle(e.url);
		});
		currentWebview.append(wv); //一定要append到当前的页面里！！！才能跟随当前页面一起做动画，一起关闭
		// #endif
	},
	data() {
		return {
			title: '',
			top: 44,
			url: ''
		};
	},
	methods: {
		handle(url) {
			if (url.indexOf('taobao.com') > -1 || url.indexOf('tmall.com') > -1) {
				this.openTaobao(url);
				return;
			}
			if (url.indexOf('http://szjx.top/index.php?r=p/d&id=') > -1) {
				let id = this.getQueryVariable(url, 'id');
				uni.navigateTo({
				    url: `/pages/product/product?goodsId=${id}`
				})
				return;
			}
			wv.loadURL(url);
		},
		openTaobao(url) {
			plus.runtime.openURL(url.replace('https:', 'taobao:'), res => {
				plus.runtime.openURL(url);
			});
		},
		getQueryVariable(query, variable) {
			var vars = query.split('&');
			for (var i = 0; i < vars.length; i++) {
				var pair = vars[i].split('=');
				if (pair[0] == variable) {
					return pair[1];
				}
			}
			return false;
		}
	}
};
</script>

<style scoped></style>
