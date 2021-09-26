import httpRequest from '@/utils/http'

module.exports = {
	getGoodsByCate: function(subcid, page, sort) {
		return httpRequest.get('/mi/load_goods_by_cate', {
			subcid: subcid,
			page: page,
			sort: sort
		})
	},
	getPrivilegeLink: function(goodsId) {
		return httpRequest.get('/mi/get_privilege_link', {
			goodsId: goodsId
		})
	},
	getGoodsDetail: function(id, goodsId) {
		let params = {};
		if (id) {
			params.id = id
		}
		if (goodsId) {
			params.goodsId = goodsId
		}
		return httpRequest.get('/mi/goods_detail', params)
	},
	getSimilarGoods: function(daTaoKeGoodsId) {
		return httpRequest.get('/mi/get_similar_goods', {
			daTaoKeGoodsId: daTaoKeGoodsId
		})
	},
	favoriteGoods: function(goodsId) {
		return httpRequest.get('/collection/product/add', {
			productId: goodsId
		})
	},
	favoriteList: function(page, size) {
		return httpRequest.get('/collection/product/list', {
			page: page,
			size: size
		})
	}
}
