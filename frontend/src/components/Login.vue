<template>
    <div id="login-form">
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

        </b-form>
        <b-button type="submit" variant="primary" v-on:click="check">Log in</b-button>

        <transition>
            <b-modal id="modal1" title="BootstrapVue" v-if="!success">
                <p class="my-4">Wrong login or password!</p>
            </b-modal>
        </transition>
    </div>
</template>

<script>

    export default {
        name: "Login",
        data () {
            return {
                username: '',
                password: '',
            }
        },
        methods: {
            check: function () {
                this.$store.dispatch('login',{username:this.username, password:this.password}).then(()=>{
                    console.log("from login " + localStorage.getItem('user'));
                    this.$acl.change(localStorage.getItem('user'));
                    this.$router.push('/home');
                });

                this.username='';
                this.password='';
            }
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