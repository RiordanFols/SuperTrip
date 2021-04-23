let paymentForm = new Vue({
    el: '#paymentForm',
    data: {
        ticketNumber: frontendData.ticketNumber,
        // error: frontendData.error,
        // notification: frontendData.notification,
    },
    template:
        '<div>' +
            '<form method="post" v-bind:href="\'/ticket/buy/\' + ticketNumber">' +
                // '<p v-if="error !== null">{{ error }}</p>' +
                // '<p v-if="notification !== null">{{ notification }}</p>' +

                '<div>' +
                    '<input value="Pay" type="submit">' +
                '</div>' +
            '</form>' +
        '</div>'
});