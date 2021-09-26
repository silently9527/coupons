import httpRequest from '@/utils/http'

module.exports = {
	recommendCollocations: function(page) {
		return httpRequest.get('/mi/collocation/load_recommend', {
			page
		})
	},
	collocationsList: function(page, sex) {
		if (sex) {
			return httpRequest.get('/mi/collocation/list', {
				page: page,
				sex: sex
			})
		}
		return httpRequest.get('/mi/collocation/list', {
			page
		})
	},
	cancelCollocationAppreciate: function(collocationId) {
		return httpRequest.get('/collocation/cancel_appreciate', {
			collocationId
		})
	},
	addCollocationAppreciate: function(collocationId) {
		return httpRequest.get('/collocation/add_appreciate', {
			collocationId
		})
	},
	collocationSimpleDetail: function(collocationId) {
		return httpRequest.get('/mi/collocation/simple_detail', {
			collocationId
		})
	},
	recommendCollocations2: function(page) {
		return httpRequest.get('/mi/collocation/load_recommend2', {
			page
		})
	},
	collocationProduct: function(collocationId) {
		return httpRequest.get('/mi/collocation/product', {
			collocationId
		})
	}
}
