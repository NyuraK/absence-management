import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../components/Login'
import Calendar from '../components/Calendar'
import store from '../store/index'

Vue.use(VueRouter);
const router = new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/',
            name: 'Login',
            component: Login
        },
        {
            path: '/calendar',
            name: 'calendar',
            component: Calendar,
        },
    ]
});

const openRoutes = ['Login', 'Calendar'];

// router.beforeEach((to, from, next) => {
//
//     if (openRoutes.includes(to.name)) {
//         next()
//     } else if (store.getters.isAuthenticated) {
//         next()
//     } else {
//         next('/')
//     }
//
// });

export default router