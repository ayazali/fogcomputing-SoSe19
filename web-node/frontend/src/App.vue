<template>
  <div id="app">
    <img alt="Vue logo" src="./assets/logo.png">
    <welcome msg="Welcome to FogComputing App"/>
    <main-view msg="I am mainview"/>
  </div>
</template>

<script>
import Welcome from './components/Welcome.vue'
import MainView from './components/MainView.vue'

export default {
  name: 'app',
  components: {
    Welcome,
    MainView
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
