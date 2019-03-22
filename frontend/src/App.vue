<template>
  <div id="app">
    <Login/>
    <!--чтобы туды передать параметры, надо так: <Login [имя параметра]=значение-->
    <Calendar/>
  </div>
</template>

<script>
import Login from './components/Login.vue'
import Calendar from './components/Calendar.vue'
export default {
  name: 'app',
  components: {
    Login,
    Calendar
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
