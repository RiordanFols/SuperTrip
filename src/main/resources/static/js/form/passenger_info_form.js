let passengerInfoForm = new Vue({
    el: '#passengerInfoForm',
    data: {
        fd: frontendData.formData,
        solutionId: frontendData.solutionId,
        error: frontendData.error,
        notification: frontendData.notification,
    },
    template:
        '<div>' +
            '<form method="post" v-bind:href="\'/ticket/assemble/\' + solutionId">' +
                '<p v-if="error !== null">{{ error }}</p>' +
                '<p v-if="notification !== null">{{ notification }}</p>' +

                '<div>' +
                    '<span>Name</span>' +
                    '<input type="text" maxlength="25" name="name" :value="fd.name" required autofocus/>' +
                '</div>' +

                '<div>' +
                    '<span>Surname</span>' +
                    '<input type="text" maxlength="25" name="surname" :value="fd.surname" required/>' +
                '</div>' +

                '<div>' +
                    '<span>Middle name</span>' +
                    '<input type="text" maxlength="25" :value="fd.middleName" name="middleName"/>' +
                '</div>' +

                '<div>' +
                    '<span>Passport id</span>' +
                    '<input type="text" maxlength="6" minlength="6" name="passportId" :value="fd.passportId" required/>' +
                '</div>' +

                '<div>' +
                    '<span>Passport series</span>' +
                    '<input type="text" maxlength="4" minlength="4" name="passportSeries" :value="fd.passportSeries" required/>' +
                '</div>' +

                '<div>' +
                    '<input value="OK" type="submit"/>' +
                '</div>' +
            '</form>' +
        '</div>'
});