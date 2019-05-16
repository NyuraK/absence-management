<template>
    <div>
        <h5>Departments
            <v-icon v-on:click="dialog = ! dialog">add</v-icon>
        </h5>
        <v-dialog v-model="dialog" max-width="600px">
            <v-card>
                <v-card-text>
                    <h3 class="headline mb-0">Enter department details</h3>
                    <v-text-field label="Name" v-model="name"></v-text-field>
                    <h3 class="headline mb-0">Select director</h3>
                    <v-radio-group v-model="directorId" :mandatory="true">
                        <div v-for="director of findDirectors">
                            <v-layout  row>
                                <v-radio class="ml-3" label="" :value="director.userId"></v-radio>
                                <div>{{ director.name }} {{ director.surname }}</div>
                            </v-layout>
                        </div>
                    </v-radio-group>
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
        name: "NewDepartment",
        data() {
            return {
                dialog: false,
                name: '',
                directorId: '',
                directors: [],
            }
        },
        methods: {
            save: function () {

                var newDepartment = {
                    name: this.name,
                    directorId: this.directorId,
                };
                instance.post('departments/addDepartment',
                    newDepartment
                )
                    .then(resp => {
                        this.name = '';
                        this.directorId = '';
                        this.$emit("newDep");
                    });
                this.dialog = false;
            }
        },

        created: function () {
            instance.get('users')
                .then(response => {
                    this.directors = response.data;
                });

        },

        computed: {
            findDirectors() {
                return this.directors = this.directors.filter(x => x.teamName === 'Main Team');

            },
        }
    }
</script>

<style scoped>

</style>