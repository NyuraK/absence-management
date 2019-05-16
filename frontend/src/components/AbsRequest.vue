<template>
    <div>
        <b-form @submit="onSubmit" @reset="onReset" v-if="show">

            <b-form-group id="input-group-type" label="Type:" label-for="input-type">
                <b-form-select
                        id="input-type"
                        v-model="form.absType"
                        :options="absTypes"
                        required
                ></b-form-select>
            </b-form-group>

            <b-form-group id="input-group-dates" label="Select date:" label-for="input-date">
                <v-date-picker
                        mode='range'
                        v-model='range'
                        show-caps>
                </v-date-picker>
            </b-form-group>

            <b-form-group>
                <b-form-textarea
                        id="textarea"
                        v-model="form.text"
                        placeholder="Add comment..."
                        rows="3"
                        max-rows="6"
                ></b-form-textarea>
            </b-form-group>
            <b-button type="submit" variant="primary">Submit</b-button>
            <b-button type="reset" variant="danger">Reset</b-button>

        </b-form>

        <b-modal ref="my-modal" hide-footer>
            <div class="d-block text-center">
                <h3>{{error_msg}}</h3>
            </div>
        </b-modal>
    </div>
</template>

<script>
    import {instance} from "../Api";

    export default {
        name: "AbsRequest",
        data() {
            return {
                form: {
                    absType: null,
                    text: ''
                },
                absTypes: [],
                show: true,
                isManagerOnRest: false,
                range: {
                    start: new Date(),
                    end: new Date()
                },
                error_msg: ''
            }
        },
        created() {
            instance.get('/requests/types').then((resp) => {
                this.absTypes = resp.data;
            }).catch(err => {
                console.log(err);
            });
            instance.get('/requests/managerVac').then((resp) => {
                this.isManagerOnRest = resp.data;
                console.log("Is manager on the rest " + this.isManagerOnRest);
            }).catch(err => {
                console.log(err);
            });
        },
        methods: {
            onSubmit(evt) {
                evt.preventDefault();
                let msg = {
                    username: localStorage.getItem('username'),
                    start: this.range.start,
                    end: this.range.end,
                    type: this.form.absType,
                    description: this.form.text,
                    needToEmail: this.isManagerOnRest,
                };
                instance.post("/requests", msg).then(res => {
                    this.$emit("addRequest");
                }).catch(err => {
                    if (err.response.status === 406) {
                        this.error_msg = err.response.data;
                        this.$refs['my-modal'].show();
                    }
                });
                this.form.absType = null;
                this.form.text = '';

            },
            onReset(evt) {
                evt.preventDefault();
                this.form.absType = null;
                this.show = false;
                this.$nextTick(() => {
                    this.show = true
                })
            }
        }
    }

</script>

<style scoped>

</style>