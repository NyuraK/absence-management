<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <Nav></Nav>
        <v-content>
            <v-container>
                <v-alert v-model="alert" dismissible type="success" class="alert alert-success" outline>
                    Department has been successfully updated.
                </v-alert>
                <h4>{{departmentName}}
                    <v-icon>edit</v-icon>
                </h4>
                <p v-if="department.directorName === null">Department director: none </p>
                <p v-else>Department director: {{department.directorName }} {{ department.directorSurname}} </p>
                <v-layout justify-space-around row>
                    <v-flex xs12 lg5 mb-3>
                        <v-expansion-panel>
                            <v-expansion-panel-content>
                                <template v-slot:header>
                                    <div>Rename</div>
                                </template>
                                <v-card>
                                    <v-card-text>
                                        <v-text-field v-model="department.name" label="name" solo></v-text-field>
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
                                    <div>Change Director</div>
                                </template>
                                <v-card>
                                    <v-card-text>
                                        <ChangeDirector @changeDirector="changeDirector"></ChangeDirector>
                                    </v-card-text>
                                </v-card>
                            </v-expansion-panel-content>
                        </v-expansion-panel>
                    </v-flex>
                </v-layout>
                <h5>Teams: </h5>
                <ul class="list-group mt-1" v-for="team of departmentTeams" :key="team.teamId">
                    <li class="list-group-item">
                        <v-layout align-center justify-space-between row fill-height>
                            <div>
                                <router-link :to="'/teams/' + team.teamId">
                                    <strong class="ml-2">{{ team.name }}</strong>
                                </router-link>
                            </div>
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
    import ChangeDirector from "./ChangeDirector";
    import Footer from "../Footer";

    export default {

        name: "DepartmentEdit",
        components: {Footer, ChangeDirector, Nav},

        data() {
            return {
                alert: false,
                departmentName: '',
                department: [],
                departmentTeams: [],
            }
        },

        methods: {
            rename() {
                instance.put('departments/' + this.$router.currentRoute.params['id'], this.department)
                    .then(resp => {
                        this.atStart();
                        this.alert = true;
                    });
            },
            changeDirector() {
                this.atStart();
                this.alert = true;
            },
            atStart(){
                instance.get('departments/' + this.$router.currentRoute.params['id'])
                    .then(response => {
                        this.department = response.data;
                        this.departmentName = this.department.name;
                    });
                instance.get('teams/',
                    {
                        params: {
                            departmentId: this.$router.currentRoute.params['id']
                        }
                    })
                    .then(response => {
                        this.departmentTeams = response.data;
                    });
            }
        },

        created: function () {
            this.atStart();
        },
    }
</script>

<style scoped>

</style>