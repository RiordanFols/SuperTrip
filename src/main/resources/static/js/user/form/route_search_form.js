let routeSearchForm = new Vue({
    el: '#routeSearchForm',
    data: {
        fd: frontendData.formData,
        error: frontendData.error,
        notification: frontendData.notification,
        cities: frontendData.cities,
    },
    template:
        '<form method="post" action="/">' +

            '<div class="route-search-form">' +
                '<div class="error" v-if="error !== null">{{ error }}</div>' +
                '<div class="notification" v-if="notification !== null">{{ notification }}</div>' +
                '<div class="error" v-if="error === null && notification === null"></div>' +

                '<div class="form-block-left">' +
                    '<div class="form-block-left-line">' +
                        '<select class="city-input" name="fromCity" :value="fd.fromCity" required autofocus>' +
                            '<option disabled selected value="">Departure city</option> ' +
                            '<option v-for="city in cities" :key="city.id" :value="city.name">{{ city.name }}</option>' +
                        '</select>' +
                        '<select class="city-input" name="toCity" :value="fd.toCity" required>' +
                            '<option disabled selected value="">Arrival city</option> ' +
                            '<option v-for="city in cities" :key="city.id" :value="city.name">{{ city.name }}</option>' +
                        '</select>' +
                    '</div>' +
                    '<div class="form-block-left-line">' +
                        '<div class="datetime-input-line">' +
                            '<div class="datetime-label">Min departure time</div>' +
                            '<input class="datetime-input" type="datetime-local" name="departureTime" ' +
                                ' :value="fd.departureTime" required >' +
                        '</div>' +
                        '<div class="datetime-input-line">' +
                            '<div class="datetime-label">Max arrival time</div>' +
                            '<input class="datetime-input" type="datetime-local" name="arrivalTime"' +
                                ' :value="fd.arrivalTime" required>' +
                        '</div>' +
                    '</div>' +
                '</div>' +

                '<div class="form-block-right">' +
                    '<div class="transport-block">' +
                        '<div class="transport-item">' +
                            '<div class="transport-name">Bus</div>' +
                            '<input class="transport-checkbox" type="checkbox"' +
                                ' name="busAllowed" :checked="fd.busAllowed">' +
                        '</div>' +
                        '<div class="transport-item">' +
                            '<div class="transport-name">Train</div>' +
                            '<input class="transport-checkbox" type="checkbox" ' +
                                ' name="trainAllowed" :checked="fd.trainAllowed">' +
                        '</div>' +
                        '<div class="transport-item">' +
                            '<div class="transport-name">Plane</div>' +
                            '<input class="transport-checkbox" type="checkbox"' +
                                ' name="planeAllowed" :checked="fd.planeAllowed">' +
                        '</div>' +
                    '</div>' +
                '</div>' +
            '</div>' +

            '<div class="submit-block">' +
                '<input class="submit-btn" value="Find" type="submit">' +
            '</div>' +
        '</form>'
});