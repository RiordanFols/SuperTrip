let ticketSearchForm = new Vue({
    el: '#ticketSearchForm',
    data: {
        fd: frontendData.formData,
        error: frontendData.error,
        notification: frontendData.notification,
    },
    template:
        '<div>' +
            '<form method="post" action="/ticket/search">' +
                '<p v-if="error !== null">{{ error }}</p>' +
                '<p v-if="notification !== null">{{ notification }}</p>' +

                '<div>Passenger data:</div>' +

                '<div>' +
                    '<span>Name</span>' +
                    '<input type="text" maxlength="25" name="name" :value="fd.name" required autofocus>' +
                '</div>' +

                '<div>' +
                    '<span>Surname</span>' +
                    '<input type="text" maxlength="25" name="surname" :value="fd.surname" required>' +
                '</div>' +

                '<div>' +
                    '<span>Middle name</span>' +
                    '<input type="text" maxlength="25" :value="fd.middleName" name="middleName">' +
                '</div>' +

                '<div>' +
                    '<span>Passport id</span>' +
                    '<input type="text" minlength="6" maxlength="6" name="passportId" :value="fd.passportId" required>' +
                '</div>' +

                '<div>' +
                    '<span>Passport series</span>' +
                    '<input type="text" minlength="4" maxlength="4" name="passportSeries" :value="fd.passportSeries" required>' +
                '</div>' +

                '<div>' +
                    '<input value="OK" type="submit">' +
                '</div>' +
            '</form>' +
        '</div>'
});