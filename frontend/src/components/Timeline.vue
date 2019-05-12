<template>
    <div>
        <Nav></Nav>
        <b-container>
            <h3 v-if="userTeam.name !== ''">You are a member of {{userTeam.name}} team</h3>
            <h3 v-else>{{this.$store.getters.team_msg}}</h3>
            <v-slider v-if="showChart"
                      id="slider"
                      v-model="zoom"
                      :min="1200"
                      :max="7000"
                      label="Zoom"
                      v-on:click="zoomChart"
            ></v-slider>
            <v-select
                    v-if="$acl.check('isManager') && showSelector"
                    :items="teams"
                    item-text="name"
                    item-value="id"
                    label="Team"
                    v-on:change="showChartForManager"
            ></v-select>

            <div id="chart_wrapper" v-if="showChart">
                <GChart id="timeline" refs="timeline"
                        :settings="{ packages: ['timeline'] }"
                        type="Timeline"
                        :data='absences'
                        :options='options'>
                </GChart>
            </div>
            <p v-if="!showChart && !this.$store.getters.team_msg">
                Nothing to show
            </p>
        </b-container>
    </div>
</template>

<script>
    import Nav from "./Nav";
    import {GChart} from 'vue-google-charts'
    import {instance} from "../Api";

    export default {
        components: {Nav, GChart},
        name: "Timeline",
        data() {
            return {
                absences: [],
                members: [],
                day: Date,
                zoom: 0,
                options: {},
                teams: [{
                    id: '',
                    name: ''
                }],
                showChart: false,
                showSelector: false,
                userTeam: {
                    teamId: 0,
                    name: ''
                }
            }
        },
        created() {
            this.$acl.change(localStorage.getItem('user'));
            this.userTeam.teamId = localStorage.getItem('teamId');
            this.userTeam.name = localStorage.getItem('team');
            instance.get('/teams').then((res) => {
                this.teams = parseTeam(res.data);
                this.showSelector = true;
            });

            if (this.$acl.not.check('isManager')) {
                this.showChartForEmployee(this.showAbsences);
            }
        },
        methods: {
            zoomChart() {
                this.options = {
                    width: this.zoom
                }
            },

            showChartForManager(id) {
                this.members = [];
                this.absences = [];
                this.showChart = false;
                this.$router.replace({name: "Timeline", params: {id: id}});
                instance.get('/teams/members/' + id)
                    .then((res) => {
                        this.members = extractMembers(res.data);
                    }).catch((err) => {
                    console.log(err);
                });

                instance.get('/teams/absences/'
                    + id
                ).then((res) => {
                    this.absences = extractMembers(res.data);
                    if (this.absences.length > 0) {
                        Array.prototype.push.apply(this.absences, this.members);
                        this.showChart = true;
                    }

                }).catch((err) => {
                    console.log(err);
                });
            },

            showChartForEmployee(callback) {
                instance.get('/teams/members')
                    .then((res) => {
                        this.members = extractMembers(res.data);
                        callback(this.userTeam.teamId);
                    }).catch((err) => {
                    console.log(err);
                });
            },

            showAbsences(id) {
                instance.get('/teams/absences/' + id).then((res) => {
                    this.absences = extractMembers(res.data);
                    if (this.absences.length > 0) {
                        Array.prototype.push.apply(this.absences, this.members);
                        this.showChart = true;
                    }
                }).catch((err) => {
                    console.log(err);
                });
            }
        }
    }

    function parseTeam(data) {
        let teams = [];
        for (let i = 0; i < data.length; i++) {
            teams.push({
                id: data[i].teamId,
                name: data[i].name
            });
        }
        return teams;
    }

    function extractMembers(data) {
        let members = [];
        for (let i = 0; i < data.length; i++) {
            members[i] = new Array(4);
            members[i][0] = data[i].name + ' ' + data[i].surname;
            members[i][1] = data[i].type;
            members[i][2] = new Date(data[i].begin + 'T00:00:00');
            members[i][3] = new Date(data[i].end + 'T00:00:00');
        }
        return members;
    }


</script>

<style scoped>
    #chart_wrapper {
        overflow-x: scroll;
        overflow-y: hidden;
    }

    #slider {
        max-width: 300px;
    }

    #timeline {
        min-height: 400px;
    }

</style>