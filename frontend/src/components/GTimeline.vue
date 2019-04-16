<template>
    <div>
        <Nav></Nav>
        <b-container>
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
                    backgroundColor: ''
                },
            }
        },
        created() {
            instance.get('/requests').then((res) => {
                this.days = parseStringToDate(res.data);
            }).catch((err) => {
                console.log(err);
            });
            this.options = {
                width: 4000,
            };
        },
    }

    function parseStringToDate(data) {
        for (let i = 0; i < data.length; i++) {
            data[i][2] = new Date(data[i][2]+'T00:00:00');
            data[i][3] = new Date(data[i][3]+'T00:00:00');
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