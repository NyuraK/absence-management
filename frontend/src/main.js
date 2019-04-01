import App from './App.vue'
import Vue from 'vue';
import store from './store/index'
import router from './router/index'
import {instance} from "./Api";
import VCalendar from 'v-calendar';
import 'v-calendar/lib/v-calendar.min.css';
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

window.token = localStorage.getItem('token');
window.user = localStorage.getItem('user');

// Use v-calendar, v-date-picker & v-popover components
Vue.use(VCalendar, {
  firstDayOfWeek: 2,  // Monday
});

Vue.use(Vuetify);
Vue.use(BootstrapVue);

Vue.config.productionTip = false;

new Vue({
  render: h => h(App),
  router, store, instance
}).$mount('#app');

