
Vue.component('privilege-progress', {
    props: ['privilegeInfo'],
    template:
        '<div class="privilege-progress">' +
            '<progress class="privilege-progress-bar" :max="privilegeInfo.threshold" :value="privilegeInfo.spent"/>' +
            '<div class="privilege-caption">{{ privilegeInfo.desc }}</div>' +
        '</div>'
});

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

let profile = new Vue({
    el: '#profile',
    data: {
        user: frontendData.authUser,
        expiredTickets: frontendData.expiredTickets,
        actualTickets: frontendData.actualTickets,
        privilegeInfo: frontendData.privilegeInfo,
    },
    template:
        '<div class="profile">' +
            '<div class="profile-caption">Profile</div>' +

            '<div class="user-info">' +
                '<div class="user-info-line">Username: {{ user.username }}</div>' +
                '<div class="user-info-line">Full name: {{ user.name }} {{ user.surname }} {{ user.middleName }}</div>' +
                '<div class="user-info-line">Passport: {{ user.passportId }} {{ user.passportSeries }}</div>' +
            '</div>' +

            '<privilege-progress :privilegeInfo="privilegeInfo"/>' +

            '<table>' +
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