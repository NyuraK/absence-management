var api = Vue.resource('/login');

Vue.component('input-form', {
    data: function(){
        return {
            login: '',
            password: ''
        }
    },
    template: '<div>' +
        '<input type="email" placeholder="login" v-model="login"/> ' +
        '<input type="password" placeholder="password" v-model="password"/>' +
        '<input type="button" value="Sign in" @click="check"></div>',
      methods: {
        check: function () {
            var user = {login: this.login, password: this.password};
            api.save({}, user);
            this.login = '';
            this.password='';
        }
    }
})

var app = new Vue({
    el: '#app',
    template: '<input-form/>'
});