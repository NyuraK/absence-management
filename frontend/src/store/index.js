import Vuex from 'vuex'
import Vue from 'vue'
import {instance} from "../Api";

Vue.use(Vuex);

const store = new Vuex.Store({

    state: {
        token: localStorage.getItem('token') || '',
        isAuth: false,
    //    probably should store a role?

    },

    mutations: {
        authSuccess(state, token) {
            state.token = token;
            state.status = true;
        },

        authLogout(state) {
            state.token = '';
            state.status= false;
        }
    },

    actions: {
        login(context, payload) {
            return new Promise((resolve, reject) => {
                instance.post('/login', payload)
                    .then((response) => {
                        let accessToken = response.headers['authorization'];
                        console.log(accessToken);
                        context.commit('authSuccess', accessToken);
                        localStorage.setItem('token', accessToken);
                        instance.defaults.headers.common['Authorization'] = accessToken;
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
            delete instance.defaults.headers.common['Authorization'] ;
        }

    },


    getters: {
        isAuthenticated: state => !!state.token,
        isToken: state => state.token
    }

});

export default store