Vue.component('ticket-trip', {
    props: ['trip'],
    template:
        '<div class="ticket-trip">' +
            '<div class="trip-info-transport">Transport: {{ trip.type }} ({{ trip.cost }}$)</div>' +
            '<div class="trip-info-nodes">From {{ trip.edge.from.name }} to {{ trip.edge.to.name }}</div>' +
            '<div class="trip-info-time">{{ trip.fromTime }} - {{ trip.toTime }}</div>' +
        '</div>'
});

let ticketInfo = new Vue({
    el: '#ticketInfo',
    data: {
        ticket: frontendData.ticket,
        // error: frontendData.error,
        // notification: frontendData.notification,
    },
    template:
        '<div class="ticket-info">' +
            '<div class="ticket-number">Number: {{ ticket.number }}</div>' +
            '<div class="ticket-status">Status: {{ ticket.status }}</div>' +
            '<div class="ticket-name">Passenger name: {{ ticket.pasName }}</div>' +
            '<div class="ticket-name">Passenger surname: {{ ticket.pasSurname }}</div>' +
            '<div class="ticket-name">Passenger middle name: {{ ticket.pasMiddleName }}</div>' +
            '<div class="ticket-name">Passenger passport: {{ ticket.pasPassportSeries }}' +
                ' {{ ticket.pasPassportId }}</div>' +
            '<div class="ticket-trips">' +
                '<ticket-trip v-for="trip in ticket.trips" :key="trip.id" :trip="trip"/>' +
            '</div>' +
        '</div>'
});