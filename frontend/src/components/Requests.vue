<template>
    <div>
        <Nav></Nav>
        <b-container>
            <div>
                <b-form-group label="Selection mode:" label-cols-md="4">
                    <b-form-select v-model="selectMode" :options="modes" class="mb-3"></b-form-select>
                </b-form-group>

                <b-table
                        selectable
                        :select-mode="selectMode"
                        selectedVariant="success"
                        :items="items"
                        @row-selected="rowSelected"
                ></b-table>

                {{ selected }}
            </div>
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
                selectMode: 'multi',
                selected: []
            }
        },
        mounted () {
            instance.get("/requests").then((resp)=> {
                this.items = resp.data;
                console.log(resp.data);
            }).catch(err => {
            });
        },
        methods: {
            rowSelected(items) {
                this.selected = items
            }
        }
    }
</script>

<style scoped>

</style>