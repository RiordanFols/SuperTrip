Vue.component('solution-trip', {
    props: ['trip'],
    template:
        '<div class="solution-trip">' +
            '<div class="trip-info-nodes">Transport: {{ trip.type }} ({{ trip.cost }}$)</div>' +
            '<div class="trip-info-nodes">From {{ trip.edge.from.name }} to {{ trip.edge.to.name }}</div>' +
            '<div class="trip-info-time">{{ trip.fromTime }} - {{ trip.toTime }}</div>' +
        '</div>'
});

Vue.component('time-separator', {
    props: ['time'],
    data: function() {
        return {
            hours: 0,
            minutes: 0
        };
    },
    template:
        '<div class="solution-time">Time &#61; {{ hours }}h {{ minutes }}m</div>',
    created: function () {
        this.hours = Math.floor(this.time / 60);
        this.minutes = this.time % 60;
    }
});

Vue.component('solution', {
    props: ['solution'],
    template:
        '<div class="solution">' +
            '<a v-bind:href="\'/ticket/assemble/\' + solution.id">' +
                '<h3 class="solution-description">Solution:</h3>' +
                '<div class="solution-header">' +
                    '<div class="solution-type">Priority &#61;  {{ solution.type }}</div>' +
                    '<time-separator :time="solution.time"/>' +
                    '<div class="solution-cost">Cost &#61;  {{ solution.cost }} $</div>' +
                '</div>' +
                '<div class="solution-trips-block">' +
                    '<h4 class="trips-caption">Trips:</h4>' +
                    '<solution-trip v-for="trip in solution.trips" :key="trip.id" :trip="trip"/>' +
                '</div>' +
            '</a>' +
        '<br/><br/></div>'
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