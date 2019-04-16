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
                        :data='days'
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
        name: "GTimeline",
        data() {
            return {
                days: [],
                members: [],
                options: {
                    backgroundColor: ''
                },
                day: Date,
                zoom: 0
            }
        },
        created() {
            instance.get('/team').then((res) => {
                this.members = parseStringToDate(res.data);
            });

            instance.get('/requests').then((res) => {
                this.days = parseStringToDate(res.data);
                Array.prototype.push.apply(this.days, this.members);
                console.log(this.days);
            }).catch((err) => {
                console.log(err);
            });
        },
        methods: {
            zoomChart() {
                this.options = {
                    width: this.zoom
                }
            }
        },
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