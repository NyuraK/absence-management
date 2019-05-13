<template>
    <div id="users-list">
        <Nav></Nav>
        <v-content>
            <v-container>
<!--                <v-alert v-model="alert" dismissible type="success" class="alert alert-success" outline>-->
<!--                    Profile has been successfully updated.-->
<!--                </v-alert>-->
                <v-layout align-space-between justify-start column>
                    <h4>Manage users</h4>
                    <p></p>
                    <input v-model="search" class="form-control" placeholder="Search by name or role...">
                    <p></p>
                    <NewUser></NewUser>
                    <ul class="list-group mt-1" v-for="user of findUsers" :key="user.user">
                        <li class="list-group-item">
                            <v-layout align-center justify-space-between row fill-height>
                                <div>
                                    <strong class="ml-2"> {{ user.name }} {{ user.surname }}</strong>
                                    <v-chip color="primary" text-color="white" v-if="user.role === 'Employee'" small>
                                        {{user.role}}
                                    </v-chip>
                                    <v-chip color="secondary" text-color="white"
                                            v-else-if="user.role === 'Administrator'" small>{{user.role}}
                                    </v-chip>
                                    <v-chip color="red" text-color="white" v-else-if="user.role === 'Director'" small>
                                        {{user.role}}
                                    </v-chip>
                                    <v-chip color="green" text-color="white" v-else small>{{user.role}}</v-chip>
                                </div>
                                <div>
                                    <v-layout align-center>
                                        <router-link :to="'/users/' + user.userId">
                                            <v-icon>edit</v-icon>
                                        </router-link>
                                        <v-icon @click="clickDelete(user.userId, user.name, user.surname)">delete
                                        </v-icon>
                                    </v-layout>
                                </div>
                            </v-layout>
                        </li>
                    </ul>
                </v-layout>
                <v-layout row justify-center>
                    <v-dialog v-model="deleteDialog" persistent max-width="290">
                        <v-card>
                            <v-card-title class="headline">Are you sure, you want to delete this profile?</v-card-title>
                            <v-card-text>
                                <p>User name: <strong>{{deleteName}}</strong></p>
                                <p>User surname: <strong>{{deleteSurname}}</strong></p>
                            </v-card-text>
                            <v-card-actions>
                                <v-spacer></v-spacer>
                                <v-btn color="green darken-1" flat @click.native="deleteDialog = false">Cancel</v-btn>
                                <v-btn color="green darken-1" flat @click.native="deleteUser(deleteId)">Yes</v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-dialog>
                </v-layout>
            </v-container>
        </v-content>
        <Footer></Footer>
    </div>
</template>

<script>

    import {instance} from '../../Api.js';
    import NewUser from "./NewUser";
    import Nav from "../Nav";
    import Footer from "../Footer";


    export default {
        components: {Footer, Nav, NewUser},
        data() {
            return {
                alert: false,
                deleteName: '',
                deleteSurname: '',
                deleteId: '',
                deleteDialog: false,
                search: '',
                users: []
            }
        },
        created: function () {
            if (this.$route.query.alert) {
                this.alert = this.$route.query.alert;
            }
            instance.get('users')
                .then(response => {
                    this.users = response.data;
                    this.users.sort((a, b) => a.name.localeCompare(b.name))
                })
        },
        methods: {
            clickDelete(id, name, surname) {
                this.deleteDialog = true;
                this.deleteId = id;
                this.deleteName = name;
                this.deleteSurname = surname;
            },
            deleteUser(id) {
                instance.delete('users/' + id,
                )
                    .then(function (response) {
                    });
                this.deleteDialog = false;
                this.users = this.users.filter(x => x.userId !== id);
            }
        },
        computed: {
            findUsers() {
                return this.users.filter(item => ''.concat(item.name.toLocaleLowerCase(), ' ',
                    item.surname.toLocaleLowerCase(), ' ',
                    item.role.toLocaleLowerCase()).indexOf(this.search.toLowerCase()) !== -1)
            },
        }

    }
</script>

<style scoped>
</style>