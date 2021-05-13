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
                    '<input type="text" maxlength="25" name="username" v-model="fd.username" required autofocus/>' +
                '</div>' +

                '<div>' +
                    '<span>Name</span>' +
                    '<input type="text" maxlength="25" name="name" v-model="fd.name" required/>' +
                '</div>' +

                '<div>' +
                    '<span>Surname</span>' +
                    '<input type="text" maxlength="25" name="surname" v-model="fd.surname" required/>' +
                '</div>' +

                '<div>' +
                    '<span>Middle name</span>' +
                    '<input type="text" maxlength="25" name="middleName" v-model="fd.middleName"/>' +
                '</div>' +

                '<div>' +
                    '<span>Passport id</span>' +
                    '<input type="text" minlength="6" maxlength="6" name="passportId" v-model="fd.passportId" required/>' +
                '</div>' +

                '<div>' +
                    '<span>Passport series</span>' +
                    '<input type="text" minlength="4" maxlength="4" name="passportSeries" v-model="fd.passportSeries" required/>' +
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