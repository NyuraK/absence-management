import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../components/Login'
import Calendar from '../components/Calendar'
import Home from "../components/Home";
import NotFound from "../components/NotFound";
import store from "../store/index"

import Users from "../components/Users";
import UserEdit from "../components/UserEdit";



Vue.use(VueRouter);
const router = new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/users',
            component: Users,
            meta: {nonRequiresAuth: true, rule:'isPublic'}

        },
        {
            path: '/users/:id',
            component: UserEdit,
            meta: {nonRequiresAuth: true, rule:'isPublic'}

        },
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
            path: "/error",
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