<template>
    <div id="users-list">
        <v-toolbar app>
            <v-toolbar-title>Users</v-toolbar-title>
            <v-spacer></v-spacer>
        </v-toolbar>
        <v-content>
            <!--<router-link type="button" class="btn btn-primary mb-2 ml-2" to="users/adduser">Add user</router-link>-->
            <v-container>
                <v-layout align-space-between justify-start column>
                    <NewUser></NewUser>
                    <input v-model="search" class="form-control" placeholder="Search by name or role...">
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
                                            <v-btn color="primary" fab small dark>
                                                <v-icon>edit</v-icon>
                                            </v-btn>
                                        </router-link>
                                        <v-btn depressed small color="error"
                                               @click="clickDelete(user.userId, user.name, user.surname)">Delete
                                        </v-btn>
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
    </div>
</template>

<script>

    import {instance} from '../../Api.js';
    import NewUser from "./NewUser";


    export default {
        components: {NewUser},
        data() {
            return {
                deleteName: '',
                deleteSurname: '',
                deleteId: '',
                deleteDialog: false,
                search: '',
                users: []
            }
        },
        created: function () {
            instance.get('users')
                .then(response => {
                    this.users = response.data;
                    console.log(response)
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
                        console.log(response);
                    });
                this.deleteDialog = false;
                location.reload();
            }
        },
        computed: {
            findUsers() {
                return this.users.filter(item => ''.concat(item.name.toLowerCase(), ' ',
                    item.surname.toLowerCase(), ' ',
                    item.familyName.toLowerCase(), item.role.toLowerCase()).indexOf(this.search.toLowerCase()) !== -1)
            },
        }

    }
</script>

<style scoped>

</style>