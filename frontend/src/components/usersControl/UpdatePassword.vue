<template>
    <div>
        <v-btn dark color="teal lighten-1" v-on:click="dialog = ! dialog">Change password</v-btn>
        <v-dialog v-model="dialog" max-width="600px">
            <v-card>
                <v-card-text>
                    <v-alert v-model="alert" dismissible type="error" class="alert alert-danger" outline>
                        Your password was not changed. New password and repeated new password did not match.
                    </v-alert>
                    <v-form>
                        <v-container fluid>
                            <v-layout column wrap>
                                <v-flex xs12 sm6>
                                    <v-text-field
                                            v-model="passwordCur"
                                            :append-icon="show1 ? 'visibility' : 'visibility_off'"
                                            :type="show1 ? 'text' : 'password'"
                                            name="input-10-1"
                                            label="Current password"
                                            counter
                                            @click:append="show1 = !show1"
                                    ></v-text-field>
                                </v-flex>
                                <v-flex xs12 sm6>
                                    <v-text-field
                                            v-model="passwordNew"
                                            :append-icon="show2 ? 'visibility' : 'visibility_off'"
                                            :type="show2 ? 'text' : 'password'"
                                            name="input-10-1"
                                            label="New password"
                                            counter
                                            @click:append="show2 = !show2"
                                    ></v-text-field>
                                </v-flex>
                                <v-flex xs12 sm6>
                                    <v-text-field
                                            v-model="passwordNewRep"
                                            :append-icon="show3 ? 'visibility' : 'visibility_off'"
                                            :type="show3 ? 'text' : 'password'"
                                            name="input-10-1"
                                            label="Repeat new password"
                                            counter
                                            @click:append="show3 = !show3"
                                    ></v-text-field>
                                </v-flex>
                            </v-layout>
                        </v-container>
                    </v-form>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn flat color="error" @click="dialog = false">Cancel</v-btn>
                        <v-btn flat color="primary" @click="save">Save</v-btn>
                    </v-card-actions>
                </v-card-text>
            </v-card>
        </v-dialog>
    </div>
</template>

<script>

    import {instance} from "../../Api";

    export default {


        name: "UpdatePassword",

        data() {
            return {
                alert: false,
                dialog: false,
                passwordCur: '',
                passwordNew: '',
                passwordNewRep: '',
                show1: false,
                show2: false,
                show3: false,
            }
        },

        methods: {
            save() {
                if (this.passwordNew === this.passwordNewRep) {
                    instance.put('users/password/' + this.$router.currentRoute.params['id'], this.passwordNew);
                    this.dialog = false;
                } else {
                    this.alert = true;
                }
            }
        }
    }
</script>

<style scoped>

</style>