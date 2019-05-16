<template>
    <div>
        <h3>My requests:</h3>
        <b-table
                selectable
                :select-mode="'single'"
                selectedVariant="success"
                :fields="fields"
                :items="items"
                @row-selected="rowSelected"
                show-empty
        >
            <template slot="empty" slot-scope="scope">
                <h4>You haven't submitted requests</h4>
            </template>
            <template slot="thead-top" slot-scope="data"></template>
        </b-table>
        <b-button variant="danger" v-on:click="delete_request">Delete</b-button>
    </div>

</template>

<script>
    import {instance} from '../../Api.js';

    export default {
        name: "UserRequests",
        data() {
            return {
                items: [],
                fields: [
                    {key: "start", label: "Beginning"},
                    {key: "end", label: "End"},
                    {key: "type", label: "Type"},
                    {key: "status", label: "Status"},
                    {key: "description", label: "Description"},
                ],
                selected: -1,
            }
        },
        created() {
            this.getRequests();
        },

        methods: {
            rowSelected(item) {
                this.selected = item[0].id;
            },

            delete_request() {
                instance.patch("/requests/delete", this.selected)
                    .then(() => {
                        this.getRequests();
                    });
            },

            getRequests() {
                instance.get("/requests/my").then(res => {
                    this.items = res.data;
                })
            }
        }
    }
</script>

<style scoped>

</style>