<template>
    <div id="main">
        <b-navbar toggleable="lg" type="dark" variant="info">
            <b-navbar-brand href="#">Absence Management</b-navbar-brand>

            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

            <b-collapse id="nav-collapse" is-nav>
                <b-navbar-nav>
                    <b-nav-item href="#">Profile</b-nav-item>
                    <b-nav-item href="#">Timeline</b-nav-item>
                    <b-nav-item href="#">Requests</b-nav-item>
                    <b-nav-item href="#">Manage users</b-nav-item>
                    <b-nav-item href="#">Calendar view</b-nav-item>
                </b-navbar-nav>
                <!-- Right aligned nav items -->
                <b-navbar-nav class="ml-auto">
                    <b-nav-item v-b-modal="'modal-sm'">Logout</b-nav-item>
                    <b-modal id="modal-sm" size="sm" @ok="exit">Are you sure?</b-modal>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
        <div class="container">
            <SecurityTest></SecurityTest>
        </div>
    </div>
</template>

<script>
    import SecurityTest from "./SecurityTest";
    export default {
        name: "Home",
        components: {SecurityTest},
        methods: {
            exit(evt) {
                evt.preventDefault();
                this.$store.dispatch('userLogOut').then(()=>{
                    this.$router.push('/');
                });
            }
        }
    }
</script>

<style scoped>


</style>