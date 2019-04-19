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
                        v-model="date1"
                        :events="vacDates"
                        event-color="red"
                        mode='range'
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
    </div>
</template>

<script>
    import {instance} from "../Api"

    export default {
        name: "AbsRequest",
        data() {
            return {
                form: {
                    absType: null,
                    text: ''
                },
                absTypes: [],
                vacDates: null,
                show: true,
                range: {
                    start: new Date(2019, 3, 16),
                    end: new Date(2019, 3, 19)
                }
            }
        },
        created() {
            let msg = {
                username: localStorage.getItem('username'),
                start: this.range.start,
                end: this.range.end,
                type: this.form.absType,
                description: this.form.text,
            };
            let user= localStorage.getItem('username').toString();
            instance.post("/calendar", user).then(res => {
            }).catch(err=> {
                console.log(err);
            });
            instance.get("/requests/types").then((response)=> {
                this.absTypes = response.data;
            })
            instance.get("/calendar/occupied").then((resp)=> {
                this.vacDates = parseStringToDate(resp.data).toISOString().substr(0, 10);
            })
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
            };
            instance.post("/requests", msg).then(res => {

            }).catch(err=> {
                console.log(err);
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
    function parseStringToDate(data) {
        for (let i = 0; i < data.length; i++) {
            data[i] = new Date(data[i] + 'T00:00:00');
        }
        return data;
    }

</script>

<style scoped>

</style>