let passengerInfoForm = new Vue({
    el: '#passengerInfoForm',
    data: {
        tempTicketNumber: frontendData.tempTicketNumber,
        // error: frontendData.error,
        // notification: frontendData.notification,
    },
    template:
        '<div>' +
            '<form method="post" action="/ticket/assemble">' +
                // '<p v-if="error !== null">{{ error }}</p>' +
                // '<p v-if="notification !== null">{{ notification }}</p>' +

                '<input v-model="tempTicketNumber" hidden name="tempTicketNumber">' +
                '<div>' +
                    '<span>Name</span>' +
                    '<input type="text" maxlength="25" name="name" required autofocus>' +
                '</div>' +

                '<div>' +
                    '<span>Surname</span>' +
                    '<input type="text" maxlength="25" name="surname" required>' +
                '</div>' +

                '<div>' +
                    '<span>Middle name</span>' +
                    '<input type="text" maxlength="25" name="middleName">' +
                '</div>' +

                '<div>' +
                    '<span>Passport id</span>' +
                    '<input type="number" min="0" max="999999" name="passportId" required>' +
                '</div>' +

                '<div>' +
                    '<span>Passport series</span>' +
                    '<input type="number" min="0" max="9999" name="passportSeries" required>' +
                '</div>' +

                '<div>' +
                    '<input value="OK" type="submit">' +
                '</div>' +
            '</form>' +
        '</div>'
});