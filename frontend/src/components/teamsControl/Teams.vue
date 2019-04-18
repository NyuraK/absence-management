<template>
    <div>
        <v-toolbar app>
            <v-toolbar-title>Teams</v-toolbar-title>
            <v-spacer></v-spacer>
        </v-toolbar>
        <v-content>
            <ul class="list-group mt-1" v-for="team of teams" :key="team.teamId">
                <li class="list-group-item">
                    <v-layout align-center justify-space-between row fill-height>
                        <div>
                            <strong class="ml-2"> {{ team.name }} </strong>
                        </div>
                    </v-layout>
                </li>
            </ul>
        </v-content>
    </div>
</template>

<script>

    import {instance} from '../../Api.js';

    export default {
        name: "Teams",

        data() {
            return {
                search: '',
                teams: []
            }
        },
        created: function () {
            instance.get('teams')
                .then(response => {
                    this.teams = response.data;
                    console.log(response)
                })
        }
    }
</script>

<style scoped>

</style>