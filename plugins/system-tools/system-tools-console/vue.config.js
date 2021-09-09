const PLUGIN_ID = 'grape-plugin-system-tools'
const DEV_PORT = 8082

module.exports = {
  // 该出必须配置为：/插件静态资源配置字符/当前插件id
  publicPath: `/plugin-web/${PLUGIN_ID}`,
  devServer: {
    port: DEV_PORT,
    headers: {
      'Access-Control-Allow-Origin': '*',
    }
  },
  // 自定义webpack配置
  configureWebpack: {
    output: {
      // 把子应用打包成 umd 库格式(必须)
      library: `${PLUGIN_ID}-[name]`,
      libraryTarget: 'umd',// 把微应用打包成 umd 库格式
      jsonpFunction: `webpackJsonp_${PLUGIN_ID}`,
    },
  }
}
