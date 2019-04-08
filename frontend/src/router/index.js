import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../components/Login'
import Calendar from '../components/Calendar'
import Home from "../components/Home";
import NotFound from "../components/NotFound";
import Requests from "../components/Requests"
import store from "../store/index"

Vue.use(VueRouter);
const router = new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/',
            name: 'Login',
            component: Login,
            meta: {loginPage: true, nonRequiresAuth: true, rule: 'isPublic'}
        },
        {
            path: '/calendar',
            name: 'Calendar',
            component: Calendar,
            meta: {nonRequiresAuth: true, rule: 'isPublic'}
        },
        {
            path: '/home',
            name: 'home',
            component: Home,
            meta: {rule: 'isLoggedUser'}
        },
        {
            path: '/requests',
            name: 'requests',
            component: Requests,
            meta: {rule: 'isManager'}
        },
        {
            path: "/*",
            component: NotFound,
            meta: {rule: '*'}
        }
    ]
});

router.beforeEach((to, from, next) => {
    const isLoginPage = to.matched.some(record => record.meta.loginPage);
    const requiresAuth = !to.matched.some(record => record.meta.nonRequiresAuth);
    const isAuthenticated = store.getters.isAuthenticated;
    if (requiresAuth && !isAuthenticated) {
        next("/")
    } else if (isLoginPage && isAuthenticated) {
        router.push('/home')
    }
    next()
});

export default router