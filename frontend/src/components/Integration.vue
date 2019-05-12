<template>
    <div>
        <v-btn outline color="indigo" v-on:click="click">Integrate</v-btn>
        <v-btn outline color="indigo" v-on:click="click2">Add to calendar</v-btn>
        <v-btn outline color="indigo" v-on:click="click3">No integration</v-btn>
    </div>
</template>

<script>

    import {instance} from "../Api";

    export default {
        name: "Integration",

        data() {
            return {}
        },

        methods: {
            click() {
                console.log(localStorage.getItem('username'));
                instance.get('integration')
                    .then(response => {
                        console.log(response.data);
                        window.open(response.data, '_blank');
                        // window.open('https://calendar.google.com/calendar/r?tab=rc');
                    });

            },
            click2() {
                instance.post('integration');

            },
            click3() {
                instance.put('integration');

            }
        },
        // created: function () {
        //     instance.get('user')
        //         .then(response => {
        //             this.users = response.data;
        //         })
        // },
    }

</script>

<style scoped>

</style>