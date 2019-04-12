<template>
    <div>
        <Nav></Nav>
        <b-container>
            <b-tabs content-class="mt-3">
                <b-tab title="Active requests" active>
                    <b-form-group label="Selection mode:" label-cols-md="4">
                        <b-form-select v-model="selectMode" :options="modes" class="mb-3"></b-form-select>
                    </b-form-group>

                    <b-table id="table"
                             selectable
                             :select-mode="selectMode"
                             selectedVariant="success"
                             :fields="fields"
                             :items="items"
                             @row-selected="rowSelected"
                             show-empty
                    >
                        <template slot="empty" slot-scope="scope">
                            <h4>No requests</h4>
                        </template>
                        <template slot="thead-top" slot-scope="data"></template>
                    </b-table>
                    <b-button variant="primary" v-on:click="approve">Approve</b-button>
                    <b-button variant="danger" v-on:click="decline">Decline</b-button>
                </b-tab>
                <b-tab title="Resolved requests">
                    <b-form-group label="Selection mode:" label-cols-md="4">
                        <b-form-select v-model="selectMode" :options="modes" class="mb-3"></b-form-select>
                    </b-form-group>

                    <b-table id="tableResolved"
                             selectable
                             :select-mode="selectMode"
                             selectedVariant="success"
                             :fields="resolvedFields"
                             :items="itemsResolved"
                             @row-selected="rowSelected"
                             show-empty
                    >
                        <template slot="empty" slot-scope="scope">
                            <h4>No requests</h4>
                        </template>
                        <template slot="thead-top" slot-scope="data"></template>
                    </b-table>
                </b-tab>
            </b-tabs>
        </b-container>
    </div>
</template>

<script>
    import Nav from "./Nav";
    import {instance} from "../Api";

    export default {
        name: "Requests",
        components: {Nav},
        data() {
            return {
                modes: ['multi', 'single'],
                items: [],
                itemsResolved: [],
                selectMode: 'multi',
                selected: [],
                fields: [
                    { key: "name", label: "Name" },
                    { key: "start", label: "Beginning" },
                    { key: "end", label: "End" },
                    { key: "type", label: "Type"},
                    { key: "description", label: "Description"},
                ],
                resolvedFields: [
                    { key: "name", label: "Name" },
                    { key: "start", label: "Beginning" },
                    { key: "end", label: "End" },
                    { key: "type", label: "Type"},
                    { key: "description", label: "Description"},
                    { key: "status", label: "Status"}
                ]
            }
        },
        mounted() {
            instance.get("/requests").then((resp) => {
                this.items = resp.data.filter(function (el) {
                    return el.status === 'Under consideration'
                });
                this.itemsResolved = resp.data.filter(function (el) {
                    return el.status !== 'Under consideration'
                });
            });
        },
        methods: {
            rowSelected(items) {
                this.selected = items
            },

            approve() {
                instance.put("/requests/approve", this.selected).then(()=>{
                    instance.get("/requests").then((resp) => {
                        this.items = resp.data.filter(function (el) {
                            return el.status === 'Under consideration'
                        });
                        this.itemsResolved = resp.data.filter(function (el) {
                            return el.status !== 'Under consideration'
                        });
                    });
                });
            },

            decline() {
                instance.put("/requests/decline", this.selected).then(()=>{
                    instance.get("/requests").then((resp) => {
                        this.items = resp.data.filter(function (el) {
                            return el.status === 'Under consideration'
                        });
                        this.itemsResolved = resp.data.filter(function (el) {
                            return el.status !== 'Under consideration'
                        });
                    });
                });
            }
        }
    }
</script>

<style scoped>
    #table {
        border: #009999;
    }

</style>