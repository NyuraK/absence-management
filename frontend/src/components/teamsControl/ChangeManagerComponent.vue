<template>
    <div>
        <v-btn block dark color="teal lighten-1" v-on:click="dialog = ! dialog">Change manager</v-btn>
        <v-dialog v-model="dialog" max-width="600px">
            <v-card>
                <v-card-text>
                    <v-card-title>
                        <h3 class="headline mb-0">Select manager</h3>
                        <v-spacer></v-spacer>
                        <v-text-field
                                v-model="search"
                                append-icon="search"
                                label="Search"
                                single-line
                        ></v-text-field>
                    </v-card-title>
                    <v-radio-group v-model="managerId" :mandatory="false">
                        <div v-for="user of findUsers">
                            <v-layout justify-space-around row >
                                <v-radio class="ml-3" :label="' '" :value="user.userId"></v-radio>
                                <div>{{ user.name }} {{ user.surname }}</div>
                            </v-layout>
                        </div>
                    </v-radio-group>
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
        name: "ChangeManagerComponent",

        data() {
            return {
                search: '',
                managerId: '',
                users: [],
                team: [],
                dialog: false,
                mame: '',
                headers: [
                    {
                        text: 'Name',
                        align: 'left',
                        sortable: false,
                        value: 'name'
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
            save() {
                this.team.managerId = this.managerId;
                instance.put('teams/' + this.$router.currentRoute.params['id'], this.team);
                this.dialog = false;
                location.reload();
            }
        },

        created: function () {
            instance.get('teams/' + this.$router.currentRoute.params['id'])
                .then(response => {
                    this.team = response.data;
                    this.managerId = this.team.managerId;
                });
            instance.get('users')
                .then(response => {
                    this.users = response.data;
                })
        },

        computed: {
            findUsers() {
                return this.users = this.users.filter(x => x.role !== 'Administrator' && x.role !== 'Employee'
                    && ''.concat(x.name.toLocaleLowerCase(), ' ',
                        x.surname.toLocaleLowerCase(), ' ',
                        x.role.toLocaleLowerCase()).indexOf(this.search.toLowerCase()) !== -1);

            },
        }

    }
</script>

<style scoped>

</style>