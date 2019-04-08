<template>
    <div>
        <Nav></Nav>
        <b-container>
            <b-row>
                <b-col>
                    <SecurityTest></SecurityTest>
                </b-col>
                <b-col cols="2" v-if="$acl.not.check('isAdmin')">
                    <v-card id="days">
                        <v-container fill-height>
                            <v-layout fill-height>
                                <v-flex xs12 align-end>
                                    <span class="headline">Amount of vacant days</span> <br> <br>
                                    <span class="headline">{vacantDays}</span>
                                </v-flex>
                            </v-layout>
                        </v-container>
                    </v-card>
                </b-col>
                <b-col cols="5">
                    <AbsRequest></AbsRequest>
                </b-col>
            </b-row>
        </b-container>>
    </div>
</template>

<script>
    import SecurityTest from "./SecurityTest";
    import AbsRequest from "./AbsRequest";
    import Nav from "./Nav";
    import {instance} from "../Api";

    export default {
        name: "Home",
        data() {
            return {
                vacantDays: ''
            }
        },
        components: {AbsRequest, SecurityTest, Nav},
        created () {
            this.$acl.change(localStorage.getItem('user'));
            this.vacantDays = instance.get('/user/rest_days');
        },
        methods: {
            exit(evt) {
                evt.preventDefault();
                this.$store.dispatch('userLogOut').then(()=>{
                    this.$acl.change(localStorage.getItem('user'));
                    this.$router.push('/');
                });
            }
        },

    }
</script>

<style scoped>

    #days {
        background-color: cadetblue;
        color: white;
        text-align: center;
    }

</style>