Vue.component('solution-trip', {
    props: ['trip'],
    template:
        '<div class="solution-trip">' +
            '<div class="trip-info">{{ trip.edge.from.name }} to {{ trip.edge.to.name }}</div>' +
        '</div>'
});

Vue.component('solution', {
    props: ['solution'],
    template:
        '<div class="solution">' +
            '<a v-bind:href="\'/ticket/assemble/\' + solution.id">' +
                '<div class="solution-description"></div>' +
                '<div class="solution-header">' +
                    '<div class="solution-type">{{ solution.type }}</div>' +
                    '<div class="solution-time">{{ solution.time }}</div>' +
                    '<div class="solution-cost">{{ solution.cost }}</div>' +
                '</div>' +
                '<div class="solution-trips-block">' +
                    '<solution-trip v-for="trip in solution.trips" :key="trip.id" :trip="trip"/>' +
                '</div>' +
            '</a>' +
        '</div>'
});

let routeSearchResult = new Vue({
    el: '#routeSearchResult',
    data: {
        solutions: frontendData.solutions,
        // error: frontendData.error,
        // notification: frontendData.notification,
    },
    template:
        '<div>' +
            '<form method="post" action="/main">' +
                // '<p v-if="error !== null">{{ error }}</p>' +
                // '<p v-if="notification !== null">{{ notification }}</p>' +

                '<div class="solutions">' +
                    '<solution v-for="solution in solutions" :key="solution.id" :solution="solution"/>' +
                '</div>' +
            '</form>' +
        '</div>'
});