<template>
    <div>
        <v-btn block dark color="teal lighten-1" v-on:click="dialog = ! dialog">Change director</v-btn>
        <v-dialog v-model="dialog" max-width="600px">
            <v-card>
                <v-card-text>
                    <v-card-title>
                        <h3 class="headline mb-0">Select director</h3>
                        <v-spacer></v-spacer>
                        <v-text-field
                                v-model="search"
                                append-icon="search"
                                label="Search"
                                single-line
                        ></v-text-field>
                    </v-card-title>
                    <v-radio-group v-model="directorId" :mandatory="false">
                        <div v-for="director of findDirectors">
                            <v-layout row>
                                <v-radio class="ml-3" label="" :value="director.userId"></v-radio>
                                <div>{{ director.name }} {{ director.surname }}</div>
                            </v-layout>
                        </div>
                    </v-radio-group>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn flat color="error" @click="dialog = false">Cancel</v-btn>
                        <v-btn flat color="primary" @click="save">Save</v-btn>
                    </v-card-actions>
                </v-card-text>
            </v-card>

        </v-dialog>
    </div>
</template>

<script>

    import {instance} from "../../Api";

    export default {
        name: "ChangeDirector",
        data() {
            return {
                search: '',
                directorId: '',
                directors: [],
                department: [],
                dialog: false,
                oldDirectorId: '',
                teams: [],
            }
        },

        methods: {
            save() {
                var i;
                for (i = 0; i < this.teams.length; i++) {
                    if (this.teams[i].managerId === this.oldDirectorId) {
                        this.teams[i].managerId = this.directorId;
                        instance.put('teams/' + this.teams[i].teamId, this.teams[i]);
                    }
                }
                this.department.directorId = this.directorId;
                instance.put('departments/' + this.$router.currentRoute.params['id'], this.department)
                    .then(rest => {
                        this.$emit("changeDirector");
                    });
                this.dialog = false;
            }
        },

        created: function () {
            instance.get('departments/' + this.$router.currentRoute.params['id'])
                .then(response => {
                    this.department = response.data;
                    this.directorId = this.department.directorId;
                    this.oldDirectorId = this.directorId;

                });
            instance.get('users')
                .then(response => {
                    this.directors = response.data;
                });
            instance.get('teams')
                .then(response => this.teams = response.data);
        },

        computed: {
            findDirectors() {
                return this.directors.filter(x => (x.teamName === 'Department Managers')
                    && (''.concat(x.name.toLocaleLowerCase(), ' ', x.surname.toLocaleLowerCase(), ' ',)
                        .indexOf(this.search.toLowerCase()) !== -1));
            },
        }
    }
</script>

<style scoped>

</style>