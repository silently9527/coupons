import httpRequest from '@/utils/http'

module.exports = {
	getRankGoods: function(cid) {
		return httpRequest.get('/mi/load_rank_goods', {
			cid: cid
		})
	}
}
