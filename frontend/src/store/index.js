import Vuex from 'vuex'
import Vue from 'vue'
import axios from 'axios'
import {instance} from "../Api";

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
                        let role = response.headers['role'];
                        let username = response.headers['username'];
                        localStorage.setItem('user', role);
                        localStorage.setItem('username', username);
                        resolve(response);
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
        }

    },


    getters: {
        isAuthenticated: state => !!state.token,
        isToken: state => state.token
    }

});

export default store