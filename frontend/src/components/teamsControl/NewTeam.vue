<template>
    <div>
        <h5>Teams
            <v-icon v-on:click="dialog = ! dialog">add</v-icon>
        </h5>
        <v-dialog v-model="dialog" max-width="600px">
            <v-card>
                <v-card-text>
                    <h3 class="headline mb-0">Enter team details</h3>
                    <v-text-field label="Name*" v-model="name"></v-text-field>
                    <v-text-field label="Quota*"  v-model="quota"></v-text-field>
<!--                    <v-select :items="departments"  label="Department*" v-model="department"-->
<!--                              item-text="name"-->
<!--                              return-object></v-select>-->
                    <v-select :items="findManagers"  label="Manager*" v-model="managerId"
                              :item-text="text"
                              item-value="userId"></v-select>
                    <small class="grey--text">* Required fields.</small>
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
                rules: {
                    required: value => !!value || 'Required.'
                },
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
                    manager: this.managerId,
                    // departmentId: this.department.departmentId
                };
                instance.post('teams/',
                    newTeam
                )
                    .then(resp => {
                        this.$emit("newTeam");
                        this.name = '';
                        this.quota = '';
                        this.managerId = '';
                        this.department = [];
                        this.dialog = false;
                    });
            }
        },

        created: function () {
            // instance.get('departments')
            //     .then(response => {
            //         this.departments = response.data;
            //     });
            instance.get('users')
                .then(response => this.managers = response.data);
        },

        computed: {
            findManagers() {
                return this.managers.filter(item => item.role !== 'Employee' && item.role !== 'Administrator');
            },
        }


    }
</script>

<style scoped>

</style>
