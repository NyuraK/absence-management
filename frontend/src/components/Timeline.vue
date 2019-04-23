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
                <v-select
                        :items="teams"
                        item-text="name"
                        item-value="id"
                        label="Team"
                        v-on:change="showChart"
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

        },

        methods: {
            zoomChart() {
                this.options = {
                    width: this.zoom
                }
            },
            showChart(id) {
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
                    { params: {username: localStorage.getItem('username')}}
                ).then((res) => {
                    this.absences = extractMembers(res.data);
                    Array.prototype.push.apply(this.absences, this.members);
                    this.show = true;
                }).catch((err) => {
                    console.log(err);
                });
            }
        },
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