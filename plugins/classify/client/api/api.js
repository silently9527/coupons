import httpRequest from '@/utils/http'

module.exports = {
  getCate: function () {
    return httpRequest.get('/mi/load_cate', {})
  }
}

