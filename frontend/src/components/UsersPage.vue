<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <Nav></Nav>
        <b-container>
            <b-row>
                <b-col md="6">
                    <div class="profile-head">
                        <h5>
                            {{fields.name}} {{fields.surname}}
                        </h5>
                        <h6>
                            {{fields.role}}
                        </h6>
                    </div>
                    <b-tabs content-class="mt-3">
                        <b-tab title="About" active>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="tab-content profile-tab" id="myTabContent">
                                        <div class="tab-pane fade show active" id="home" role="tabpanel"
                                             aria-labelledby="home-tab">
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <label>Login</label>
                                                </div>
                                                <div class="col-md-6">
                                                    <p>{{fields.login}}</p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <label>Name</label>
                                                </div>
                                                <div class="col-md-6">
                                                    <p>{{fields.name}}</p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <label>Surname</label>
                                                </div>
                                                <div class="col-md-6">
                                                    <p>{{fields.surname}}</p>
                                                </div>
                                            </div>
<!--                                            <div class="row">-->
<!--                                                <div class="col-md-6">-->
<!--                                                    <label>Patronymic</label>-->
<!--                                                </div>-->
<!--                                                <div class="col-md-6">-->
<!--                                                    <p>{{fields.familyName}}</p>-->
<!--                                                </div>-->
<!--                                            </div>-->
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <label>Email</label>
                                                </div>
                                                <div class="col-md-6">
                                                    <p>{{fields.email}}</p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <label>Hire date</label>
                                                </div>
                                                <div class="col-md-6">
                                                    <p>{{fields.hireDate}}</p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <label>Phone number</label>
                                                </div>
                                                <div class="col-md-6">
                                                    <p>{{fields.phoneNumber}}</p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <label>Other information</label>
                                                </div>
                                                <div class="col-md-6">
                                                    <p>{{fields.description}}</p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <label>Work team</label>
                                                </div>
                                                <div class="col-md-6">
                                                    <p>{{fields.teamName}}</p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <label>Subordinate teams</label>
                                                </div>
                                                <div class="col-md-6">
                                                    <p>{{fields.subordinateTeams}}</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </b-tab>
                        <b-tab title="Options">
                            <UpdatePassword></UpdatePassword>
                            <div v-if="!integrated">
                                <v-layout>
                                    <v-btn dark color="teal lighten-1" v-on:click="integrate">
                                        start
                                        google calendar integration
                                    </v-btn>
                                    <v-tooltip bottom>
                                        <template v-slot:activator="{ on }">
                                            <v-icon v-on="on">info</v-icon>
                                        </template>
                                        <span>all current and new approved
                                                <div>requests will be transferred</div>
                                                  to your Google calendar
                                            </span>
                                    </v-tooltip>
                                </v-layout>
                            </div>
                            <div v-else>
                                <v-layout>
                                    <v-btn dark color="teal lighten-1" v-on:click="cancelIntegration">cancel
                                        google calendar integration
                                    </v-btn>
                                    <v-tooltip bottom>
                                        <template v-slot:activator="{ on }">
                                            <v-icon v-on="on">info</v-icon>
                                        </template>
                                        <span>new approved requests
                                                <div>will not be transferred</div>
                                                  to your Google calendar
                                            </span>
                                    </v-tooltip>
                                </v-layout>
                            </div>
                        </b-tab>
                    </b-tabs>
                </b-col>
                <b-col>
                    <UserRequests></UserRequests>
                </b-col>
            </b-row>
        </b-container>
        <Footer></Footer>
    </div>

</template>

<script>
    import Nav from "./Nav";
    import {instance} from "../Api";
    import UpdatePassword from "./usersControl/UpdatePassword";
    import Footer from "./Footer";
    import UserRequests from "./usersControl/UserRequests";

    export default {
        name: "UsersPage",
        components: {UserRequests, Footer, UpdatePassword, Nav},
        data() {
            return {
                fields: [
                    {key: "login"},
                    {key: "role"},
                    {key: "name"},
                    {key: "surname"},
                    {key: "familyName"},
                    {key: "email"},
                    {key: "phoneNumber"},
                    {key: "description"},
                    {key: "teamName"},
                    {key: "subordinateTeams"},
                ],
                integrated: ''
            }
        },
        created() {
            instance.get("/users/" + localStorage.getItem('user_id') + "/info").then((resp) => {
                this.fields = resp.data;
                this.integrated = resp.data.integrated;
                this.fields['teamName'] = localStorage.getItem('team');
            }).catch(err => {
                console.log(err);
            })
        },

        mounted() {
        },

        methods: {
            integrate() {
                instance.get('integration')
                    .then(response => {
                        window.open(response.data, '_self');
                    });

            },
            cancelIntegration() {
                instance.put('integration')
                    .then(response => instance.get("/users/info").then((resp) => {
                        this.fields = resp.data;
                        this.fields['teamName'] = localStorage.getItem('team');
                        this.integrated = resp.data.integrated;
                    }));

            },
        },
    }
</script>

<style scoped>

</style>
