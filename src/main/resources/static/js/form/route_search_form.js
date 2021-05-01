let routeSearchForm = new Vue({
    el: '#routeSearchForm',
    data: {
        // error: frontendData.error,
        // notification: frontendData.notification,
    },
    template:
        '<form method="post" action="/">' +
            // '<p v-if="error !== null">{{ error }}</p>' +
            // '<p v-if="notification !== null">{{ notification }}</p>' +
            '<div class="route-search-form">' +
                '<div class="form-block-left">' +
                    '<div class="form-block-left-line">' +
                        '<input class="city-input" type="text" maxlength="50" name="fromCity" placeholder="From" required autofocus>' +
                        '<input class="city-input" type="text" maxlength="50" name="toCity" placeholder="To" required>' +
                    '</div>' +
                    '<div class="form-block-left-line">' +
                        '<div class="datetime-input-line">' +
                            '<div class="datetime-label">Min departure time</div>' +
                            '<input class="datetime-input" type="datetime-local" name="departureTime" required >' +
                        '</div>' +
                        '<div class="datetime-input-line">' +
                            '<div class="datetime-label">Max arrival time</div>' +
                            '<input class="datetime-input" type="datetime-local" name="arrivalTime"  >' +
                        '</div>' +
                    '</div>' +
                '</div>' +

                '<div class="form-block-right">' +
                    '<div class="transport-block">' +
                        '<div class="transport-item">' +
                            '<div class="transport-name">Bus</div>' +
                            '<input class="transport-checkbox" type="checkbox" checked name="busAvailable">' +
                        '</div>' +
                        '<div class="transport-item">' +
                            '<div class="transport-name">Train</div>' +
                            '<input class="transport-checkbox" type="checkbox" checked name="trainAvailable">' +
                        '</div>' +
                        '<div class="transport-item">' +
                            '<div class="transport-name">Plane</div>' +
                            '<input class="transport-checkbox" type="checkbox" checked name="planeAvailable">' +
                        '</div>' +
                    '</div>' +
                '</div>' +
            '</div>' +

            '<div class="submit-block">' +
                '<input class="submit-btn" value="Find" type="submit">' +
            '</div>' +
        '</form>'
});