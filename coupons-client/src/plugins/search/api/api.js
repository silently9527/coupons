import httpRequest from '@/utils/http'

module.exports = {
	search2: function(keyword, page, sort, hasCoupon) {
		return httpRequest.get('/mi/search2', {
			keyword: keyword,
			page: page,
			sort: sort,
			hasCoupon: hasCoupon
		})
	},
	getHotSearch: function() {
		return httpRequest.get('/mi/get_hot_search', {})
	}
}
