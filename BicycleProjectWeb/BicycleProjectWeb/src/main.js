// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'

//引入 Axios 和 VueAxios
import axios from "axios";
import VueAxios from "vue-axios";

//扫描全部路由
import router from "./router"
//element-ui 可以直接导入
import ElementUI from "element-ui"
//导入 element-ui 的 ss
import 'element-ui/lib/theme-chalk/index.css';
//引入 vuex
import Vuex from "./vuex"

Vue.config.productionTip = false

//使用router 和 ElementUI
Vue.use(router);
Vue.use(ElementUI);
Vue.use(VueAxios,axios);//顺序必须先 VueAxios 再 axios
//Vue.use(Vuex)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  //用了 Element-UI 就写这句话   render:h => h(App)
  render:h => h(App) //Element-UI

})
