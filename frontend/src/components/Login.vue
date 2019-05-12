<template>
    <div id="login-form">
        <v-alert v-model="mismatch" dismissible type="error" class="alert alert-danger" outline>
            Sorry, but your password or your surname is incorrect.
        </v-alert>
        <b-form>
            <b-form-group
                    id="exampleInputGroup1"
                    label="Username:"
                    label-for="exampleInput1"
                    description="Enter your username"
            >
                <b-form-input
                        id="exampleInput1"
                        type="text"
                        v-model="username"
                        required
                        placeholder="Enter username"/>
            </b-form-group>

            <b-form-group id="exampleInputGroup2" label="Your password:" label-for="exampleInput2">
                <b-form-input
                        id="exampleInput2"
                        type="password"
                        v-model="password"
                        required
                        placeholder="Enter password"/>
            </b-form-group>
        </b-form>
        <b-button type="submit" variant="primary" v-on:click="check">Log in</b-button>
        <br><br>
        <SendByEmail></SendByEmail>
    </div>
</template>

<script>

    import SendByEmail from "./SendByEmail";

    export default {
        components: {SendByEmail},
        name: "Login",
        data() {
            return {
                username: '',
                password: '',
                mismatch: false,
            }
        },
        methods: {
            check: function () {
                this.$store.dispatch('login', {username: this.username, password: this.password}).then(() => {
                    this.$acl.change(localStorage.getItem('user'));
                    this.$store.dispatch('getTeam');
                    this.$store.dispatch('getUserInfo').then(()=>this.$router.push('/home'));
                    this.mismatch=false;
                }).catch((err) => {
                    console.log(err);
                    this.mismatch=true;
                });
                this.username = '';
                this.password = '';
            },
            async jj () {
                this.$store.dispatch('getTeam');
                this.$store.dispatch('getUserInfo');
            },
        },
    }
</script>

<style scoped>
    #login-form {
        display: inline-block;
        max-width: 350px;
        text-align: center;
        position: absolute;
        top: 50px;
        left: 0;
        bottom: 0;
        right: 0;
        margin: auto;

    }
</style>