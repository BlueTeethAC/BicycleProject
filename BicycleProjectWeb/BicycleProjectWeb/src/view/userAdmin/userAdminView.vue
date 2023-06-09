<template>

  <div>
    <!--  导航栏模块  -->
    <div class="head">
      <!--head-->
      <head1></head1>
    </div>

    <!-- 侧边栏模块 -->
    <div>
      <sidebar></sidebar>
    </div>


    <!-- 路由模块展示 -->
    <div id="head3">
      <el-main style="display: inline">
        <router-view></router-view>
      </el-main>
    </div>


  </div>

</template>

<script>
//引入组件
import head1 from "@/view/indexAdmin/components/head1.vue";
import sidebar from "@/view/userAdmin/components/sidebar.vue";
import userController from "@/view/userAdmin/userController/userController.vue"

export default {
  name: "userAdminView",
  components:{
    head1,
    sidebar,
    userController
  }
}
</script>

<style scoped>

html,body{
  padding: 0;
  margin: 0;
  border: 0;
  width: 100%;
  height: 100%;
}
.head{
  width: 100%;
  height: 60px;
  position: relative;
  margin-top: 80px;
  /*background-color: whitesmoke;*/
  width: auto;
}

#head3{
  margin-right: 10px;
  width: 1600px;
  display: inline;
  float: right;
}

</style>
