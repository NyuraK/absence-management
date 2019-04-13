<template>
    <div>
        <v-btn small color="primary" dark @click="dialog = !dialog">Add user</v-btn>
        <v-dialog v-model="dialog" max-width="600px">
            <v-card>
                <v-card-text>
                    <h3 class="headline mb-0">Enter user details</h3>
                    <v-text-field label="First name" v-model="name"></v-text-field>
                    <v-text-field label="Surname" v-model="surname"></v-text-field>
                    <v-text-field label="Family name" v-model="familyName"></v-text-field>
                    <v-select :items="roles" label="Role" v-model="role"></v-select>
                    <v-text-field v-model="email" :rules="[rules.required, rules.email]" label="E-mail"></v-text-field>
                    <v-text-field label="Login" v-model="login"></v-text-field>
                    <v-text-field label="Team id" v-model="teamId"></v-text-field>
                    <v-text-field label="Rest Days" v-model="restDays"></v-text-field>
                    <v-text-field label="Hire date" type="date" v-model="hireDate"></v-text-field>
                    <v-text-field label="Phone number" v-model="phoneNumber" mask="+# (###) ### - ##-##"></v-text-field>
                    <v-text-field label="Description" v-model="description"></v-text-field>
                    <!--<small class="grey&#45;&#45;text">* This doesn't actually save.</small>-->
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn flat color="error" @click="dialog = false">Cancel</v-btn>
                        <v-btn flat color="primary" @click="save">Add</v-btn>
                    </v-card-actions>
                </v-card-text>
            </v-card>
        </v-dialog>
    </div>
</template>

<script>

    import {instance} from '../../Api.js';

    export default {
        name: "NewUser",
        data() {
            return {

                rules: {
                    required: value => !!value || 'Required.',
                    email: value => {
                        const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
                        return pattern.test(value) || 'Invalid e-mail.'
                    }
                },
                roles: ['Director', 'Manager', 'Employee', 'Administrator'],
                dialog: false,
                user: '',
                name: '',
                surname: '',
                familyName: '',
                email: '',
                role: '',
                login: '',
                teamId: '',
                restDays: '',
                hireDate: '',
                phoneNumber: '',
                description: '',
            }
        },
        methods: {
            save: function () {

                var newUser = {
                    user: this.user,
                    login: this.login,
                    password: Math.random().toString(36).slice(-8),
                    name: this.name,
                    surname: this.surname,
                    familyName: this.familyName,
                    email: this.email,
                    role: this.role,
                    teamId: this.teamId,
                    restDays: this.restDays,
                    hireDate: this.hireDate,
                    phoneNumber: this.phoneNumber,
                    description: this.description
                };
                console.log(newUser);
                instance.post('users/addUser',
                    newUser
                )
                    .then(function (response) {
                        console.log(response);
                    });
                location.reload();
                this.dialog = false;
            }
        }
    }
</script>

<style scoped>

</style>