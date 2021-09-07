import notification from 'ant-design-vue/es/notification'

export function fetchResult (res, showSuccessNotify = true, showErrorNotify = true) {
  if (res.code === 1) {
    if (showSuccessNotify) {
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
    if (showErrorNotify) {
      notification.error({
        message: '提示',
        description: res.message
      })
    }
    return undefined
  }
}
