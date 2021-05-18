let paymentForm = new Vue({
    el: '#paymentForm',
    data: {
        ticket: frontendData.ticket,
    },
    template:
        '<form method="post" v-bind:href="\'/ticket/buy/\' + ticket.number">' +

            '<div class="user-info-form">' +
                '<div class="user-info-form-caption">{{ ticket.cost }}$</div>' +

                '<div class="user-info-input-line">' +
                    '<input class="user-info-btn" value="Pay" type="submit"/>' +
                '</div>' +
            '</div>' +

        '</form>'
});