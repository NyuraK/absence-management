<template>
    <div>
        <Nav></Nav>
        <b-container>
            <!--<div id="chart_wrapper">-->
                <GChart
                        :settings="{ packages: ['timeline'] }"
                        type="Timeline"
                        :data='days'
                        :options='options'>
                </GChart>
                <b-button variant="primary" v-on:click="nextTwoWeek">Next</b-button>
                <b-button variant="primary" v-on:click="prevTwoWeek">Prev</b-button>
            <!--</div>-->
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
                    backgroundColor: ''
                },
                day: Date,
            }
        },
        created() {
            this.day = new Date();
            instance.get('/requests', {
                    params : {
                        date: this.day
                    }
            }
            ).then((res) => {
                this.days = parseStringToDate(res.data);
            }).catch((err) => {
                console.log(err);
            });
            this.options = {
                // width: 4000,
            };
        },
        methods: {
            nextTwoWeek() {
                this.day.setDate(this.day.getDate() + 14);
                let temp = new Date(this.day);
                let str = temp.toISOString().slice(0, 10);
                this.day = new Date(str + 'T00:00:00');
                instance.get('/requests',
                    {params : {
                            date: this.day
                        }}
                ).then((res) => {
                    this.days = parseStringToDate(res.data);
                }).catch((err) => {
                    console.log(err);
                });
            },
            prevTwoWeek() {
                this.day.setDate(this.day.getDate() - 14);
                let temp = new Date(this.day);
                let str = temp.toISOString().slice(0, 10);
                this.day = new Date(str + 'T00:00:00');
                instance.get('/requests',
                    {params : {
                            date: this.day
                        }}
                ).then((res) => {
                    this.days = parseStringToDate(res.data);
                }).catch((err) => {
                    console.log(err);
                });
            }
        }
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
        /*width: 1200px;*/
    }

</style>