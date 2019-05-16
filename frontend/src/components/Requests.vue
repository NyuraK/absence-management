<template>
    <div>
        <Nav></Nav>
        <b-container>
            <b-row>
                <b-col md="4">
                    <b-form-select v-model="selectedTeam" :options="teams" @change="show">
                        <template slot="first">
                            <option :value="null" disabled>--Please pick a team to see the days on which you do not want
                                to
                                allow vacation--
                            </option>
                        </template>
                    </b-form-select>
                    <br><br>
                    <v-calendar
                            is-expanded :attributes='attr'>
                    </v-calendar>
                </b-col>
                <b-col>
                    <b-tabs content-class="mt-3">
                        <b-tab title="Active requests" active>
                            <b-form-group label="Selection mode:" label-cols-md="4">
                                <b-form-select v-model="selectMode" :options="modes" class="mb-3"></b-form-select>
                            </b-form-group>

                            <b-table
                                    selectable
                                    :select-mode="selectMode"
                                    selectedVariant="success"
                                    :fields="fields"
                                    :items="items"
                                    @row-selected="rowSelected"
                                    show-empty
                            >
                                <template slot="empty" slot-scope="scope">
                                    <h4>No active requests</h4>
                                </template>
                                <template slot="thead-top" slot-scope="data"></template>
                            </b-table>
                            <b-button variant="primary" v-on:click="approve">Approve</b-button>
                            <b-button variant="danger" v-on:click="decline">Decline</b-button>
                        </b-tab>
                        <b-tab title="Resolved requests">

                            <b-table id="tableResolved"
                                     :fields="resolvedFields"
                                     :items="itemsResolved"
                                     show-empty
                            >
                                <template slot="empty" slot-scope="scope">
                                    <h4>No resolved requests</h4>
                                </template>
                                <template slot="thead-top" slot-scope="data"></template>
                            </b-table>
                        </b-tab>
                    </b-tabs>
                </b-col>
            </b-row>
        </b-container>
        <Footer></Footer>
    </div>
</template>

<script>
    import Nav from "./Nav";
    import {instance} from "../Api";
    import Footer from "./Footer";

    export default {
        name: "Requests",
        components: {Footer, Nav},
        data() {
            return {
                modes: ['multi', 'single'],
                items: [],
                itemsResolved: [],
                selectMode: 'multi',
                selectedTeam: null,
                occupiedDays: new Object(),
                busyDays: new Object(),
                teams: [],
                selected: [],
                fields: [
                    {key: "name", label: "Name"},
                    {key: "teamName", label: "Team"},
                    {key: "start", label: "Beginning"},
                    {key: "end", label: "End"},
                    {key: "type", label: "Type"},
                    {key: "description", label: "Description"},
                ],
                resolvedFields: [
                    {key: "name", label: "Name"},
                    {key: "teamName", label: "Team"},
                    {key: "start", label: "Beginning"},
                    {key: "end", label: "End"},
                    {key: "type", label: "Type"},
                    {key: "description", label: "Description"},
                    {key: "status", label: "Status"}
                ],
                attr:
                    [
                        {
                            highlight: {
                                backgroundColor: '#ff2d25',     // Red background
                                borderColor: '#ff473d',
                                borderWidth: '2px',
                                borderStyle: 'solid',
                            },
                            contentStyle: {
                                color: 'white',                 // White text
                            },
                            dates: [],
                        },
                        {
                            highlight: {
                                backgroundColor: '#e1d031',     // Orange background
                                borderColor: '#d2d168',
                                borderWidth: '2px',
                                borderStyle: 'solid',
                            },
                            contentStyle: {
                                color: 'white',                 // White text
                            },
                            dates: [],
                        },
                    ],
            }
        },
        mounted() {
            let name = localStorage.getItem('username');
            instance.get("/calendar/occupiedForDiscuss", {
                params: {
                    name: name
                }
            }).then(res => {
                let arr = res.data;
                let occ;
                for (let i = 0; i < arr.length; i++) {
                    occ = [];
                    for (let j = 1; j < arr[i].length; j++) {
                        occ.push(arr[i][j]);
                    }
                    this.occupiedDays[arr[i][0]] = occ;
                    this.teams.push({text: arr[i][0], value: arr[i][0]},);
                }
            }).catch(err => {
                console.log(err);
            });
            instance.get("/calendar/busyForDiscuss").then(res => {
                let arr = res.data;
                let busy;
                for (let i = 0; i < arr.length; i++) {
                    busy = [];

                    for (let j = 1; j < arr[i].length; j++) {
                        busy.push(arr[i][j]);
                    }
                    this.busyDays[arr[i][0]] = busy;
                }
            }).catch(err => {
                console.log(err);
            });
            instance.get("/requests/active").then((resp) => {
                this.items = resp.data;
            }).catch(err => {
                console.log(err);
            });
            instance.get("/requests/resolved").then((resp) => {
                this.itemsResolved = resp.data;
            }).catch(err => {
                console.log(err);
            })
        },
        methods: {
            show: function () {
                this.attr[0].dates = this.occupiedDays[this.selectedTeam].map(dateString => new Date(dateString));
                this.attr[1].dates = this.busyDays[this.selectedTeam].map(dateString => new Date(dateString));
            },
            rowSelected(items) {
                this.selected = items.map(function (el) {
                    return el.id;
                })
            },

            approve() {
                let name = localStorage.getItem('username');
                instance.patch("/requests/approve", this.selected).then(() => {
                    instance.get("/requests/active").then((resp) => {
                        this.items = resp.data;
                    }).catch(err => {
                            console.log(err);
                        });

                    instance.get("/requests/resolved").then((resp) => {
                        this.itemsResolved = resp.data;
                    }).catch(err => {
                        console.log(err);
                    })
                });
            },

            decline() {
                let name = localStorage.getItem('username');
                instance.patch("/requests/decline", this.selected).then(() => {
                    instance.get("/requests/active").then((resp) => {
                        this.items = resp.data;
                    }).catch(err => {
                        console.log(err);
                    });
                    instance.get("/requests/resolved").then((resp) => {
                        this.itemsResolved = resp.data;
                    }).catch(err => {
                        console.log(err);
                    })
                });
            }
        }
    }
</script>

<style scoped>

</style>