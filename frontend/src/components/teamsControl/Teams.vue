<template>
    <div>
        <Nav></Nav>
        <v-content>
            <v-container>
                <h4>Manage teams</h4>
                <p></p>
                <input v-model="search" class="form-control" placeholder="Search by team name...">
                <p></p>
                <NewTeam></NewTeam>
                <ul class="list-group mt-1" v-for="team of findTeams" :key="team.teamId">
                    <li class="list-group-item">
                        <v-layout align-center justify-space-between row fill-height>
                            <div>
                                <router-link :to="'/teams/' + team.teamId">
                                    <strong class="ml-2"> {{ team.name }} </strong>
                                </router-link>
                            </div>
                            <div>
                                <v-layout align-center>
                                    <router-link :to="'/teams/' + team.teamId">
                                        <v-icon>edit</v-icon>
                                    </router-link>
                                    <v-icon @click="clickDelete(team.teamId, team.name)">delete</v-icon>
                                </v-layout>
                            </div>
                        </v-layout>
                    </li>
                </ul>
                <v-layout row justify-center>
                    <v-dialog v-model="deleteDialog" persistent max-width="290">
                        <v-card>
                            <v-card-title class="headline">Are you sure, you want to delete this team?</v-card-title>
                            <v-card-text>
                                <p>Team name: <strong>{{deleteName}}</strong></p>
                            </v-card-text>
                            <v-card-actions>
                                <v-spacer></v-spacer>
                                <v-btn color="green darken-1" flat @click.native="deleteDialog = false">Cancel</v-btn>
                                <v-btn color="green darken-1" flat @click.native="deleteTeam(deleteId)">Yes</v-btn>
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
    import NewTeam from "./NewTeam";
    import Nav from "../Nav";
    import AddUserToTeam from "./AddUserToTeam";
    import Footer from "../Footer";

    export default {
        name: "Teams",
        components: {Footer, AddUserToTeam, Nav, NewTeam},
        data() {
            return {
                search: '',
                teams: [],
                deleteName: '',
                deleteId: '',
                deleteDialog: false
            }
        },

        methods: {
            clickDelete(id, name) {
                this.deleteDialog = true;
                this.deleteId = id;
                this.deleteName = name;
            },
            deleteTeam(id) {
                instance.delete('teams/' + id,
                )
                    .then(function (response) {
                        console.log(response);
                    });
                this.deleteDialog = false;
                this.teams = this.teams.filter(x => x.teamId !== id);
            },
        },

        created: function () {
            instance.get('teams')
                .then(response => {
                    this.teams = response.data;
                    this.teams.sort((a, b) => a.name.localeCompare(b.name));
                })
        },

        computed: {
            findTeams() {
                return this.teams.filter(item => item.name.toLocaleLowerCase().indexOf(this.search.toLowerCase()) !== -1)
            },
        }
    }
</script>

<style scoped>

</style>