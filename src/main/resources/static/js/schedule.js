Vue.component('trip', {
    props: ['trip'],
    template:
        '<tr class="schedule-row">' +
            '<td class="trip-id">{{ trip.id }}</td>' +
            '<td class="trip-route">From {{ trip.edge.from.name }} {{ trip.fromTime }} to {{ trip.edge.to.name }}' +
                ' {{ trip.toTime }} on {{ trip.type }}</td>' +
            '<td class="trip-cost">{{ trip.cost }}</td>' +
        '</tr>'
});

let schedule = new Vue({
    el: '#schedule',
    data: {
        schedule: frontendData.schedule,
        // error: frontendData.error,
        // notification: frontendData.notification,
    },
    template:
        '<div class="schedule">' +
            '<table>' +
                '<tr class="schedule-header">' +
                    '<th class="schedule-header-id">Id</th>' +
                    '<th class="schedule-header-route">Route</th>' +
                    '<th class="schedule-header-cost">Cost</th>' +
                '</tr>' +
                '<trip v-for="trip in schedule" :key="trip.id" :trip="trip"/>' +
            '</table>' +
        '</div>'
});