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
                <p v-if="team.departmentId === null">Team department: none </p>
                <p v-else>Team department: {{team.departmentId}} </p>



                <v-layout justify-space-around row>
                    <v-flex xs12 lg5 mb-3>
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

                    <v-flex xs12 lg5 mb-3>
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

                    <v-flex xs12 lg5 mb-3>
                        <v-expansion-panel>
                            <v-expansion-panel-content>
                                <template v-slot:header>
                                    <div>Change Department</div>
                                </template>
                                <v-card>
                                    <v-card-text>
                                        <v-text-field v-model="team.departmentId" label="department id"
                                                      solo></v-text-field>
                                        <v-btn block color="teal lighten-1" dark @click="changeDepartment">Save</v-btn>
                                    </v-card-text>
                                </v-card>
                            </v-expansion-panel-content>
                        </v-expansion-panel>
                    </v-flex>

                    <v-flex xs12 lg5 mb-3>
                        <v-expansion-panel>
                            <v-expansion-panel-content>
                                <template v-slot:header>
                                    <div>Change Manager</div>
                                </template>
                                <v-card>
                                    <v-card-text>
                                        <ChangeManagerComponent></ChangeManagerComponent>
                                    </v-card-text>
                                </v-card>
                            </v-expansion-panel-content>
                        </v-expansion-panel>
                    </v-flex>
                </v-layout>

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
    </div>
</template>

<script>

    import {instance} from "../../Api";
    import ChangeManagerComponent from "./ChangeManagerComponent";
    import Nav from "../Nav";
    import AddUserToTeam from "./AddUserToTeam";

    export default {
        name: "TeamEdit",
        components: {AddUserToTeam, Nav, ChangeManagerComponent},
        data() {
            return {
                teamName: '',
                team: [],
                teamUsers: [],
                user: []
            }
        },

        methods: {
            clear(user) {
                user.teamId = null;
                instance.put('users/' + user.userId, user);
                this.teamUsers = this.teamUsers.filter(x => x.userId !== user.userId)
            },
            rename(){
                instance.put('teams/' + this.$router.currentRoute.params['id'], this.team);
                location.reload();
            },
            changeQuota(){
                instance.put('teams/' + this.$router.currentRoute.params['id'], this.team);
                location.reload();
            },
            changeDepartment(){
                instance.put('teams/' + this.$router.currentRoute.params['id'], this.team);
                location.reload();
            }

        },

        created: function () {
            instance.get('teams/' + this.$router.currentRoute.params['id'])
                .then(response => {
                    this.team = response.data;
                    this.teamName = this.team.name;
                });
            instance.get('users/team/' + this.$router.currentRoute.params['id'])
                .then(response => {
                    this.teamUsers = response.data;
                });
        },

    }
</script>

<style scoped>

</style>