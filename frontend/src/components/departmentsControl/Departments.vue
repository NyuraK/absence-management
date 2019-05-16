<template>
    <div>
        <Nav></Nav>
        <v-content>
            <v-container>
                <h4>Manage departments</h4>
                <p></p>
                <input v-model="search" class="form-control" placeholder="Search by department name...">
                <p></p>
                <NewDepartment @newDep="reboot"></NewDepartment>
                <ul class="list-group mt-1" v-for="department of findDepartments" :key="department.departmentId">
                    <li class="list-group-item">
                        <v-layout align-center justify-space-between row fill-height>
                            <div>
                                <router-link :to="'/departments/' + department.departmentId">
                                    <strong class="ml-2"> {{ department.name }} </strong>
                                </router-link>
                            </div>
                            <div>
                                <v-layout align-center>
                                    <router-link :to="'/departments/' + department.departmentId">
                                        <v-icon>edit</v-icon>
                                    </router-link>
                                    <v-icon @click="clickDelete(department.departmentId, department.name)">delete
                                    </v-icon>
                                </v-layout>
                            </div>
                        </v-layout>
                    </li>
                </ul>
                <v-layout row justify-center>
                    <v-dialog v-model="deleteDialog" persistent max-width="290">
                        <v-card>
                            <v-card-title class="headline">Are you sure, you want to delete this department?
                            </v-card-title>
                            <v-card-text>
                                <p>Department name: <strong>{{deleteName}}</strong></p>
                            </v-card-text>
                            <v-card-actions>
                                <v-spacer></v-spacer>
                                <v-btn color="green darken-1" flat @click.native="deleteDialog = false">Cancel</v-btn>
                                <v-btn color="green darken-1" flat @click.native="deleteDepartment(deleteId)">Yes
                                </v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-dialog>
                </v-layout>
            </v-container>
        </v-content>
        <Footer></Footer>
    </div>
</template>

<script>

    import {instance} from '../../Api.js';
    import Nav from "../Nav";
    import NewDepartment from "./NewDepartment";
    import Footer from "../Footer";

    export default {
        name: "Departments",
        components: {Footer, NewDepartment, Nav},
        data() {
            return {
                search: '',
                departments: [],
                deleteName: '',
                deleteId: '',
                deleteDialog: false
            }
        },

        methods: {
            clickDelete(id, name) {
                this.deleteDialog = true;
                this.deleteId = id;
                this.deleteName = name;
            },
            deleteDepartment(id) {
                instance.delete('departments/' + id,)
                    .then(resp => {
                        instance.get('departments')
                            .then(response => {
                                this.departments = response.data;
                                this.departments.sort((a, b) => a.name.localeCompare(b.name));
                            })
                    });
                this.deleteDialog = false;
            },
            reboot() {
                instance.get('departments')
                    .then(response => {
                        this.departments = response.data;
                        this.departments.sort((a, b) => a.name.localeCompare(b.name));
                    })
            }
        },

        created: function () {
            instance.get('departments')
                .then(response => {
                    this.departments = response.data;
                    this.departments.sort((a, b) => a.name.localeCompare(b.name));
                })
        },

        computed: {
            findDepartments() {
                return this.departments.filter(item => item.name.toLocaleLowerCase().indexOf(this.search.toLowerCase()) !== -1)
            },
        }
    }
</script>

<style scoped>

</style>