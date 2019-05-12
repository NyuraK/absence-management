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
        team_msg: '',
        user_info: 'My profile' || localStorage.getItem('user_info'),
    },

    mutations: {
        authSuccess(state, token) {
            state.token = token;
            state.isAuth = true;
        },

        teamFailure(state, msg) {
            state.team_msg = msg;
        },

        authLogout(state) {
            state.token = '';
            state.isAuth = false;
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
                        instance.defaults.headers.Authorization = accessToken;
                        let ca = accessToken.substring(7);
                        let decodedValue = jwt.decode(ca, {algorithm: 'HS512'});
                        localStorage.setItem('user', decodedValue.authorities);
                        localStorage.setItem('username', decodedValue.sub);
                        resolve(response);
                    })
                    .catch((error) => {
                        localStorage.removeItem('token');
                        reject(error);
                    })

            })
        },
        getTeam({commit}) {
            instance.get('/users/team').then((resp) => {
                localStorage.setItem('team', resp.data.name);
                localStorage.setItem('teamId', resp.data.teamId);
            }).catch((err) => {
                if (err.response.status === 500)
                    commit('teamFailure', err.response.data);
            });
        },

        getUserInfo({commit}) {
            return new Promise((resolve, reject) => {
                instance.get("/users/name").then(resp => {
                    localStorage.setItem('user_info', resp.data);
                    resolve(resp);
                }).catch(err => {
                    console.log(err);
                    reject(err);
                });
            })
        },

        userLogOut({commit}) {
            commit('authLogout');
            localStorage.removeItem('token');
            localStorage.setItem('user', 'public');
            localStorage.removeItem('username');
            localStorage.removeItem('teamId');
            localStorage.removeItem('team');
            localStorage.removeItem('user_info');
        }
    },

    getters: {
        isAuthenticated: state => !!state.token,
        isToken: state => state.token,
        teamId: state => state.userTeam.teamId,
        team: state => state.userTeam,
        team_msg: state => state.team_msg,
        user: state => state.user_info,
    },
});

export default store