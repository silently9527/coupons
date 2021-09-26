module.exports = {
  getShareInfo: (shareTicket, callback) => {
    if (!shareTicket) {
      return;
    }
    // #ifdef MP-WEIXIN
    wx.getShareInfo({
      shareTicket: shareTicket,
      timeout: 5000,
      success: callback
    })
    // #endif
    // #ifdef MP-QQ
    qq.getShareInfo({
      shareTicket: shareTicket,
      timeout: 5000,
      success: callback
    })
    // #endif
  }
}
