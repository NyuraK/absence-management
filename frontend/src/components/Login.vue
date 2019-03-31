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
                login: '',
                password: '',
            }
        },
        methods: {
            check: function () {
                this.$store.dispatch('login',{login:this.login, password:this.password}).then(()=>{
                    this.$router.push('/secured');
                })

                this.login='';
                this.password='';

            }
        }
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