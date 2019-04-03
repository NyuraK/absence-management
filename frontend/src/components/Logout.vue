<template>
    <div>
        <b-button v-b-modal="'modal-sm'" variant="primary">Logout</b-button>
        <b-modal id="modal-sm" size="sm" @ok="exit">Are you sure?</b-modal>
    </div>
</template>

<script>
    export default {
        name: "Logout",
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