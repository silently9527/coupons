const Config = {
  getConfig () {
    return window.config
  },
  getEnv () {
    return window.config.env
  },
  getServerUrl () {
    return window.config.serverUrl
  },
  getApiUrl (suffix, haveApi = true) {
    if(haveApi){
      return `${window.config.serverUrl}/api${suffix}`
    } else {
      return `${window.config.serverUrl}${suffix}`
    }
  }
}
export default Config
