<template>
    <div>
        <Nav></Nav>
        <b-container>
            <b-row>
                <b-col>
                    <div style="text-align: center">
                        <p> {{timezone}}</p>
                        <p>Your requests:</p>
                        <v-calendar
                                is-expanded :attributes='attributes'>
                        </v-calendar>
                    </div>
                </b-col>
                <b-col>
                    <div>
                        <p>Send new request</p>
                        <AbsRequest></AbsRequest>
                    </div>
                </b-col>
            </b-row>
            <br>
            <b-row>
                <b-col cols="3">
                    <v-switch v-model="accepted" label="Accepted" color="indigo" @change="onAccepted()"
                              hide-details></v-switch>
                    <v-switch v-model="declined" label="Declined" color="indigo" @change="onDeclined()"
                              hide-details></v-switch>
                    <v-switch v-model="consider" label="Under consider" color="indigo" @change="onConsider()"
                              hide-details></v-switch>
                </b-col>
                <b-col cols="3">
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
                <b-col cols="3">
                    <div style="text-align: center">
                        <p>Occupied days for {{team}} team:</p>
                        <v-calendar
                                is-expanded :attributes='attr'>
                        </v-calendar>
                    </div>
                </b-col>
            </b-row>
        </b-container>
        <Footer></Footer>
    </div>
</template>

<script>
    import AbsRequest from "./AbsRequest";
    import Nav from "./Nav";
    import {instance} from "../Api";
    import Footer from "./Footer";

    export default {
        name: "Home",
        data() {
            return {
                timezone: '',
                vacantDays: '',
                isAllActive: false,
                accepted: true,
                declined: false,
                consider: false,
                occupiedDays: new Object(),
                busyDays: new Object(),
                team: null,
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
        components: {Footer, AbsRequest, Nav},
        created() {
            this.$acl.change(localStorage.getItem('user'));

            instance.get("/calendar/current_timezone").then(res=>{
                console.log(res.data);
                this.timezone = res.data;
            });
            instance.get("/calendar/occupiedForSend").then(res => {
                let arr = res.data;
                let occ;
                for (let i = 0; i < arr.length; i++) {
                    occ = [];
                    for (let j = 1; j < arr[i].length; j++) {
                        occ.push(arr[i][j]);
                    }
                    this.occupiedDays[arr[i][0]] = occ;
                    if (arr[0][0] != null) {
                        this.team = arr[0][0];
                    } else {
                        this.team = '';
                    }
                }
                this.attr[0].dates = this.occupiedDays[arr[0][0]].map(dateString => new Date(dateString));


            }).catch(err => {
                console.log(err);
            });
            instance.get("/calendar/busyForSend").then(res => {
                let arr = res.data;

                let busy;
                for (let i = 0; i < arr.length; i++) {
                    busy = [];

                    for (let j = 1; j < arr[i].length; j++) {
                        busy.push(arr[i][j]);
                    }
                    this.busyDays[arr[i][0]] = busy;
                }
                this.attr[1].dates = this.busyDays[arr[0][0]].map(dateString => new Date(dateString));
                console.log(this.busyDays);
            }).catch(err => {
                console.log(err);
            });

            instance.get("/calendar/accepted").then(res => {
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
            instance.get("/calendar/declined").then(res => {
                this.declinedVacs = res.data;
            }).catch(err => {
                console.log(err);
            });
            instance.get("/calendar/consider").then(res => {
                this.considerVacs = res.data;
            }).catch(err => {
                console.log(err);
            });

            instance.get("/users/restdays").then((resp)=>{
                this.vacantDays = resp.data;
            });
        },
        methods: {
            disableChecks() {
                if (this.isAllActive == true) {
                    this.isAllActive = false;
                    this.business = false;
                    this.child = false;
                    this.vacation = false;
                    this.remote = false;
                    this.sick = false;
                } else if (this.isAllActive == false) {
                    this.isAllActive = true;
                    this.business = true;
                    this.child = true;
                    this.vacation = true;
                    this.remote = true;
                    this.sick = true;
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
                    this.clearDates();
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
                    this.clearDates();
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
                    this.clearDates();
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
                    console.log("SICK " + this.sick);
                }
                if (this.vacation == true) {
                    this.attributes[1].dates = this.vacVacation;
                    console.log("VAC " + this.vacation);
                }
                if (this.business == true) {
                    this.attributes[2].dates = this.vacBusiness;
                    console.log("BUS " + this.business);
                }
                if (this.child == true) {
                    this.attributes[3].dates = this.vacChild;
                    console.log("CH " + this.child);
                }
                if (this.remote == true) {
                    this.attributes[4].dates = this.vacRemote;
                    console.log("RE " + this.remote);
                }
                if (this.sick == false) {
                    this.attributes[0].dates = [];
                    console.log("SICK " + this.sick);
                }
                if (this.vacation == false) {
                    this.attributes[1].dates = [];
                    console.log("VAC " + this.vacation);
                }
                if (this.business == false) {
                    this.attributes[2].dates = [];
                    console.log("BUS " + this.business);
                }
                if (this.child == false) {
                    this.attributes[3].dates = [];
                    console.log("CH " + this.child);
                }
                if (this.remote == false) {
                    this.attributes[4].dates = [];
                    console.log("RE " + this.remote);
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