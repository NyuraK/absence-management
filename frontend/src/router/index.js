import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../components/Login'
import Calendar from '../components/Calendar'
import Home from "../components/Home";
import NotFound from "../components/NotFound";
import Requests from "../components/Requests"
import store from "../store/index"

import Users from "../components/usersControl/Users";
import UserEdit from "../components/usersControl/UserEdit";
import Teams from "../components/teamsControl/Teams";
import TeamEdit from "../components/teamsControl/TeamEdit";

import Timeline from "../components/Timeline"


Vue.use(VueRouter);
const router = new VueRouter({
    mode: 'history',
    routes: [
        {
            name: 'users',
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
            path: '/teams',
            component: Teams,
            meta: {nonRequiresAuth: true, rule:'isPublic'}

        },
        {
            path: '/teams/:id',
            component: TeamEdit,
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
            path: '/requests',
            name: 'requests',
            component: Requests,
            meta: {rule: 'isManager'}
        },
        {
            path: '/timeline',
            name: 'Timeline',
            component: Timeline,
            meta: {rule: 'isLoggedUser'}
        },
        {
            path: "/*",
            component: NotFound,
            meta: {rule: '*'}
        },

    ]
});

router.beforeEach((to, from, next) => {
    const isLoginPage = to.matched.some(record => record.meta.loginPage);
    const requiresAuth = !to.matched.some(record => record.meta.nonRequiresAuth);
    const isAuthenticated = store.getters.isAuthenticated;
    const isAllowed = to.matched.some(record => record.meta.rule);
    if (requiresAuth && !isAuthenticated) {
        next("/")
    } else if (isLoginPage && isAuthenticated) {
        router.push('/home')
    }
    else if (isAllowed) {
        next()
    }

});


export default router