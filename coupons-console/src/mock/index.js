import { isIE } from '@/utils/util'
import config from '@/utils/config'

// 判断环境不是 prod 或者 preview 是 true 时，加载 mock 服务
if (config.getEnv() === 'mock') {
  if (isIE()) {
    console.error('[antd-pro] ERROR: `mockjs` NOT SUPPORT `IE` PLEASE DO NOT USE IN `production` ENV.')
  }
  // 使用同步加载依赖
  // 防止 vuex 中的 GetInfo 早于 mock 运行，导致无法 mock 请求返回结果
  console.log('[antd-pro] mock mounting')
  const Mock = require('mockjs2')
  require('./services/auth')
  require('./services/user')
  require('./services/manage')
  require('./services/other')
  require('./services/tagCloud')
  require('./services/article')

  Mock.setup({
    timeout: 800 // setter delay time
  })
  console.log('[antd-pro] mock mounted')
}
