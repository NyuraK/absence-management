<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <Nav></Nav>
        <v-content>
            <v-container>
                <h4>{{teamName}}
                    <v-icon>edit</v-icon>
                </h4>
                <p v-if="team.managerName === null">Team manager: none </p>
                <p v-else>Team manager: {{team.managerName }} {{ team.managerSurname}} </p>
                <p v-if="team.departmentName === null">Team department: none </p>
                <p v-else>Team department: {{team.departmentName}} </p>
                <v-flex xs12 lg5 sm5>
                    <v-expansion-panel>
                        <v-expansion-panel-content>
                            <template v-slot:header>
                                <div>Rename</div>
                            </template>
                            <v-card>
                                <v-card-text>
                                    <v-text-field v-model="team.name" label="name" solo></v-text-field>
                                    <v-btn block color="teal lighten-1" dark @click="rename">Save</v-btn>
                                </v-card-text>
                            </v-card>
                        </v-expansion-panel-content>
                    </v-expansion-panel>
                </v-flex>
                <v-flex xs12 lg5>
                    <v-expansion-panel>
                        <v-expansion-panel-content>
                            <template v-slot:header>
                                <div>Change Quota</div>
                            </template>
                            <v-card>
                                <v-card-text>
                                    <v-text-field v-model="team.quota" label="quota" solo></v-text-field>
                                    <v-btn block color="teal lighten-1" dark @click="changeQuota">Save</v-btn>
                                </v-card-text>
                            </v-card>
                        </v-expansion-panel-content>
                    </v-expansion-panel>
                </v-flex>
                <v-flex xs12 lg5>
                    <v-expansion-panel>
                        <v-expansion-panel-content>
                            <template v-slot:header>
                                <div>Change manager or (and) department</div>
                            </template>
                            <v-card>
                                <v-card-text>
                                    <v-select :items="departments" label="Department" v-model="department"
                                              item-text="name" return-object></v-select>
                                    <v-select :items="findManagers" label="Manager" v-model="managerId"
                                              :item-text="text" item-value="userId"></v-select>
                                    <v-btn block color="teal lighten-1" dark @click="changeDepartmentAndManager">Save
                                    </v-btn>
                                </v-card-text>
                            </v-card>
                        </v-expansion-panel-content>
                    </v-expansion-panel>
                </v-flex>
                <AddUserToTeam></AddUserToTeam>
                <ul class="list-group mt-1" v-for="user of teamUsers" :key="user.userId">
                    <li class="list-group-item">
                        <v-layout align-center justify-space-between row fill-height>
                            <div>
                                <router-link :to="'/users/' + user.userId">
                                    <strong class="ml-2">{{ user.name }} {{ user.surname }}</strong>
                                </router-link>
                            </div>
                            <v-icon @click="clear(user)">clear</v-icon>
                        </v-layout>
                    </li>
                </ul>
            </v-container>
        </v-content>
        <Footer></Footer>
    </div>
</template>

<script>

    import {instance} from "../../Api";
    import Nav from "../Nav";
    import AddUserToTeam from "./AddUserToTeam";
    import Footer from "../Footer";

    export default {
        name: "TeamEdit",
        components: {Footer, AddUserToTeam, Nav},
        data() {
            return {
                teamName: '',
                team: [],
                teamUsers: [],
                managers: [],
                departments: [],
                department: [],
                user: [],
                managerId: '',
            }
        },

        methods: {
            text: item => item.name + ' ' + item.surname,
            clear(user) {
                user.teamId = null;
                instance.put('users/' + user.userId, user);
                this.teamUsers = this.teamUsers.filter(x => x.userId !== user.userId)
            },
            rename() {
                instance.put('teams/' + this.$router.currentRoute.params['id'], this.team);
                location.reload();
            },
            changeQuota() {
                instance.put('teams/' + this.$router.currentRoute.params['id'], this.team);
                location.reload();
            },
            changeDepartmentAndManager() {
                this.team.departmentId = this.department.departmentId;
                this.team.managerId = this.managerId;
                instance.put('teams/' + this.$router.currentRoute.params['id'], this.team);
                location.reload();
            },
            checkUser(id) {
                var i;
                for (i = 0; i < this.teamUsers.length; i++) {
                    if (this.teamUsers[i].userId === id) {
                        return false;
                    }
                }
                return true;
            }

        },

        created: function () {
            instance.get('teams/' + this.$router.currentRoute.params['id'])
                .then(response => {
                    this.team = response.data;
                    this.teamName = this.team.name;
                });
            instance.get('users',
                {
                    params: {
                        teamId: this.$router.currentRoute.params['id']
                    }
                })
                .then(response => {
                    this.teamUsers = response.data;
                    this.teamUsers.sort((a, b) => a.name.localeCompare(b.name))
                });
            instance.get('users')
                .then(response => this.managers = response.data);
            instance.get('departments')
                .then(response => this.departments = response.data);
        },

        computed: {
            findManagers() {
                return this.managers.filter(item => item.departmentId === this.department.departmentId && item.role !== 'Employee'
                    && this.checkUser(item.userId) || item.userId === this.department.directorId
                    && item.userId !== this.team.managerId);
            },
        }

    }
</script>

<style scoped>

</style>