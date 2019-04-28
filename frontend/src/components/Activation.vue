<template>
    <div id="login-form">
        <b-form>
            <div v-if="show" class="alert alert-danger">
                {{warning}}
            </div>
            <b-form-group id="exampleInputGroup1" label="Insert new password:" label-for="exampleInput1">
                <b-form-input
                        id="exampleInput1"
                        type="password"
                        v-model="password"
                        required
                        placeholder="Enter password"/>
            </b-form-group>

            <b-form-group id="exampleInputGroup2" label="Repeat password:" label-for="exampleInput2">
                <b-form-input
                        id="exampleInput2"
                        type="password"
                        v-model="repeatPassword"
                        required
                        placeholder="Repeat password"/>
            </b-form-group>

        </b-form>
        <b-button type="submit" variant="primary" v-on:click="change">Change</b-button>

        <!--<transition>
            <b-modal id="modal1" title="BootstrapVue" v-if="!success">
                <p class="my-4">Wrong login or password!</p>
            </b-modal>
        </transition>-->
    </div>
</template>

<script>
    import {instance} from "../Api";

    export default {
        name: "Activation",
        data() {
            return {
                repeatPassword: '',
                password: '',
                user: null,
                warning: '',
                show: false,
                isChecked: false,
                userInfo: []
            }
        },
        created() {
            instance.get("/users/userByCode/" + this.$router.currentRoute.params['code']).then(res => {
                let name = res.data;
                this.user = name;
                this.isChecked = true;
                console.log("USER IS " + this.user);
                console.log("CODE " + this.$router.currentRoute.params['code']);
            }).catch((err) => {
                console.log(err);
            });
        },
        methods: {
            change: function () {
                if (this.password === '' || this.repeatPassword === '') {
                    this.warning = "You need to fill empty fields!";
                    this.show = true;
                } else if (this.password != this.repeatPassword) {
                    this.warning = "Your passwords do not match!";
                    this.show = true;
                } else {
                    this.show = false;
                    if (this.user != null) {
                        console.log("PASSWORD " + this.password);
                        console.log("USER " + this.user);
                        this.userInfo[0] = this.password;
                        this.userInfo[1] = this.user;
                        instance.patch("/users/changePassword", this.userInfo)
                            .catch((err) => {
                                console.log(err);
                            });

                    } else {
                        this.warning = "You have no access to this operation!";
                        this.show = true;
                    }

                    this.password = '';
                    this.repeatPassword = '';
                }
                this.$router.push('/home');
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