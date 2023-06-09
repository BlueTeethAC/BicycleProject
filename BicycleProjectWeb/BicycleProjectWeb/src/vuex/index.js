import Vue from "vue"
import Vuex from "vuex"

import createVuexAlong from 'vuex-along' //vuex-along 让 vuex 中的数据不会被刷新掉

Vue.use(Vuex)


//const 是常量
export default new Vuex.Store({
  state:{ //配置状态值
    userId:null, //用户id
    username:null, //用户名
    role:0, //角色类型 0：顾客  1：管理员  2：最高管理员
    shoppingCart:null //购物车
  },
  mutations:{ //提供修改值的方法
    updateUserId(state,userId){ //修改 userId
      state.userId = userId;
    },
    updateUsername(state,username){ //修改 username
      state.username = username;
    },
    updateRole(state,role){ //修改 role
      state.role = role;
    },
    updateShoppingCart(state,shoppingCart){ //修改购物车
      state.shoppingCart = shoppingCart;
    }
  },
  plugins: [createVuexAlong()]//vuex-along 让vuex 的数据不会被刷新掉
})
