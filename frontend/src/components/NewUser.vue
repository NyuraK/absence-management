<template>
    <div>
        <v-btn small color="primary" dark @click="dialog = !dialog">Add user</v-btn>
        <v-dialog v-model="dialog" max-width="600px">
            <v-card>
                <v-card-text>
                    <v-text-field label="First name" v-model="name"></v-text-field>
                    <small class="grey--text">* This doesn't actually save.</small>
                    <v-text-field label="Surname" v-model="surname"></v-text-field>
                    <v-text-field label="Family name" v-model="family_name"></v-text-field>
                    <v-select :items="roles" label="Role" v-model="role"></v-select>
                    <v-text-field label="Email" v-model="email"></v-text-field>
                    <v-text-field label="Login" v-model="login"></v-text-field>
                    <v-text-field label="Password" v-model="password"></v-text-field>
                    <v-text-field label="Password" v-model="password2"></v-text-field>
                    <v-text-field label="Team id" v-model="team_id"></v-text-field>
                    <v-text-field label="Rest Days" v-model="rest_days"></v-text-field>
                    <v-text-field label="Hire date" v-model="hire_date"></v-text-field>
                    <v-text-field label="Phone number" v-model="phone_number"></v-text-field>
                    <v-text-field label="Id" v-model="users_id"></v-text-field>
                    <small class="grey--text">* This doesn't actually save.</small>
                    <v-text-field label="File name"></v-text-field>
                    <small class="grey--text">* This doesn't actually save.</small>
                    <v-text-field label="File name"></v-text-field>
                    <small class="grey--text">* This doesn't actually save.</small>
                    <v-text-field
                            v-model="password"
                            :append-icon="show1 ? 'visibility' : 'visibility_off'"
                            :rules="[rules.required, rules.min]"
                            :type="show1 ? 'text' : 'password'"
                            name="input-10-1"
                            label="Normal with hint text"
                            hint="At least 8 characters"
                            counter
                            @click:append="show1 = !show1"
                    ></v-text-field>
                    <v-text-field
                            v-model="password2"
                            :append-icon="show4 ? 'visibility' : 'visibility_off'"
                            :rules="[rules.required, rules.emailMatch]"
                            :type="show4 ? 'text' : 'password'"
                            name="input-10-2"
                            label="Error"
                            hint="At least 8 characters"
                            @click:append="show4 = !show4"
                    ></v-text-field>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn flat color="primary" @click="save">Add</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </div>
</template>

<script>

    import {instance} from '../Api.js';

    export default {
        name: "NewUser",
        data() {
            return {
                show1: false,
                show4: false,
                rules: {
                    required: value => !!value || 'Required.',
                    min: v => v.length >= 8 || 'Min 8 characters',
                    emailMatch: () => ('The password you entered don\'t match')},
                roles: ['user', 'manager', 'admin'],
                dialog: false,
                users_id: '',
                name: '',
                surname: '',
                family_name: '',
                email: '',
                role: '',
                login: '',
                password: '',
                password2: '',
                team_id: '',
                rest_days: '',
                hire_date: '',
                phone_number: ''
            }
        },
        methods: {
            save: function () {

                var newUser = {
                    users_id: this.users_id,
                    login: this.login,
                    password: this.password,
                    name: this.name,
                    surname: this.surname,
                    family_name: this.family_name,
                    email: this.email,
                    role: this.role,
                    password2: this.password,
                    team_id: this.team_id,
                    rest_days: this.rest_days,
                    hire_date: this.hire_date,
                    phone_number: this.phone_number
                };

                instance.post('/api/user/addUser',
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