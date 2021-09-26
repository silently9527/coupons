import httpRequest from '@/utils/http'

module.exports = {
	getNineGoods: function(page, size, nineCid) {
		return httpRequest.get('/mi/load_nine_goods', {
			page: page,
			size: size,
			nineCid: nineCid
		})
	}
}
