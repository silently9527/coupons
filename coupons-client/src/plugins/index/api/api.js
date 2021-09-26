import httpRequest from '@/utils/http'

module.exports = {
	recommendGoods: function(page) {
		return httpRequest.get('/mi/load_recommend_goods', {
			page: page
		})
	},
	listCarousel: function() {
		return httpRequest.post('/mi/list_carousel', {})
	},
	listMenus: function() {
		return httpRequest.post('/mi/list_menus', {})
	}
}
