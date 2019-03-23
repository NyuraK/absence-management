<template>
    <div id="login-form">
        <b-form @submit="check">
            <b-form-group
                    id="exampleInputGroup1"
                    label="Username:"
                    label-for="exampleInput1"
                    description="Enter your username"
            >
                <b-form-input
                        id="exampleInput1"
                        type="text"
                        v-model="login"
                        required
                        placeholder="Enter email" />
            </b-form-group>

            <b-form-group id="exampleInputGroup2" label="Your password:" label-for="exampleInput2">
                <b-form-input
                        id="exampleInput2"
                        type="password"
                        v-model="password"
                        required
                        placeholder="Enter password" />
            </b-form-group>

            <b-button type="submit" variant="primary">Log in</b-button>
        </b-form>
    </div>
</template>

<script>
    import api from '../Api.js'

    export default {
        name: "Login",
        data () {
            return {
                login: '',
                password: ''
            }
        },
        methods: {
            check: function () {
                let user = {login: this.login, password: this.password};
                api.login(user);
                this.login='';
                this.password='';
                this.$router.push('/calendar');
            }
        }
    }
</script>

<style scoped>
    #login-form {
        display: inline-block;
        max-width: 500px;
    }
</style>