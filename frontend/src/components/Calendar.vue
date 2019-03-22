<template>
    <v-calendar
            is-double-paned :attributes='attributes'>
    </v-calendar>

</template>

<script>
    export default {
        data() {
            return {
                attributes: [
                    {
                        highlight: {
                            backgroundColor: '#ff8080',     // Red background
                            borderColor: '#ff6666',
                            borderWidth: '2px',
                            borderStyle: 'solid',
                        },
                        contentStyle: {
                            color: 'white',                 // White text
                        },
                        dates: [
                            {
                                start: new Date(2019, 2, 19),
                                end: new Date(2019, 2, 25),
                            },

                        ],
                    }
                ],
            };
        },
        computed() {
            return
        }
    };
</script>
<style scoped>

</style>