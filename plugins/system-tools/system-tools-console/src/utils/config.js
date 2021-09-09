const PLUGIN_ID = 'grape-plugin-system-tools'

const Config = {
  getConfig () {
    return window.config
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
  getPluginApiUrl (suffix, configId) {
    if (!configId) {
      configId = PLUGIN_ID
    }
    if (suffix && !suffix.startsWith('/')) {
      suffix = '/' + suffix
    }
    return `${window.config.serverUrl}${window.config.pluginApiPrefix}${configId}${suffix}`
  }
}
export default Config
