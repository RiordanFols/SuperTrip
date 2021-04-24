Vue.component('ticket-trip', {
    props: ['trip'],
    template:
        '<div class="ticket-trip">' +
            '<div class="trip-info-transport">Transport: {{ trip.type }} ({{ trip.cost }}$)</div>' +
            '<div class="trip-info-nodes">From {{ trip.edge.from.name }} to {{ trip.edge.to.name }}</div>' +
            '<div class="trip-info-time">{{ trip.fromTime }} - {{ trip.toTime }}</div>' +
        '</div>'
});

Vue.component('user-ticket', {
    props: ['ticket'],
    template:
        '<div class="user-ticket">' +
            '<h4>Ticket info:</h4>' +
            '<div class="user-ticket-number">Number: {{ ticket.number }}</div>' +
            '<div class="user-ticket-status">Status: {{ ticket.status }}</div>' +
            '<div class="user-ticket-trips">' +
                '<ticket-trip v-for="trip in ticket.trips" :key="trip.id" :trip="trip"/>' +
            '</div>' +
        '<br/></div>'
});

let ticketSearchResult = new Vue({
    el: '#ticketSearchResult',
    data: {
        user: frontendData.user,
        expiredTickets: frontendData.expiredTickets,
        actualTickets: frontendData.actualTickets,
        // error: frontendData.error,
        // notification: frontendData.notification,
    },
    template:
        '<div class="tickets-info">' +
            '<div class="user-info">' +
                '<h3>Passenger info:</h3>' +
                '<div class="user-info-name">Name: {{ user.name }}</div>' +
                '<div class="user-info-surname">Surname: {{ user.surname }}</div>' +
                '<div class="user-info-middle-name">Middle name: {{ user.middleName }}</div>' +
                '<div class="user-info-passport">Passport: {{ user.passportId }} {{ user.passportSeries }}</div>' +
            '</div>' +

            '<div class="user-tickets" v-if="actualTickets.length > 0">' +
                '<h3>Actual tickets:</h3>' +
                '<user-ticket v-for="ticket in actualTickets" :key="ticket.id" :ticket="ticket"/>' +
            '</div>' +
            '<div class="user-tickets" v-if="expiredTickets.length > 0">' +
                '<h4>Expired tickets:</h4>' +
                '<user-ticket v-for="ticket in expiredTickets" :key="ticket.id" :ticket="ticket"/>' +
            '</div>' +
        '</div>'
});