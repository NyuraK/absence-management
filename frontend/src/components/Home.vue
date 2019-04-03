<template>
    <v-layout column m-5>
        <v-flex xs12 class="text-xs-center" mt-5>
            <h1>Check area</h1>
            <v-btn dark color="teal lighten-1" v-on:click="getSecuredUserInformation">Call secured user service</v-btn>
            <v-btn dark color="teal lighten-1" v-on:click="getSecuredAdminInformation">Call secured admin service</v-btn>
            <v-btn dark color="teal lighten-1" v-on:click="getSecuredManagerInformation">Call secured manager service</v-btn>

        </v-flex>

        <v-flex xs8 offset-xs3 class="text-xs-left" mt-5>
            <h2>Request URL: {{responseObj.url}}</h2>
            <h2>Request method: {{responseObj.method}}</h2>
            <h2>Status code: {{responseObj.statusCode}}</h2>
            <h2>Response: {{responseObj.msg}}</h2>
        </v-flex>

        <Logout></Logout>
    </v-layout>
</template>

<script>
    import {instance} from '../Api'
    import Logout from "./Logout";
    export default {
        components: {Logout},
        data () {
            return {
                responseObj: {
                    url: '',
                    statusCode: '',
                    method: '',
                    msg: '',
                }
            }
        },
        created: function () {
        },
        methods: {
            getSecuredUserInformation() {
                this.responseObj = {};
                instance.get('http://localhost:8088/user/home')
                    .then(response => {
                        console.log("Get response: ", response.data);
                        this.responseObj = this.parseResponse(response)
                    })
                    .catch(error => {
                        this.responseObj = this.parseResponse(error)
                    });
            },
            getSecuredAdminInformation() {
                this.responseObj = {};
                instance.get('http://localhost:8088/admin/home')
                    .then(response => {
                        console.log("Get response: ", response.data);
                        this.responseObj = this.parseResponse(response)
                    })
                    .catch(error => {
                        this.responseObj = this.parseResponse(error)
                    });
            },
            getSecuredManagerInformation() {
                instance.get('http://localhost:8088/manager/home')
                    .then(response => {
                        console.log("Get response: ", response.data);
                        this.responseObj = this.parseResponse(response)
                    })
                    .catch(error => {
                        this.responseObj = this.parseResponse(error)
                    });
            },
            parseResponse(response) {
                let respObj = {};
                respObj.url = response.config.url;
                respObj.statusCode = response.status;
                respObj.method = response.config.method;
                respObj.msg = response.data.message ? response.data.message : response.data;
                return respObj
            }
        }
    }
</script>