<template>
    <div>
        <Nav></Nav>
        <b-container>
            <gantt-elastic :tasks="tasks" :options="options">
                <gantt-elastic-header slot="header"></gantt-elastic-header>
            </gantt-elastic>
        </b-container>
    </div>
</template>

<script>

    import GanttElastic from "gantt-elastic/src/GanttElastic.vue";
    import Header from "gantt-elastic/src/components/Header.vue";
    import Nav from "./Nav";

    export default {
        name: "Timeline",
        components: {
            Nav,
            ganttElasticHeader: Header,// or Header
            ganttElastic: GanttElastic
        },
        data() {
            return {
                tasks: [],
                options: []
            };
        },
        created() {
            this.tasks = [
                {
                    id: 1,
                    label: 'Reason to leave',
                    user: 'Denis',
                    start: getDate(-24 * 5),
                    duration: 15 * 24 * 60 * 60,
                    type: 'task'
                },
                {
                    id: 2,
                    label: 'Reason to leave',
                    user: 'Anna',
                    start: getDate(-24 * 4),
                    duration: 4 * 24 * 60 * 60,
                    type: 'task',
                    style: {
                        base: {
                            fill: '#1EBC61',
                            stroke: '#0EAC51'
                        },
                    }
                },
                {
                    id: 3,
                    label: 'Reason to leave',
                    user: 'Denis',
                    start: getDate(-24 * 5),
                    duration: 4 * 24 * 60 * 60,
                    type: 'task',
                    style: {
                        base: {
                            fill: '#a028cc',
                            stroke: '#205aac'
                        },
                    }
                },
            ];
            this.options = {
                maxRows: 100,
                maxHeight: 300,
                title: {
                    label: 'Dream team',
                    html: false
                },
                row: {
                    height: 24
                },
                calendar: {
                    hour: {
                        display: false
                    }
                },
                taskList: {
                    expander: {
                        straight: false
                    },
                    columns: [
                        {
                            id: 1,
                            label: 'Employee',
                            value: 'user',
                            width: 200,
                            html: true,
                            events: {
                                click({data, column}) {
                                    alert('description clicked!\n' + data.label);
                                }
                            }
                        },

                    ]
                }
            }
        },
        locale: {
            name: 'ru',
            Now: 'Now',
            'X-Scale': 'Zoom-X',
            'Y-Scale': 'Zoom-Y',
            'Task list width': 'Task list',
            'Before/After': 'Expand',
            'Display task list': 'Task list'
        }
    }

    function getDate(hours) {
        const currentDate = new Date();
        const currentYear = currentDate.getFullYear();
        const currentMonth = currentDate.getMonth() + 1;
        const currentDay = currentDate.getDate();
        const timeStamp = new Date(`${currentYear}-${currentMonth}-${currentDay} 00:00:00`).getTime();
        return new Date(timeStamp + hours * 60 * 60 * 1000).getTime();
    }

</script>

<style scoped>

</style>