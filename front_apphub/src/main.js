import Vue from 'vue'
import App from './App.vue'
import store from './store'
import router from './router'
import './plugins/element.js'
// global style
import './assets/css/global.css'
// icons
import './assets/icons/iconfont.css'
// import md5 lib
import md5 from 'js-md5';
Vue.prototype.$md5 = md5
//sha 
import jsSHA from "jssha"      //module
// const jsSHA = require("jssha")

Vue.prototype.$sha224  = function(password){
 
  const shaObj = new jsSHA("SHA-224","TEXT",{encoding:"UTF8"})
  shaObj.update(password)
  let data = shaObj.getHash("HEX")
  console.log(data)
  return data
 }

Vue.prototype.$server_url = "http://120.48.116.243:8080"
// import axios
import axios from 'axios'
Vue.prototype.$http = axios
// Initial configuration of axios
axios.defaults.baseURL = "http://120.48.116.243:8080"
axios.defaults.headers = {
  "Content-Type": "application/json;charset=UTF-8",
},

Vue.config.productionTip = false

new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app')
