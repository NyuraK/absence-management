<template>
    <b-navbar toggleable="lg" type="dark" variant="info">
        <b-navbar-brand href="#">Absence Management</b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
            <b-navbar-nav>
                <b-nav-item href="/home">Profile</b-nav-item>
                <b-nav-item href="#">Timeline</b-nav-item>
                <b-nav-item href="/requests" v-if="$acl.check('isManager')">
                    <!--<router-link to="/requests">-->
                    Requests
                    <!--</router-link>-->
                </b-nav-item>
                <b-nav-item href="#" v-if="$acl.check('isAdmin')">Manage users</b-nav-item>
                <b-nav-item href="/vacations">Calendar view</b-nav-item>
            </b-navbar-nav>
            <!-- Right aligned nav items -->
            <b-navbar-nav class="ml-auto">
                <b-nav-item v-b-modal="'modal-sm'">Logout</b-nav-item>
                <b-modal id="modal-sm" size="sm" @ok="exit">Are you sure?</b-modal>
            </b-navbar-nav>
        </b-collapse>
    </b-navbar>
</template>

<script>
    export default {
        name: "Nav",
        created () {
            this.$acl.change(localStorage.getItem('user'));
        },
        methods: {
            exit(evt) {
                evt.preventDefault();
                this.$store.dispatch('userLogOut').then(()=>{
                    this.$acl.change(localStorage.getItem('user'));
                    this.$router.push('/');
                });
            }
        },
    }
</script>

<style scoped>

</style>