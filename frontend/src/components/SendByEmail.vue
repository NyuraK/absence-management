<template>
    <div>
        <a class="badge badge-light" style='font-size:1.0em' v-on:click="dialog = ! dialog">Forgot your password?</a>
        <v-dialog v-model="dialog" max-width="600px">
            <v-card>
                <v-card-text>
                    <v-alert v-model="mismatch" dismissible type="error" class="alert alert-danger" outline>
                        Your email was not recognised. Insert email which is currently using on your account.
                    </v-alert>
                    <v-alert v-model="success" dismissible type="primary" class="alert alert-info" outline>
                        A message, which describes the next steps, was successfully sent to your Email.
                    </v-alert>
                    <v-alert v-model="process" dismissible type="warning" class="alert alert-warning" outline>
                        Processing...
                    </v-alert>
                    <v-form>
                        <v-container fluid>
                            <v-layout column wrap>
                                <v-flex xs12 sm6>
                                    <v-text-field v-model="email" :rules="[rules.required, rules.email]" label="Insert your E-mail"></v-text-field>
                                </v-flex>
                            </v-layout>
                        </v-container>
                    </v-form>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn flat color="error" @click="dialog = false">Close</v-btn>
                        <v-btn flat color="primary" @click="send">Send</v-btn>
                    </v-card-actions>
                </v-card-text>
            </v-card>
        </v-dialog>
    </div>
</template>

<script>

    import {instance} from "../Api";

    export default {
        name: "SendByEmail",
        data() {
            return {
                rules: {
                    required: value => !!value || 'Required.',
                    email: value => {
                        const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
                        return pattern.test(value) || 'Invalid e-mail.'
                    }
                },
                dialog: false,
                mismatch: false,
                success: false,
                process: false,
                email: '',
            }
        },

        methods: {
            send() {
                this.success=true;
                instance.get("users/sendMailForgot",{
                    params: {
                        email: this.email,
                    }
                }).then(res => {
                    this.process=false;
                    this.success=true;
                    this.mismatch=false;
                    console.log(res.data);
                    if (res.data===false){
                        this.mismatch=true;
                        this.success=false;
                    } else if (res.data===true){
                        this.success= true;
                    }
                }).catch(err => {
                    console.log(err);
                });
            }
        }
    }
</script>

<style scoped>

</style>