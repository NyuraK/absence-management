import Vuex from 'vuex'
import Vue from 'vue'
import {instance} from "../Api";

Vue.use(Vuex);

const store = new Vuex.Store({

    state: {
        token: localStorage.getItem('token') || '',
        isAuth: false
    //    probably should store a role?


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
                instance.post('/login', payload)
                    .then((response) => {
                        let accessToken = response.headers['authorization'];
                        context.commit('authSuccess', accessToken);
                        localStorage.setItem('token', accessToken);
                        // instance.defaults.headers.common['Authorization'] = accessToken;
                        let role = response.headers['role'];
                        localStorage.setItem('user', role);
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
        }

    },


    getters: {
        isAuthenticated: state => !!state.token,
            // state.isAuth,
        isToken: state => state.token
    }

});

export default store