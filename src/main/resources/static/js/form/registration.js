let registrationForm = new Vue({
    el: '#registrationForm',
    data: {
        // error: frontendData.error,
        // notification: frontendData.notification,
    },
    template:
        '<div>' +
            '<form method="post" action="/auth/registration">' +
                // '<p v-if="error !== null">{{ error }}</p>' +
                // '<p v-if="notification !== null">{{ notification }}</p>' +

                '<div>' +
                    '<span>Username</span>' +
                    '<input type="text" maxlength="25" name="username" required autofocus>' +
                '</div>' +

                '<div>' +
                    '<span>Name</span>' +
                    '<input type="text" maxlength="25" name="name" required>' +
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
                    '<span>Password</span>' +
                    '<input type="password" maxlength="25" name="password" required>' +
                '</div>' +

                '<div>' +
                    '<span>Confirm password</span>' +
                    '<input type="password" maxlength="25" name="passwordConfirm" required>' +
                '</div>' +

                '<div>' +
                    '<input value="OK" type="submit">' +
                '</div>' +
            '</form>' +

            '<a href="/auth/login">Login</a>' +
        '</div>'
});