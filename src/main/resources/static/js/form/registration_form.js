let registrationForm = new Vue({
    el: '#registrationForm',
    data: {
        fd: frontendData.formData,
        error: frontendData.error,
        notification: frontendData.notification,
    },
    template:
        '<div>' +
            '<form method="post" action="/registration">' +
                '<p v-if="error !== null">{{ error }}</p>' +
                '<p v-if="notification !== null">{{ notification }}</p>' +

                '<div>' +
                    '<span>Username</span>' +
                    '<input type="text" maxlength="25" name="username" :value="fd.username" required autofocus/>' +
                '</div>' +

                '<div>' +
                    '<span>Name</span>' +
                    '<input type="text" maxlength="25" name="name" :value="fd.name" required/>' +
                '</div>' +

                '<div>' +
                    '<span>Surname</span>' +
                    '<input type="text" maxlength="25" name="surname" :value="fd.surname" required/>' +
                '</div>' +

                '<div>' +
                    '<span>Middle name</span>' +
                    '<input type="text" maxlength="25" name="middleName" :value="fd.middleName"/>' +
                '</div>' +

                '<div>' +
                    '<span>Passport id</span>' +
                    '<input type="number" min="0" max="999999" name="passportId" :value="fd.passportId" required/>' +
                '</div>' +

                '<div>' +
                    '<span>Passport series</span>' +
                    '<input type="number" min="0" max="9999" name="passportSeries" :value="fd.passportSeries" required/>' +
                '</div>' +

                '<div>' +
                    '<span>Password</span>' +
                    '<input type="password" maxlength="25" name="password" required/>' +
                '</div>' +

                '<div>' +
                    '<span>Confirm password</span>' +
                    '<input type="password" maxlength="25" name="passwordConfirm" required/>' +
                '</div>' +

                '<div>' +
                    '<input value="OK" type="submit"/>' +
                '</div>' +
            '</form>' +

            '<a href="/login">Login</a>' +
        '</div>'
});