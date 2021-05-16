let routeSearchForm = new Vue({
    el: '#routeSearchForm',
    data: {
        fd: frontendData.formData,
        error: frontendData.error,
    },
    template:
        '<form method="post" action="/">' +

            '<div class="route-search-form">' +
                '<div class="error" v-if="error !== null">{{ error }}</div>' +
                '<div class="error" v-else></div>' +

                '<div class="form-block-left">' +
                    '<div class="form-block-left-line">' +
                        '<input class="city-input" type="text" maxlength="50" name="fromCity" placeholder="From"' +
                            ' :value="fd.fromCity" required autofocus>' +
                        '<input class="city-input" type="text" maxlength="50" name="toCity" placeholder="To"' +
                            ' :value="fd.toCity" required>' +
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