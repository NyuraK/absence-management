<template>
    <div>
        <v-toolbar app>
            <v-toolbar-title>{{ userName }} {{ userSurname }}
                <v-icon>edit</v-icon>
            </v-toolbar-title>
            <v-spacer></v-spacer>
        </v-toolbar>
        <v-content>
            <v-container>
                Name:
                <v-flex xs10 sm5>
                    <v-text-field v-model="user.name" label="name" solo></v-text-field>
                </v-flex>
                Surname:
                <v-flex xs10 sm5>
                    <v-text-field v-model="user.surname" label="surname" solo></v-text-field>
                </v-flex>
                Family name:
                <v-flex xs10 sm5>
                    <v-text-field v-model="user.family_name" label="family name" solo></v-text-field>
                </v-flex>
                Role:
                <v-flex xs10 sm5>
                    <v-text-field v-model="user.role" label="role" solo></v-text-field>
                </v-flex>
                Email:
                <v-flex xs10 sm5>
                    <v-text-field v-model="user.email" label="email" solo></v-text-field>
                </v-flex>
                Login:
                <v-flex xs10 sm5>
                    <v-text-field v-model="user.login" label="login" solo></v-text-field>
                </v-flex>
                Password:
                <v-flex xs10 sm5>
                    <v-text-field v-model="user.password" label="password" solo></v-text-field>
                </v-flex>
                Team id:
                <v-flex xs10 sm5>
                    <v-text-field v-model="user.teams_id" label="team id" solo></v-text-field>
                </v-flex>
                Rest days:
                <v-flex xs10 sm5>
                    <v-text-field v-model="user.rest_days" label="rest days" solo></v-text-field>
                </v-flex>
                Hire date:
                <v-flex xs10 sm5>
                    <v-text-field v-model="user.hire_date" label="hire date" solo></v-text-field>
                </v-flex>
                Phone number:
                <v-flex xs10 sm5>
                    <v-text-field v-model="user.phone_number" label="phone number" solo></v-text-field>
                </v-flex>
                Id:
                <v-flex xs10 sm5>
                    <v-text-field v-model="user.users_id" label="id" solo></v-text-field>
                </v-flex>
                <v-flex xs10 sm5>
                    <v-btn block color="primary" dark @click="save">Save</v-btn>
                </v-flex>
                <v-alert v-model="alert" dismissible type="success">
                    Profile has been successfully updated.
                </v-alert>
            </v-container>


        </v-content>
    </div>
</template>

<script>

    import {instance} from '../Api.js';

    export default {
        name: "UserEdit",
        data() {
            return {
                user: [],
                userName: '',
                userSurname: '',
                userId: '',
                alert: false
            }
        },
        created: function () {
            instance.get('/api/user/' + this.$router.currentRoute.params['id'])
                .then(response => {
                    this.user = response.data;
                    console.log(response);
                    this.userName = this.user.name;
                    this.userSurname = this.user.surname;
                    this.userId = this.user.users_id;
                });

        },
        methods: {
            save: function () {
                instance.put('/api/user/' + this.$router.currentRoute.params['id'],
                    this.user
                )
                    .then(function (response) {
                        console.log(response);
                    });
                this.alert = true;
            }
        }
    }
</script>

<style scoped>

</style>