
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
            '<div class="trip-info-transport">Transport: {{ trip.type }} ({{ trip.cost }}$)</div>' +
            '<div class="trip-info-nodes">From {{ trip.edge.from.name }} to {{ trip.edge.to.name }}</div>' +
            '<div class="trip-info-time">{{ trip.fromTime }} - {{ trip.toTime }}</div>' +
        '</div>'
});

Vue.component('user-ticket', {
    props: ['ticket'],
    template:
        '<div class="user-ticket">' +
            '<div class="user-ticket-number">Number: {{ ticket.number }}</div>' +
            '<div class="user-ticket-status">Status: {{ ticket.status }}</div>' +
            '<div class="user-ticket-trips">' +
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

            // '<div class="user-tickets" v-if="actualTickets.length > 0">' +
            //     '<h4>Your actual tickets:</h4>' +
            //     '<user-ticket v-for="ticket in actualTickets" :key="ticket.id" :ticket="ticket"/>' +
            // '</div>' +
            // '<div class="user-tickets" v-if="expiredTickets.length > 0">' +
            //     '<h4>Your expired tickets:</h4>' +
            //     '<user-ticket v-for="ticket in expiredTickets" :key="ticket.id" :ticket="ticket"/>' +
            // '</div>' +
        '</div>'
});