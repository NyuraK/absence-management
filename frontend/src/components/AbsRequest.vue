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
            absTypes: [{ text: 'Select One', value: null }, 'Sickness', 'Business trip', 'Maternity/Paternity', 'Vacations', 'At home'],
            show: true,
            range: {
                start: new Date(2018, 3, 16),
                end: new Date(2018, 3, 19)
            }
        }
        },
        methods: {
        onSubmit(evt) {
            //TODO: complete task -> appearance in list of request from manage requests
            evt.preventDefault();
            // let req_st = extractStart();
            // let req_en = extractEnd();
            alert(JSON.stringify(this.range));
            let msg = {
                type: this.type,
                description: "Test request",
                start: "2018-04-01",
                end: "2018-04-10"
            };
            instance.post("/request/add", msg).then(res => {

            }).catch(err=> {
                console.log(err);
            })

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