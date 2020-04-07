'use strict'
const path = require('path')

module.exports = {

  dev: {
    assetsSubDirectory: 'static',
    assetsPublicPath: '/',
    proxyTable: {
      '/pre': {
        target: 'http://127.0.0.1:8008', // 测试地址
        // target: 'http://fb.jsyc11.com',//正式
        changeOrigin: true, // 是否跨域
        pathRewrite: {
          '^/pre': '/project' // 测试库,
        }
      }
    },

    // 本地开发使用
    host: '127.0.0.1',
    port: 9508,
    autoOpenBrowser: true,
    errorOverlay: true,
    notifyOnErrors: false,
    poll: false,
    useEslint: true,
    showEslintErrorsInOverlay: false,
    devtool: 'cheap-source-map',
    cssSourceMap: false
  },

  build: {
    index: path.resolve(__dirname, '../dist/index.html'),
    assetsRoot: path.resolve(__dirname, '../dist'),
    assetsSubDirectory: 'static',
    assetsPublicPath: '/',
    productionSourceMap: false,
    devtool: 'source-map',
    productionGzip: false,
    productionGzipExtensions: ['js', 'css'],
    bundleAnalyzerReport: process.env.npm_config_report || false,
    generateAnalyzerReport: process.env.npm_config_generate_report || false
  }
}
