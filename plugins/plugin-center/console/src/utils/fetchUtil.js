import notification from 'ant-design-vue/es/notification'

export function fetchResult (res, showNotify = true) {
  if (res.code === 1) {
    if (showNotify) {
      notification.success({
        message: '提示',
        description: res.message
      })
    }
    if (res.data) {
      return res.data
    } else {
      return true
    }
  } else {
    if (showNotify) {
      notification.error({
        message: '提示',
        description: res.message
      })
    }
    return undefined
  }
}
