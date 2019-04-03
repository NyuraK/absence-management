import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../components/Login'
import Calendar from '../components/Calendar'
import Home from "../components/Home";
import NotFound from "../components/NotFound";
import store from "../store/index"

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
            name: 'Calendar',
            component: Calendar,
        },
        {
            path: '/home',
            name: 'home',
            component: Home,
        },
        {
            path: "*",
            component: NotFound
        }
    ]
});

const openRoutes = ['Login', 'Calendar'];

router.beforeEach((to, from, next) => {

    console.log("isAuth " + store.getters.isAuthenticated + " " + store.getters.isToken);
    if (openRoutes.includes(to.name)) {
        next()
    } else if (store.getters.isAuthenticated) {
        next()
    } else {
        next('/')
    }
});

export default router