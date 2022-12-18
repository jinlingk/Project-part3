const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  publicPath: process.env.NODE_ENV ==="development"
  ? '/'
  : '/src/',
  devServer:{
    hot: true,
    open: true,
    port: 8888,
    host: "localhost"
  }
})
