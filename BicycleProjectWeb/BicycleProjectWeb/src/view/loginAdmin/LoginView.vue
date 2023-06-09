<template>

  <div id="loginView">
    <div class="div1">
      <img src="@/assets/loginView2.png" width="100%">
    </div>

    <div>
      <router-view></router-view>
    </div>

  </div>

</template>

<script>
export default {
  name: "LoginView",
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

#loginView{
  margin: 0;
  padding: 0;
  border: 0;
  height: 100%;
  width: 100%;
}

.div1 {
  width: 50%;
  height: 100%;
  margin: 0;
  /*border: 1px solid black;*/
  border: 0;
  box-sizing: border-box;
  float: left;
  background-color: yellow;
}

</style>

