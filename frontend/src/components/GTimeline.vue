<template>
    <div>
        <Nav></Nav>
        <b-container>
            <v-flex>
                <v-slider id="slider"
                        v-model="zoom"
                        :min="4000"
                        :max="7000"
                        label="Zoom"
                        v-on:change="zoomChart"
                ></v-slider>
            </v-flex>

            <div id="chart_wrapper">
                <GChart
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
                options: {
                    backgroundColor: '',
                    // width: 2500
                },
                day: Date,
                zoom: 0
            }
        },
        created() {
            this.day = new Date();
            instance.get('/requests', {
                    params: {
                        date: this.day
                    }
                }
            ).then((res) => {
                this.days = parseStringToDate(res.data);
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

</style>