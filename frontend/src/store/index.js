import Vuex from 'vuex'
import Vue from 'vue'
import axios from 'axios'
import {instance} from '../Api'
import jwt from 'jsonwebtoken'

Vue.use(Vuex);

const store = new Vuex.Store({

    state: {
        token: localStorage.getItem('token') || '',
        isAuth: false,
    },

    mutations: {
        authSuccess(state, token) {
            state.token = token;
            state.isAuth = true;
        },

        authLogout(state) {
            state.token = '';
            state.isAuth= false;
        }
    },

    actions: {
        login(context, payload) {
            return new Promise((resolve, reject) => {
                axios.post('/login', payload)
                    .then((response) => {
                        let accessToken = response.headers['authorization'];
                        context.commit('authSuccess', accessToken);
                        localStorage.setItem('token', accessToken);
                        instance.defaults.headers.common['Authorization'] = accessToken;
                        let ca = accessToken.substring(7);
                        let decodedValue = jwt.decode(ca, {algorithm : 'HS512'});
                        localStorage.setItem('user', decodedValue.authorities);
                        localStorage.setItem('username', decodedValue.sub);
                    })
                    .catch((error) => {
                        localStorage.removeItem('token');
                        reject(error);
                    })

            })
        },
        userLogOut ({commit}) {
            commit ('authLogout');
            localStorage.removeItem('token');
            delete instance.defaults.headers.common['Authorization'];
            localStorage.setItem('user', 'public');
            localStorage.removeItem('username');
        },

    },

    getters: {
        isAuthenticated: state => !!state.token,
        isToken: state => state.token
    },
});

export default store