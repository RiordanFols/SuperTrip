Vue.component('solution-trip', {
    props: ['trip'],
    template:
        '<div class="solution-trip">' +
            '<div class="trip-info-nodes">{{ trip.edge.from.name }} — {{ trip.edge.to.name }}:  {{ trip.type }} ({{ trip.cost }}$)</div>' +
            '<div class="trip-info-time">{{ trip.fromTime }} — {{ trip.toTime }}</div>' +
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
        '<div>Time &#61; {{ hours }}h {{ minutes }}m</div>',
    created: function () {
        this.hours = Math.floor(this.time / 60);
        this.minutes = this.time % 60;
    }
});

Vue.component('solution', {
    props: ['solution'],
    template:
        '<td class="solution">' +
            '<div v-if="solution == null">Sorry, can\'t find route</div>' +
            '<div v-else>' +
                '<div v-if="solution.type == null" class="coming-soon">Coming soon...</div>' +

                '<div v-else>' +
                    '<div class="solution-metrics-block">' +
                        '<time-separator :time="solution.time"/>' +
                        '<div>Cost &#61;  {{ solution.cost }} $</div>' +
                    '</div>' +
                    '<div class="solution-trips-block">' +
                        '<div>Route:</div>' +
                        '<solution-trip v-for="trip in solution.trips" :key="trip.id" :trip="trip"/>' +
                    '</div>' +
                    '<a class="buy-btn-href" v-bind:href="\'/ticket/assemble/\' + solution.id">' +
                        '<div class="buy-btn">Buy</div>' +
                    '</a>' +
                '</div>' +

            '</div>' +
        '</td>'
});

Vue.component('solution-header', {
    props: ['solution'],
    template:
        '<th class="solution-header">' +
            '<div v-if="solution.type != null">Priority: {{ solution.type }}</div>' +
            '<div v-else>Optimal</div>' +
        '</th>'
});

let routeSearchResult = new Vue({
    el: '#routeSearchResult',
    data: {
        solutions: frontendData.solutions,
    },
    template:
        '<table class="route-search-result">' +
            '<tr class="solutions-header">' +
                '<solution-header v-for="solution in solutions" :key="solution.id" :solution="solution"/>' +
            '</tr>' +
            '<tr class="solutions">' +
                '<solution v-for="solution in solutions" :key="solution.id" :solution="solution"/>' +
            '</tr>' +
        '</table>'
});