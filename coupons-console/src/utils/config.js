const Config = {
  getConfig () {
    return window.config
  },
  getPasswordEncryption () {
    if (window.config.passwordEncryption) {
      return window.config.passwordEncryption
    } else {
      return false
    }
  },
  getEnv () {
    return window.config.env
  },
  isDev () {
    return this.getEnv() === 'dev'
  },
  isProd () {
    return this.getEnv() === 'prod'
  },
  getServerUrl () {
    return window.config.serverUrl
  },
  getApiUrl (suffix) {
    if (suffix && !suffix.startsWith('/')) {
      suffix = '/' + suffix
    }
    return `${window.config.serverUrl}/api${suffix}`
  },
  getClientId () {
    return window.config.clientId
  },
  getClientSecret () {
    return window.config.clientSecret
  }
}
export default Config
