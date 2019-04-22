<template>
    <div>
        <h5>Teams<v-icon v-on:click="dialog = ! dialog">add</v-icon></h5>
        <v-dialog v-model="dialog" max-width="600px">
            <v-card>
                <v-card-text>
                    <h3 class="headline mb-0">Enter user details</h3>
                    <v-text-field label="Name" v-model="name"></v-text-field>
                    <v-text-field label="Quota" v-model="quota"></v-text-field>
                    <v-text-field label="Manager" v-model="managerId"></v-text-field>
                    <v-text-field label="Department" v-model="departmentId"></v-text-field>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn flat color="error" @click="dialog = false">Cancel</v-btn>
                        <v-btn flat color="primary" @click="save">Add</v-btn>
                    </v-card-actions>
                </v-card-text>
            </v-card>
        </v-dialog>
    </div>
</template>

<script>

    import {instance} from "../../Api";

    export default {
        name: "NewTeam",

        data() {
            return {
                dialog: false,
                name: '',
                quota: '',
                managerId: '',
                departmentId: ''
            }
        },

        methods: {
            save: function () {

                var newTeam = {
                    name: this.name,
                    quota: this.quota,
                    managerId: this.managerId,
                    departmentId: this.departmentId
                };
                instance.post('teams/addTeam',
                    newTeam
                )
                    .then(function (response) {
                    });
                location.reload();
                this.dialog = false;
            }
        }


    }
</script>

<style scoped>

</style>