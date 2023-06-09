<template>

  <div id="head1">
    <!--顶部导航栏-->
    <el-menu
      :default-active="activeIndex2"
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b">

      <el-menu-item index="1" v-on:click="$router.push('/indexView')">&nbsp;&nbsp;&nbsp;&nbsp;首页&nbsp;&nbsp;&nbsp;&nbsp;</el-menu-item>

      <el-submenu index="2">

        <template slot="title">消费服务</template>

        <!--<el-menu-item index="2-1">选项1</el-menu-item>-->
        <el-menu-item index="2-1" v-on:click="$router.push('/shoppingView')">商品页面</el-menu-item>
        <!--<el-menu-item index="2-2">选项3</el-menu-item>-->
        <el-menu-item index="2-2" v-on:click="$router.push('/userAdmin/addRepairList')">维修服务</el-menu-item>

        <el-submenu index="2-3">

          <template slot="title">订单管理</template>
          <el-menu-item index="2-4-1" v-on:click="$router.push('/shoppingList')">消费清单</el-menu-item>
          <el-menu-item index="2-4-2" v-on:click="$router.push('/userAdmin/repairService')">维修服务清单</el-menu-item>
          <!--<el-menu-item index="2-4-3">选项3</el-menu-item>-->

        </el-submenu>
      </el-submenu>

      <el-menu-item index="3" v-on:click="$router.push('/shoppingCart')">我的购物车</el-menu-item>

      <el-menu-item index="4" v-if="false">订单管理</el-menu-item>

      <el-menu-item index="5" v-if="this.role >=1" v-on:click="$router.push('/userAdminView')">管理员界面</el-menu-item>

      <!--<el-menu-item index="5" disabled>消息中心</el-menu-item>-->
      <!--<el-menu-item index="6"><a href="https://www.ele.me" target="_blank">订单管理</a></el-menu-item>-->

      <el-submenu index="7" style="float:right" v-if="this.username != null">

        <template slot="title">{{this.username}}</template>

        <el-menu-item index="7-1">个人设置</el-menu-item>
        <el-menu-item index="7-2" v-on:click="logOff()">注销</el-menu-item>

      </el-submenu>
      <el-menu-item index="8" style="float:right" v-if="this.username == null" v-on:click="$router.push('/loginView/register')">注册</el-menu-item>
      <el-menu-item index="9" style="float:right" v-if="this.username == null" v-on:click="$router.push('/loginView/login')">登录</el-menu-item>

    </el-menu>
  </div>

</template>

<script>
import vuex from "@/vuex"

export default {
  name: "head1",
  data(){
    return{
      userId:vuex.state.userId,
      username:vuex.state.username,
      role:vuex.state.role
    }
  },
  methods:{
    //注销
    logOff(){
      vuex.commit("updateUserId",null);//将userId添加到状态
      vuex.commit("updateUsername",null);//将updateUsername添加到状态
      vuex.commit("updateRole",0);//将updateRole添加到状态
      this.userId=null;
      this.username=null;
      this.role=0;
      this.$router.push("/indexView");
      this.$router.go(0);
    }
  }
}
</script>

<style scoped>
#head1 {
  margin-top: -50px;
  width: auto;
}
</style>
