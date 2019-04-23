<template>
    <div>
        <Nav></Nav>
        <b-container>
            <v-flex>
                <v-slider id="slider"
                          v-model="zoom"
                          :min="1200"
                          :max="7000"
                          label="Zoom"
                          v-on:click="zoomChart"
                ></v-slider>
                <v-select v-if="$acl.check('isManager')"
                          :items="teams"
                          item-text="name"
                          item-value="id"
                          label="Team"
                          v-on:change="showChartForManager"
                ></v-select>
            </v-flex>

            <div id="chart_wrapper" v-if="show">
                <GChart id="timeline"
                        :settings="{ packages: ['timeline'] }"
                        type="Timeline"
                        :data='absences'
                        :options='options'>
                </GChart>
            </div>
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
                show: false
            }
        },
        created() {
            instance.get('/teams', {
                params: {
                    username: localStorage.getItem('username')
                }
            }).then((res) => {
                this.teams = parseTeam(res.data);
            });
            instance.get('/teams/timeline').then((res) => {
                this.members = renderMembers(res.data, this.members);

            });
            instance.get('/requests').then((res) => {
                this.absences = parseStringToDate(res.data);
                Array.prototype.push.apply(this.absences, this.members);
            }).catch((err) => {
                console.log(err);
            });
            if (this.$acl.not.check('isManager') || this.teams.length === 0) {
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
                instance.get('/teams/'
                    + localStorage.getItem('username')
                    + '/'
                    + id
                ).then((res) => {
                    this.members = extractMembers(res.data);
                }).catch((err) => {
                    console.log(err);
                });

                instance.get('/teams/absences'
                    + '/'
                    + id,
                    {params: {username: localStorage.getItem('username')}}
                ).then((res) => {
                    this.absences = extractMembers(res.data);
                    Array.prototype.push.apply(this.absences, this.members);
                    this.show = true;
                }).catch((err) => {
                    console.log(err);
                });
            },
            showChartForEmployee(callback) {
                instance.get('/teams/members',
                    {params: {username: localStorage.getItem('username')}}
                ).then((res) => {
                    this.members = extractMembers(res.data);
                    callback(res.data[0].teamID);
                }).catch((err) => {
                    console.log(err);
                });
            },
            showAbsences(id) {
                instance.get('/teams/absences/' + id,
                    {params: {username: localStorage.getItem('username')}}
                ).then((res) => {
                    this.absences = extractMembers(res.data);
                    Array.prototype.push.apply(this.absences, this.members);
                    this.show = true;
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
            members[i][0] = data[i].name + ' ' + data[i].familyName;
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