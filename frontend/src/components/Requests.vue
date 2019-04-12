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
                             :items="items"
                             @row-selected="rowSelected"
                             show-empty
                    >
                        <template slot="empty" slot-scope="scope">
                            <h4>No requests</h4>
                        </template>
                    </b-table>
                </b-tab>
                <b-tab title="Resolved requests">
                    <b-form-group label="Selection mode:" label-cols-md="4">
                        <b-form-select v-model="selectMode" :options="modes" class="mb-3"></b-form-select>
                    </b-form-group>

                    <b-table id="tableResolved"
                             selectable
                             :select-mode="selectMode"
                             selectedVariant="success"
                             :items="itemsResolved"
                             @row-selected="rowSelected"
                             show-empty
                    >
                        <template slot="empty" slot-scope="scope">
                            <h4>No requests</h4>
                        </template>
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
                selected: []
            }
        },
        mounted() {
            instance.get("/requests").then((resp) => {
                this.items = resp.data;
            })
        },
        methods: {
            rowSelected(items) {
                this.selected = items
            }
        }
    }
</script>

<style scoped>
    #table {
        border: #009999;
    }

</style>