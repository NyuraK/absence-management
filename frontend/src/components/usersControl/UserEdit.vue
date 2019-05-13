<template>
    <div>
        <Nav></Nav>
        <v-content>
            <v-container>
                <h4>{{ userName }} {{ userSurname }}
                    <v-icon>edit</v-icon>
                </h4>
                <UpdatePassword></UpdatePassword>
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
                    <v-text-field v-model="user.familyName" label="family name" solo></v-text-field>
                </v-flex>
                Role:
                <v-flex xs10 sm5>
                    <v-select :items="roles" :label="user.role" v-model="user.role"></v-select>
                </v-flex>
                Email:
                <v-flex xs10 sm5>
                    <v-text-field v-model="user.email" label="email" solo></v-text-field>
                </v-flex>
                Login:
                <v-flex xs10 sm5>
                    <v-text-field v-model="user.login" label="login" solo></v-text-field>
                </v-flex>
                Team:
                <v-flex xs10 sm5>
                    <v-select :items="teams" :label="user.teamName" v-model="user.teamId" item-text="name"
                              item-value="teamId" placeholder="Change team"></v-select>
                </v-flex>
                Rest days:
                <v-flex xs10 sm5>
                    <v-text-field v-model="user.restDays" label="rest days" solo></v-text-field>
                </v-flex>
                Hire date:
                <v-flex xs10 sm5>
                    <v-text-field v-model="user.hireDate" label="hire date" solo></v-text-field>
                </v-flex>
                Phone number:
                <v-flex xs10 sm5>
                    <v-text-field v-model="user.phoneNumber" label="phone number" mask="+# (###) ### - ##-##"
                                  solo></v-text-field>
                </v-flex>
                <v-flex xs10 sm5>
                    <v-btn block color="primary" dark @click="save">Save</v-btn>
                </v-flex>
                <v-flex xs10 sm5>
                    <v-alert v-model="alert" dismissible type="success">
                        Profile has been successfully updated.
                    </v-alert>
                </v-flex>
            </v-container>
        </v-content>
        <Footer></Footer>
    </div>
</template>

<script>

    import {instance} from '../../Api.js';
    import Nav from "../Nav";
    import UpdatePassword from "./UpdatePassword";
    import Footer from "../Footer";

    export default {
        name: "UserEdit",
        components: {Footer, UpdatePassword, Nav},
        data() {
            return {
                roles: ['Director', 'Manager', 'Employee', 'Administrator'],
                user: [],
                userName: '',
                userSurname: '',
                userId: '',
                alert: false,
                teams: [],
            }
        },
        created: function () {
            instance.get('users/' + this.$router.currentRoute.params['id'])
                .then(response => {
                    this.user = response.data;
                    this.userName = this.user.name;
                    this.userSurname = this.user.surname;
                    this.userId = this.user.userId;
                });
            instance.get('teams')
                .then(response => this.teams = response.data);

        },
        methods: {
            save: function () {
                instance.put('users/' + this.$router.currentRoute.params['id'],
                    this.user
                )
                    .then(function (response) {
                    });
                this.$router.push({name: 'users', query: {alert: true}});
                this.alert = true;
            }
        }
    }
</script>

<style scoped>

</style>