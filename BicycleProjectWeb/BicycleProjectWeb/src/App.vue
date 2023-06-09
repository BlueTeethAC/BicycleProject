<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
//import HelloWorld from './components/HelloWorld'
import vuex from "@/vuex"

export default {
  name: 'App',
  components: {
  },
}
</script>

<style>

html,body{
  height: 100%;
  width: 100%;
  margin: 0;
  background-color: #E4E7ED;
}

#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  /*margin-top: 60px;*/
  margin: 0;
  padding: 0;
  border: 0;
  height: 100%;
  width: 100%;
}
</style>
