<template>
    <div>
        <Nav></Nav>
        <b-container>
            <GChart
                    :settings="{ packages: ['timeline'] }"
                    type="Timeline"
                    :data='days'
                    :options='options'>
            </GChart>
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
            instance.get('/requests').then((res)=>{
                console.log(res.data);
                this.days = res.data;
            }).catch((err)=>{
                console.log(err);
            });
            // this.days = [
            //     [ 'President', 'John Adams', new Date(2017, 2, 4), new Date(2017, 2, 14) ],
            //     [ 'Vice President', 'John Adams', new Date(2017, 3, 21), new Date(2017, 4, 4)],
            //     [ 'Vice President', 'George Clinton', new Date(2017, 2, 4), new Date(2017, 3, 20)],
            //     [ 'Secretary of State', 'John Jay', new Date(2017, 1, 25), new Date(2017, 2, 22)],
            //     [ 'Secretary of State', 'James Madison', new Date(2017, 2, 2), new Date(2017, 3, 3)],
            //     [ 'Secretary', 'Anna', new Date(2017, 2, 2), new Date(2017, 3, 3)]
            // ];
            this.options = {

            };
        }
    }
</script>

<style scoped>

</style>