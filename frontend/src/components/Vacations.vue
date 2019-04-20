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
                            <v-switch v-model="accepted" label="Accepted" color="indigo" @change="onAccepted()"
                                      hide-details></v-switch>
                            <v-switch v-model="declined" label="Declined" color="indigo" @change="onDeclined()"
                                      hide-details></v-switch>
                            <v-switch v-model="consider" label="Under consider" color="indigo" @change="onConsider()"
                                      hide-details></v-switch>
                        </b-col>
                        <b-col>
                            <v-checkbox v-model="all" id="all" label="All" @change="disableChecks()"
                                        hide-details></v-checkbox>
                            <v-checkbox v-model="business" label="Business trip" color="indigo" @change="showVac()"
                                        :disabled="isAllActive" hide-details></v-checkbox>
                            <v-checkbox v-model="vacation" label="Vacation" color="orange darken-3" @change="showVac()"
                                        :disabled="isAllActive" hide-details></v-checkbox>
                            <v-checkbox v-model="sick" label="Sick leave" color="red" @change="showVac()"
                                        :disabled="isAllActive" hide-details></v-checkbox>
                            <v-checkbox v-model="child" label="Child care" color="green" @change="showVac()"
                                        :disabled="isAllActive" hide-details></v-checkbox>
                            <v-checkbox v-model="remote" label="Remote work" color="cyan" @change="showVac()"
                                        :disabled="isAllActive" hide-details></v-checkbox>
                        </b-col>
                    </b-row>
                </b-col>
                <b-col>
                    <AbsRequest></AbsRequest>
                    <b-row>
                        <b-col></b-col>
                        <b-col>
                            <label><p>Occupied days:</p></label>
                        </b-col>
                        <b-col></b-col>

                    </b-row>
                    <b-row>
                        <v-calendar
                                is-expanded :attributes='attr'>
                        </v-calendar>
                    </b-row>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
    import SecurityTest from "./SecurityTest";
    import AbsRequest from "./AbsRequest";
    import Nav from "./Nav";
    import {instance} from "../Api";

    export default {
        name: "Vacations",
        /*data () {
            return {
                picker: new Date().toISOString().substr(0, 10),
                picker2: new Date().toISOString().substr(0, 10)
            }
        },*/
        data() {
            return {
                vacantDays: ' ',
                isAllActive: false,
                accepted: true,
                declined: false,
                consider: false,
                acceptedVacs: [],
                declinedVacs: [],
                considerVacs: [],
                vacBusiness: [],
                vacRemote: [],
                vacChild: [],
                vacVacation: [],
                vacSick: [],
                all: false,
                business: false,
                child: false,
                vacation: true,
                remote: false,
                sick: false,
                selected: ["Accepted"],
                attr:
                    [
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
                            dates: [],
                        },
                        {
                            highlight: {
                                backgroundColor: '#E19344',     // Orange background
                                borderColor: '#D27212',
                                borderWidth: '2px',
                                borderStyle: 'solid',
                            },
                            contentStyle: {
                                color: 'white',                 // White text
                            },
                            dates: [],
                        },
                    ],
                attributes:
                    [
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
                            dates: [],
                        },
                        {
                            highlight: {
                                backgroundColor: '#E19344',     // Red background
                                borderColor: '#D27212',
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
                                backgroundColor: '#5541D0',     // Indigo background
                                borderColor: '#291E6B',
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
                                backgroundColor: '#22B32A',     // Green background
                                borderColor: '#1D6E22',
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
                                backgroundColor: '#2BD7C6',     // Cyan background
                                borderColor: '#2C7D75',
                                borderWidth: '2px',
                                borderStyle: 'solid',
                            },
                            contentStyle: {
                                color: 'white',                 // White text
                            },
                            dates: [],
                        },
                    ]
            }

        },
        components: {AbsRequest, SecurityTest, Nav},
        created() {
            this.$acl.change(localStorage.getItem('user'));
            // this.vacantDays = instance.get('/user/rest_days');
            let name = localStorage.getItem('username');
            // } ;
            instance.get("/calendar/occupied", {
                params: {
                    name: name
                }
            }).then(res => {
                let datesArray = res.data.map(dateString => new Date(dateString));
                this.attr[0].dates = datesArray;
            }).catch(err => {
                console.log(err);
            });
            instance.get("/calendar/busy", {
                params: {
                    name: name
                }
            }).then(res => {
                let datesArray = res.data.map(dateString => new Date(dateString));
                this.attr[1].dates = datesArray;
            }).catch(err => {
                console.log(err);
            });
            instance.get("/calendar/accepted", {
                params: {
                    name: name
                }
            }).then(res => {
                this.acceptedVacs = res.data;
                let vac;
                for (let i = 0; i < res.data.length; i++) {
                    vac = res.data[i].split("//");
                    if (vac[0] === "SI") {
                        this.vacSick.push({start: new Date(vac[1]), end: new Date(vac[2])},);
                    } else if (vac[0] === "VA") {
                        this.vacVacation.push({start: new Date(vac[1]), end: new Date(vac[2])},);
                        this.attributes[1].dates.push({start: new Date(vac[1]), end: new Date(vac[2])},);
                    } else if (vac[0] === "BU") {
                        this.vacBusiness.push({start: new Date(vac[1]), end: new Date(vac[2])},);
                    } else if (vac[0] === "RE") {
                        this.vacRemote.push({start: new Date(vac[1]), end: new Date(vac[2])},);
                    } else if (vac[0] === "CH") {
                        this.vacChild.push({start: new Date(vac[1]), end: new Date(vac[2])},);
                    }
                }
            }).catch(err => {
                console.log(err);
            });
            instance.get("/calendar/declined", {
                params: {
                    name: name
                }
            }).then(res => {
                this.declinedVacs = res;
            }).catch(err => {
                console.log(err);
            });
            instance.get("/calendar/consider", {
                params: {
                    name: name
                }
            }).then(res => {
                this.considerVacs = res;
            }).catch(err => {
                console.log(err);
            });

        },
        methods: {
            disableChecks() {
                if (this.isAllActive == true) {
                    this.isAllActive = false;
                } else if (this.isAllActive == false) {
                    this.isAllActive = true;
                    this.business = false;
                    this.child = false;
                    this.vacation = false;
                    this.remote = false;
                    this.sick = false;
                }
                if (this.all == true) {
                    this.attributes[0].dates = this.vacSick;
                    this.attributes[1].dates = this.vacVacation;
                    this.attributes[2].dates = this.vacBusiness;
                    this.attributes[3].dates = this.vacChild;
                    this.attributes[4].dates = this.vacRemote;
                } else if (this.all == false) {
                    this.attributes[0].dates = [];
                    this.attributes[1].dates = [];
                    this.attributes[2].dates = [];
                    this.attributes[3].dates = [];
                    this.attributes[4].dates = [];
                }
            },
            /*IsEverythingChecked(){
                if (this.business==true&&this.child==true &&this.vacation==true &&this.remote==true &&this.sick==true){
                    this.business=false;
                    this.child=false;
                    this.vacation=false;
                    this.remote=false;
                    this.sick=false;
                    this.all=true;

                }
            },*/
            onAccepted() {
                if (this.accepted == true) {
                    this.declined = false;
                    this.consider = false;
                    this.determinateType(this.acceptedVacs);
                    this.showVac();
                } else if (this.accepted == false) {
                    this.clearDates();
                }
            },
            onDeclined() {
                if (this.declined == true) {
                    this.consider = false;
                    this.accepted = false;
                    this.determinateType(this.declinedVacs);
                    this.showVac();
                } else if (this.declined == false) {
                    this.clearDates();
                }

            },
            onConsider() {
                if (this.consider == true) {
                    this.declined = false;
                    this.accepted = false;
                    this.determinateType(this.considerVacs);
                    this.showVac();
                } else if (this.consider == false) {
                    this.clearDates();
                }
            },
            clearDates() {
                this.vacSick = [];
                this.vacVacation = [];
                this.vacBusiness = [];
                this.vacRemote = [];
                this.vacChild = [];

                this.attributes[0].dates = [];
                this.attributes[1].dates = [];
                this.attributes[2].dates = [];
                this.attributes[3].dates = [];
                this.attributes[4].dates = [];
            },
            determinateType(param) {
                let vac;
                for (let i = 0; i < param.length; i++) {
                    vac = param[i].split("//");
                    if (vac[0] === "SI") {
                        this.vacSick.push({start: new Date(vac[1]), end: new Date(vac[2])},);
                    } else if (vac[0] === "VA") {
                        this.vacVacation.push({start: new Date(vac[1]), end: new Date(vac[2])},);
                    } else if (vac[0] === "BU") {
                        this.vacBusiness.push({start: new Date(vac[1]), end: new Date(vac[2])},);
                    } else if (vac[0] === "RE") {
                        this.vacRemote.push({start: new Date(vac[1]), end: new Date(vac[2])},);
                    } else if (vac[0] === "CH") {
                        this.vacChild.push({start: new Date(vac[1]), end: new Date(vac[2])},);
                    }
                }
            },
            showVac() {
                if (this.sick == true) {
                    this.attributes[0].dates = this.vacSick;
                } else if (this.vacation == true) {
                    this.attributes[1].dates = this.vacVacation;
                } else if (this.business == true) {
                    this.attributes[2].dates = this.vacBusiness;
                } else if (this.child == true) {
                    this.attributes[3].dates = this.vacChild;
                } else if (this.remote == true) {
                    this.attributes[4].dates = this.vacRemote;
                }
                if (this.sick == false) {
                    this.attributes[0].dates = [];
                } else if (this.vacation == false) {
                    this.attributes[1].dates = [];
                } else if (this.business == false) {
                    this.attributes[2].dates = [];
                } else if (this.child == false) {
                    this.attributes[3].dates = [];
                } else if (this.remote == false) {
                    this.attributes[4].dates = [];
                }
            },
        }
    }
</script>

<style scoped>

    #days {
        background-color: deepskyblue;
        color: lightgreen;
        text-align: center;
    }

    p {
        color: cadetblue; /* Цвет текста */
        font-family: Arial, sans-serif; /* Рубленый шрифт */
        font-size: 140%; /* Размер текста */
    }

</style>