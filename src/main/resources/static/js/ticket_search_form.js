let app = new Vue({
    el: '#app',
    data: {
        // error: frontendData.error,
        // notification: frontendData.notification,
    },
    template:
        '<div>' +
            '<form method="post" action="/main">' +
                // '<p v-if="error !== null">{{ error }}</p>' +
                // '<p v-if="notification !== null">{{ notification }}</p>' +

                '<div>' +
                    '<span>From</span>' +
                    '<input type="text" maxlength="50" name="fromCity" required autofocus>' +
                '</div>' +

                '<div>' +
                    '<span>To</span>' +
                    '<input type="text" maxlength="50" name="toCity" required>' +
                '</div>' +

                '<div>' +
                    '<span>Departure date</span>' +
                    '<input type="datetime-local" name="departureTime">' +
                '</div>' +

                '<div>' +
                    '<span>Max arrival date</span>' +
                    '<input type="datetime-local" name="arrivalTime">' +
                '</div>' +

                '<div>' +
                    '<div>' +
                        '<span>Bus</span>' +
                        '<input type="checkbox" name="busAvailable">' +
                    '</div>' +
                    '<div>' +
                        '<span>Train</span>' +
                        '<input type="checkbox" name="trainAvailable">' +
                    '</div>' +
                    '<div>' +
                        '<span>Plane</span>' +
                        '<input type="checkbox" name="planeAvailable">' +
                    '</div>' +
                '</div>' +

                '<div>' +
                    '<span>Max number of transfers</span>' +
                    '<input type="number" max="10" min="0" value="2" name="transfersN" required>' +
                '</div>' +

                '<div>' +
                    '<span>Priority</span>' +
                    '<select name="solutionType">' +
                        '<option name="TIME">Time</option>' +
                        '<option name="COST">Cost</option>' +
                    '</select>' +
                '</div>' +

                '<div>' +
                    '<input value="OK" type="submit">' +
                '</div>' +
            '</form>' +
        '</div>'
});