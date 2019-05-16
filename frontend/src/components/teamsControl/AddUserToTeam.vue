<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <h5>Users
            <v-icon v-on:click="dialog = ! dialog">add</v-icon>
        </h5>
        <v-dialog v-model="dialog" max-width="800px">
            <v-card>
                <v-card-text>

                    <v-card-title>
                        <h3 class="headline mb-0">Select users</h3>
                        <v-spacer></v-spacer>
                        <v-text-field
                                v-model="search"
                                append-icon="search"
                                label="Search"
                                single-line
                                hide-details
                        ></v-text-field>
                    </v-card-title>
                    <v-data-table v-model="selected" :headers="headers" :items="findUsers" :search="search"
                                  item-key="userId" select-all
                                  class="elevation-1">
                        <template v-slot:items="user">
                            <td>
                                <v-checkbox v-model="user.selected" primary hide-details></v-checkbox>
                            </td>
                            <td>{{ user.item.name }} {{user.item.surname}}</td>
                            <td class="text-xs-left">{{ user.item.teamName }}</td>
                            <td class="text-xs-right">{{ user.item.role }}</td>
                        </template>
                    </v-data-table>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn flat color="error" @click="dialog = false">Cancel</v-btn>
                        <v-btn flat color="primary" @click="add">Add</v-btn>
                    </v-card-actions>
                </v-card-text>
            </v-card>
        </v-dialog>
    </div>
</template>

<script>

    import {instance} from "../../Api";

    export default {

        name: "AddUserToTeam",
        data() {
            return {
                search: '',
                teamUsers: '',
                users: [],
                select: [],
                dialog: false,
                selected: [],
                headers: [
                    {
                        text: 'Name',
                        align: 'left',
                        sortable: false,
                        value: 'name'
                    },
                    {
                        text: 'Team name',
                        align: 'center',
                        sortable: false,
                        value: 'team id'
                    },
                    {
                        text: 'Role',
                        align: 'right',
                        sortable: false,
                        value: 'role'
                    }
                ]
            }
        },

        methods: {
            add() {
                for (let user of this.selected) {
                    this.select.push(user.userId);
                }
                instance.put('users/addToTeam/' + this.$router.currentRoute.params['id'], this.select)
                    .then(resp => {
                        this.$emit("addUserToTeam");
                        this.inStart();
                    })
                this.dialog = false;
            },
            checkUser(id) {
                var i;
                for (i = 0; i < this.teamUsers.length; i++) {
                    if (this.teamUsers[i].userId === id) {
                        return false;
                    }
                }
                return true;
            },
            inStart() {
                instance.get('users')
                    .then(response => {
                        this.users = response.data;
                    });
                instance.get('users/',
                    {
                        params: {
                            teamId: this.$router.currentRoute.params['id']
                        }
                    })
                    .then(response => {
                        this.teamUsers = response.data;
                    });
            }
        },

        created: function () {
            this.inStart();
        },

        computed: {
            findUsers() {
                return this.users = this.users.filter(x => x.role !== 'Administrator'
                    && x.role !== 'Director' && this.checkUser(x.userId));

            },
        }
    }
</script>

<style scoped>

</style>