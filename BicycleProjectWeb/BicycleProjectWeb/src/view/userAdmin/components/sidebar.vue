<template>

  <div id="head2">
    <!--侧边栏-->
    <el-row class="tac" id="test1">
      <el-col :span="12" id="test2">
        <!--<h5>自定义颜色</h5>-->
        <el-menu
          default-active="2"
          class="el-menu-vertical-demo"
          @open="handleOpen"
          @close="handleClose"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b"
          style="height: 100vh"
        >
          <!--  在 el-menu 中添加 style="height: 100vh" 让侧边栏的长度充满整个页面 -->

          <!-- 最高管理员功能区 -->
          <el-menu-item index="1" v-if="role == 2">
            <i class="el-icon-user"></i>
            <span slot="title" v-on:click="$router.push('/userAdminView/userController')">用户管理</span>
          </el-menu-item>

          <el-menu-item index="2" v-if="role == 2">
            <i class="el-icon-goods"></i>
            <span slot="title" v-on:click="$router.push('/userAdminView/commodityController')" >商品管理</span>
          </el-menu-item>

          <el-menu-item index="3" v-if="role == 2">
            <i class="el-icon-setting"></i>
            <span slot="title" v-on:click="$router.push('/userAdminView/repairItemsController')">维修项目管理</span>
          </el-menu-item>

          <!-- 管理员功能区 -->
          <el-menu-item index="4" v-if="role == 1">
            <i class="el-icon-s-order"></i>
            <span slot="title" v-on:click="$router.push('/userAdmin/repairServiceAdmin')">维修单处理</span>
          </el-menu-item>

          <el-menu-item index="5" v-if="role == 1">
            <i class="el-icon-s-management"></i>
            <span slot="title" v-on:click="$router.push('/shoppingListAdmin')">购物单处理</span>
          </el-menu-item>

          <!-- 普通用户功能区 -->
          <el-menu-item index="6" v-if="role == 0">
            <i class="el-icon-setting"></i>
            <span slot="title" v-on:click="$router.push('/userAdmin/repairService')">维修申请</span>
          </el-menu-item>

          <el-menu-item index="7" v-if="role == 0">
            <i class="el-icon-shopping-cart-1"></i>
            <span slot="title" v-on:click="$router.push('/shoppingCart')">购物车</span>
          </el-menu-item>

          <el-menu-item index="8" v-if="role == 0">
            <i class="el-icon-goods"></i>
            <span slot="title" v-on:click="$router.push('/shoppingList')">购物记录</span>
          </el-menu-item>


          <!-- 个人设置 -->
          <el-menu-item index="10">
            <i class="el-icon-user-solid"></i>
            <span slot="title">个人设置</span>
          </el-menu-item>

        </el-menu>
      </el-col>
    </el-row>

  </div>

</template>

<script>
import vuex from "@/vuex"

//侧边栏模块
export default {
  name: "sidebar",
  data(){
    return{
      userId:vuex.state.userId,
      username:vuex.state.username,
      role:vuex.state.role
    }
  }
}
</script>

<style scoped>
#head2 {
  width: 225px;
  float: left;
}
#test1 {
  padding-right: 0px;
  width: 225px;
}
#test2 {
  margin-right: 0px;
  width: 225px;
}
</style>
