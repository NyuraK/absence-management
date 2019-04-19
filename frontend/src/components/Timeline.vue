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
                          v-on:change="zoomChart"
                ></v-slider>
            </v-flex>

            <div id="chart_wrapper">
                <GChart id="timeline"
                        :settings="{ packages: ['timeline'] }"
                        type="Timeline"
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
                options:
                    {
                        backgroundColor: ''

                    }
            }
        },
        created() {
            instance.get('/team').then((res) => {
                this.members = parseStringToDate(res.data);
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
        }
        ,
        methods: {
            zoomChart() {
                this.options = {
                    width: this.zoom
                }
            }
        }
    }

    function renderMembers(names, members) {
        let date = new Date('invalid date');
        for (let i = 0; i < names.length; i++) {
            members[i] = [
                names[i],
                '',
                date,
                date
            ]
        }
        return members;
    }

    function parseStringToDate(data) {
        for (let i = 0; i < data.length; i++) {
            data[i][2] = new Date(data[i][2] + 'T00:00:00');
            data[i][3] = new Date(data[i][3] + 'T00:00:00');
        }
        return data;
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