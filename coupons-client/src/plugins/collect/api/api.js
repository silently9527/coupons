import httpRequest from '@/utils/http'

module.exports = {
	favoriteList: function(page, size) {
		return httpRequest.get('/collection/product/list', {
			page: page,
			size: size
		})
	}
}
