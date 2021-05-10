let paymentForm = new Vue({
    el: '#paymentForm',
    data: {
        ticket: frontendData.ticket,
        // error: frontendData.error,
        // notification: frontendData.notification,
    },
    template:
        '<div>' +
            '<form method="post" v-bind:href="\'/ticket/buy/\' + ticket.number">' +
                // '<p v-if="error !== null">{{ error }}</p>' +
                // '<p v-if="notification !== null">{{ notification }}</p>' +
                '<div>{{ ticket.cost }} $</div>' +
                '<div>' +
                    '<input value="Pay" type="submit">' +
                '</div>' +
            '</form>' +
        '</div>'
});