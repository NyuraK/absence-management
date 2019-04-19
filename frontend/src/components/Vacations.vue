<template>
    <div>
        <Nav></Nav>
        <b-container>
            <b-row>
                <b-col>
                    <b-row>
                        <v-calendar
                                is-expanded :attributes='attributes'>
                        </v-calendar>
                    </b-row>
                    <b-row>
                        <b-col>
                            <v-switch v-model="ex11" label="Accepted" value="A" color="indigo" hide-details></v-switch>
                            <v-switch v-model="ex11" label="Declined" value="A" color="indigo" hide-details></v-switch>
                            <v-switch v-model="ex11" label="Under consider" value="A" color="indigo" hide-details></v-switch>
                        </b-col>
                        <b-col>
                            <v-checkbox v-model="checkbox1" label="All" value="All" onchange="document.getElementById('validated').disabled = !this.checked;" hide-details></v-checkbox>
                            <v-checkbox v-model="selected" id="validated" label="Business trip" value="A" color="indigo" hide-details></v-checkbox>
                            <v-checkbox v-model="selected" label="Validation" value="A" color="orange darken-3" hide-details></v-checkbox>
                            <v-checkbox v-model="selected" label="Sick leave" value="A" color="red" hide-details></v-checkbox>
                            <v-checkbox v-model="selected" label="Child care" value="A" color="green" hide-details></v-checkbox>
                            <v-checkbox v-model="selected" label="Remote work" value="A" color="cyan" hide-details></v-checkbox>
                        </b-col>
                    </b-row>
                </b-col>
                <b-col cols="5">
                        <AbsRequest></AbsRequest>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
    import SecurityTest from "./SecurityTest";
    import AbsRequest from "./AbsRequest";
    import Nav from "./Nav";

    export default {
        name: "Vacations",
        data () {
            return {
                picker: new Date().toISOString().substr(0, 10),
                picker2: new Date().toISOString().substr(0, 10)
            }
        },
        datas() {
            return {
                vacantDays: '',
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
            }
        },
        components: {AbsRequest, SecurityTest, Nav},
        created () {
            this.$acl.change(localStorage.getItem('user'));
            // this.vacantDays = instance.get('/user/rest_days');
        },


    }
</script>

<style scoped>

    #days {
        background-color: deepskyblue;
        color: lightgreen;
        text-align: center;
    }

</style>