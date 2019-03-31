import Vuex from 'vuex'
import Vue from 'vue'
import {instance} from "../Api";

Vue.use(Vuex)

export default new Vuex.Store({

    state: {
        token: localStorage.getItem('token') || '',
        status: '',
        loader: false

    },

    mutations: {
        authSuccess(state, token) {
            state.token = token;
            state.status = 'success';
        },
        LOADER(state, payload) {
            state.loader = payload;
        },

        authError(state) {
            state.token = '';
            state.status = 'error';
        },
        authLogout(state) {
            state.token = ''
        }
    },

    actions: {
        login(context, payload) {
            let params = new URLSearchParams()
            params.append('login', payload.login)
            params.append('password', payload.password)
            console.log(payload)
            return new Promise((resolve, reject) => {

                instance.post('/signin', params)
                    .then((response) => {
                        let accessToken = response.data.auth.token;
                        console.log(response);
                        context.commit('authSuccess', accessToken);
                        localStorage.setItem('token', accessToken);
                        instance.defaults.headers.common['Authorization'] = "Bearer " + accessToken;
                        resolve(response);

                    })
                    .catch((error) => {
                        localStorage.removeItem('token');
                        context.commit('authError')
                        console.log(error);
                        console.log("no profit =(")
                        reject(error);
                    })

            })

        },

    },


    getters: {
        isAuthenticated: state => !!state.token,
        authStatus: state => state.status,
        menus: (state, getters) => {
            if (getters.isAuthenticated) {
                return [{
                    name: "Logout", route: "Logout"
                }]
            }
            return [
                {name: "Login", route: "Login"},
            ];
        }
    }

})