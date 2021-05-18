Vue.component('ticket-trip', {
    props: ['trip'],
    template:
        '<div class="ticket-trip">' +
            '<div class="trip-info-nodes">{{ trip.edge.from.name }} — {{ trip.edge.to.name }}:  {{ trip.type }} ({{ trip.cost }}$)</div>' +
            '<div class="trip-info-time">{{ trip.fromTime }} — {{ trip.toTime }}</div>' +
        '</div>'
});

Vue.component('user-ticket', {
    props: ['ticket'],
    template:
        '<div class="user-ticket">' +
            '<div class="ticket-info-line">Ticket <span class="ticket-number">{{ ticket.number }}</span> </div>' +
            '<div class="ticket-info-line">Status: {{ ticket.status }} ({{ ticket.cost }}$)</div>' +
            '<div class="ticket-trips">' +
                '<ticket-trip v-for="trip in ticket.trips" :key="trip.id" :trip="trip"/>' +
            '</div>' +
        '</div>'
});

let ticketSearchResult = new Vue({
    el: '#ticketSearchResult',
    data: {
        user: frontendData.user,
        expiredTickets: frontendData.expiredTickets,
        actualTickets: frontendData.actualTickets,
        error: frontendData.error,
        notification: frontendData.notification,
    },
    template:
        '<div class="ticket-search-result">' +

            '<div class="ticket-search-result-caption">Ticket search result</div>' +

            '<div class="user-info">' +
                '<div class="user-info-line">Full name: {{ user.name }} {{ user.surname }} {{ user.middleName }}</div>' +
                '<div class="user-info-line">Passport: {{ user.passportId }} {{ user.passportSeries }}</div>' +
            '</div>' +

            '<div class="error" v-if="error !== null">{{ error }}</div>' +
            '<div class="notification" v-if="notification !== null">{{ notification }}</div>' +

            '<table v-if="error === null">' +
                '<tr>' +
                    '<th>Actual tickets</th>' +
                    '<th>Expired tickets</th>' +
                '</tr>' +
                '<tr>' +
                    '<td>' +
                        '<user-ticket v-for="ticket in actualTickets" :key="ticket.id" :ticket="ticket"/>' +
                    '</td>' +

                    '<td>' +
                        // '<user-ticket v-for="ticket in expiredTickets" :key="ticket.id" :ticket="ticket"/>' +
                        '<div class="expired-tickets-switch">Open</div>' +
                    '</td>' +
                '</tr>' +
            '</table>' +

        '</div>'
});