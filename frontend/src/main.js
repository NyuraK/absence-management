import App from './App.vue'
import Vue from 'vue';
import VCalendar from 'v-calendar';
import 'v-calendar/lib/v-calendar.min.css';
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import Router from 'vue-router'
import Calendar from "./components/Calendar";
import Login from "./components/Login"

// Use v-calendar, v-date-picker & v-popover components
Vue.use(VCalendar, {
  firstDayOfWeek: 2,  // Monday
});

Vue.use(BootstrapVue);

Vue.use(Router);
const router = new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/calendar',
      name:'calendar',
      component: Calendar,
      meta: { requiresAuth: true}
    },
  ]
});

Vue.config.productionTip = false;

new Vue({
  render: h => h(App),
  router
}).$mount('#app');

