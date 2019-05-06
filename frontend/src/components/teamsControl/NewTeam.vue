<template>
    <div>
        <h5>Teams
            <v-icon v-on:click="dialog = ! dialog">add</v-icon>
        </h5>
        <v-dialog v-model="dialog" max-width="600px">
            <v-card>
                <v-card-text>
                    <h3 class="headline mb-0">Enter team details</h3>
                    <v-text-field label="Name" v-model="name"></v-text-field>
                    <v-text-field label="Quota" v-model="quota"></v-text-field>
                    <v-select :items="departments" label="Department" v-model="department" item-text="name"
                              return-object></v-select>
                    <v-select :items="findManagers" label="Manager" v-model="managerId" :item-text="text"
                              item-value="userId"></v-select>
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
                departments: [],
                department: [],
                managers: [],

            }
        },

        methods: {
            text: item => item.name + ' ' + item.surname,
            save: function () {
                var newTeam = {
                    name: this.name,
                    quota: this.quota,
                    managerId: this.managerId,
                    departmentId: this.department.departmentId
                };
                instance.post('teams/addTeam',
                    newTeam
                )
                    .then(function (response) {
                        console.log(response);
                    });
                location.reload();
                this.dialog = false;
            }
        },

        created: function () {
            instance.get('departments')
                .then(response => {
                    this.departments = response.data;
                });
            instance.get('users')
                .then(response => this.managers = response.data);
        },

        computed: {
            findManagers() {
                return this.managers.filter(item => item.departmentId === this.department.departmentId && item.role !== 'Employee'
                    || item.userId === this.department.directorId);
            },
        }


    }
</script>

<style scoped>

</style>